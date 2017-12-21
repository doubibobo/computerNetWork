#include <iostream>
#include <WinSock2.h>
#include <WS2tcpip.h>
#include <windows.h>
#include <iphlpapi.h>
#include <cstdlib>
#include <iomanip>

#pragma comment(lib, "ws2_32.lib")

#define DEF_PACKET_SIZE 32
#define ECHO_REQUEST 8
#define ECHO_REPLY 0
#define ECHO_TIMEOUT 11
#define DEF_MAX_HOP 30

using namespace std;

// IP���ݱ�ͷ��
struct IPHeader {
	BYTE ip_version_IHL;//��ʶ�汾�ź��ײ��ĳ��ȣ��ײ��ĳ������ĸ��ֽ�Ϊ��λ
	BYTE ip_tos;        //��ʶ��������
	USHORT ip_totalLength;  //�ܳ���
	USHORT ip_identification;   //��ʶ����
	USHORT ip_flags_fragementOffset;    //��ʶ���Լ�ƫ��λ��
	BYTE ip_ttl;    // ����ʱ��
	BYTE ip_protocol;   // Э��ѡ��
	USHORT ip_checkSum; // �ײ�У���
	ULONG ip_source;     // ԴIP��ַ
	ULONG ip_address;    // Ŀ��IP��ַ
};

// ICMP���ݱ�ͷ��
struct ICMPHeader {
	BYTE icmp_type; //icmp������
	BYTE icmp_code; //ICMP���룬��type���ϣ���ʾ�������Ϣ
	USHORT icmp_checkSum;   //ICMPУ���
	USHORT icmp_identifier; //ICMP��ʶ����һ�����뱾���̵ı�ʶ��
	USHORT icmp_number;     //ICMP�����
	ULONG icmp_data;    //ICMP���ݲ��֣�4���ֽڵ�ʱ���
};

struct PINGItems {
	USHORT reply_number;     //�����Ϣ
	DWORD  reply_time;      //��¼�ظ�ʱ��
	DWORD  reply_bytes;     //��¼�ֽ���Ŀ
	DWORD  reply_ttl;       //��¼����ʱ��
};

struct TRACERTItems {
	USHORT reply_number;	// �����Ϣ
	DWORD  reply_time;		// ��¼����ʱ��
	in_addr dwIPaddr;		// ��¼���ص�IP��ַ
};

class Ping {
public:
	Ping();
	~Ping();
	BOOL CPing(DWORD destIP, TRACERTItems *tracertItems = NULL, DWORD timeout = 2000);
	BOOL CPing(char *destIP, TRACERTItems *tracertItems = NULL, DWORD timeout = 2000);
private:
	BOOL pingCore(DWORD destIP, TRACERTItems *tracertItems, DWORD timeout);
	USHORT calCheckSum(USHORT *pBuffer, int size);      //����icmpУ���
	ULONG getTickCountCalibrate();
	SOCKET socket;
	WSAEVENT event;
	USHORT userCurrentProcID;   // ���屾���̵�ID��
	char *ICMPData;             // ����icmp�����ݶ�
	BOOL initSucc;              // �����Ƿ���ɹ�
	static USHORT PacketNumber; // ���屨�ĵ����
};

USHORT Ping::PacketNumber;

/**
* ��ʼ�����캯��
*/
Ping::Ping() :ICMPData(NULL), initSucc(FALSE) {
	// WSADATA�������洢��WSAStartup�������ú󷵻ص�Windows Sockets����
	WSADATA wsadata;
	// WSAStartup����WSA(Windows Sockets Asynchronous��Windows�첽�׽���)����������
	// MAKEWORD �� ƽ̨��SDK����괴��һ���޷���16λ���ͣ�ͨ�����������������޷��Ų�����
	WSAStartup(MAKEWORD(1, 1), &wsadata);
	// ����һ����ʼ״̬Ϊʧ�ŵ���������Ҫ�ֶ����õ��¼�����
	event = WSACreateEvent();
	userCurrentProcID = (USHORT)GetCurrentProcessId();
	if ((socket = WSASocket(AF_INET, SOCK_RAW, IPPROTO_ICMP, NULL, 0, 0)) != SOCKET_ERROR) {
		WSAEventSelect(socket, event, FD_READ);
		initSucc = TRUE;
		ICMPData = (char*)malloc(DEF_PACKET_SIZE + sizeof(ICMPHeader));
		if (ICMPData == NULL) {
			initSucc = FALSE;
		}
	}
}

/**
* ��������
*/
Ping::~Ping() {
	WSACleanup();
	if (NULL != ICMPData) {
		free(ICMPData);
		ICMPData = NULL;
	}
}

BOOL Ping::CPing(DWORD destIP, TRACERTItems *tracertItems, DWORD timeout) {
	return pingCore(destIP, tracertItems, timeout);
}

BOOL Ping::CPing(char *destIP, TRACERTItems *tracertItems, DWORD timeout) {
	if (NULL != destIP) {
		return pingCore(inet_addr(destIP), tracertItems, timeout);
	}
	else {
		return FALSE;
	}
}

BOOL Ping::pingCore(DWORD destIP, TRACERTItems *tracertItems, DWORD timeout) {
	// �����жϳ�ʼ���Ƿ�ɹ�
	if (!initSucc) {
		return FALSE;
	}

	// ��ʼ���ɹ�����ʼ����socket
	sockaddr_in sockaddrDest;
	sockaddrDest.sin_family = AF_INET;			// �ֳ�PF_INET����ʾʹ��Internet��TCP/IPЭ����
	sockaddrDest.sin_addr.s_addr = destIP;
	int socketAddrDestSize = sizeof(sockaddrDest);

	// ����ICMP��
	int ICMPDataSize = sizeof(ICMPHeader) + DEF_PACKET_SIZE;

	// ����ʱ����������õ�icmp���Ķ��е�ʱ���
	ULONG sendTimeStamp = getTickCountCalibrate();
	USHORT thePacketNumber = ++PacketNumber;
	memset(ICMPData, 0, ICMPDataSize);

	ICMPHeader *ptrICMPHeader = (ICMPHeader*)ICMPData;
	ptrICMPHeader->icmp_type = ECHO_REQUEST;
	ptrICMPHeader->icmp_code = 0;
	ptrICMPHeader->icmp_identifier = userCurrentProcID;

	bool bReachDestHost = FALSE;
	int iTTL = 1;
	int maxHot = DEF_MAX_HOP;

	cout << iTTL << "		" << maxHot;

	//��ʱʱ��
	timeout = 3000;

	//���ý��ճ�ʱʱ��
	setsockopt(socket, SOL_SOCKET, SO_RCVTIMEO, (char *)&timeout, sizeof(timeout));

	//���÷��ͳ�ʱʱ��
	setsockopt(socket, SOL_SOCKET, SO_SNDTIMEO, (char *)&timeout, sizeof(timeout));

	while (!bReachDestHost && maxHot--) {
		// ������Ӧ��ttl�ֶ�ֵ
		setsockopt(socket, IPPROTO_IP, IP_TTL, (char *)&iTTL, sizeof(iTTL));
		ptrICMPHeader->icmp_number = PacketNumber;
		ptrICMPHeader->icmp_data = sendTimeStamp;
		ptrICMPHeader->icmp_checkSum = calCheckSum((USHORT*)ICMPData, ICMPDataSize);
		// ����ICMP���ĳɹ�������ʹ��Socket���з���
		if (sendto(socket, ICMPData, ICMPDataSize, 0, (struct sockaddr*)&sockaddrDest, socketAddrDestSize) == SOCKET_ERROR) {
			cout << "now to send message!" << endl;
			return FALSE;
		}
		// �ж��Ƿ���Ҫ������Ӧ����
		if (tracertItems == NULL) {
			return TRUE;
		}
		cout << "hahahaha" << endl;
		int count = 0;
		char buffer[256] = { "\0" };
		while (true) {
			// ������Ӧ�ı���
			if (WSAWaitForMultipleEvents(1, &event, FALSE, 1000, FALSE) != WSA_WAIT_TIMEOUT) {
				// ��û�г�ʱ��ʱ��
				WSANETWORKEVENTS wsanetworkevents;
				WSAEnumNetworkEvents(socket, event, &wsanetworkevents);
				if (wsanetworkevents.lNetworkEvents & FD_READ) {
					// �õ�ϵͳ��ǰʱ��
					ULONG theTimeStamp = getTickCountCalibrate();
					cout << theTimeStamp << endl;
					// �õ�������Ϣ�ı��Ĵ�С 
					int nPacketSize = recvfrom(socket, buffer, 256, 0, (sockaddr*)&sockaddrDest, &socketAddrDestSize);
					if (nPacketSize != SOCKET_ERROR) {
						IPHeader *ipHeader = (IPHeader *)buffer;
						// �õ�IP���ݱ����ײ�����
						USHORT userIPHeaderLen = (USHORT)((ipHeader->ip_version_IHL & 0x0f) * 4);
						// �õ�ICMP�ı��Ŀ�ʼ��
						ICMPHeader *icmpHeader = (ICMPHeader*)(buffer + userIPHeaderLen);

						cout << "23" << endl;
						if (icmpHeader->icmp_identifier == userCurrentProcID && (icmpHeader->icmp_type == ECHO_REPLY || ECHO_TIMEOUT) && icmpHeader->icmp_number == thePacketNumber) {
							// ����ǵ�ǰ���̷����ı��ġ�IMCP����Ӧ���ġ������������Ӧ����
							tracertItems->reply_number = thePacketNumber;
							tracertItems->reply_time = theTimeStamp - icmpHeader->icmp_data;
							tracertItems->dwIPaddr.S_un.S_addr = ipHeader->ip_source;
							if (tracertItems->reply_time) {
								cout << setw(6) << tracertItems->reply_time << "ms" << flush;
								cout << setw(6) << inet_ntoa(tracertItems->dwIPaddr) << flush;
							}
							else {
								cout << setw(6) << "<1" << "ms" << flush;
								cout << setw(6) << inet_ntoa(tracertItems->dwIPaddr) << flush;
							}
							if (tracertItems->dwIPaddr.S_un.S_addr == sockaddrDest.sin_addr.S_un.S_addr) {
								bReachDestHost = TRUE;
								cout << '\t' << inet_ntoa(tracertItems->dwIPaddr) << endl;
							}
							break;
						}
					}
				}
			}
			// ��ʱ�����
			if (getTickCountCalibrate() - sendTimeStamp >= timeout) {
				cout << "timeout!" << endl;
				return FALSE;
			}
		}
		cout << "what������" << endl;
		iTTL++;
	}
	return TRUE;
}

/**
* �������ܣ��õ����ݵ�ʱ���
* �����㷨����ϵͳʱ��ת����ΪUTCʱ�䣬����UTCʱ��õ���ʾʱ���64λ����
* @return  ʱ���
*/
ULONG Ping::getTickCountCalibrate() {
	static ULONG ulFirstCallTick = 0;
	static LONGLONG ulFirstCallTickMs = 0;

	SYSTEMTIME systemtime;
	FILETIME filetime;          // UTCʱ��
	GetLocalTime(&systemtime);  // ��ƽʱ��ʾ��ʱ�䷽��һ����������
	SystemTimeToFileTime(&systemtime, &filetime);   //��ϵͳʱ��ת����Ϊ����ʱ���ʾ����

	LARGE_INTEGER currentTime;  // �ṹ�壬��ʾһ��64λ�з�������
	currentTime.HighPart = filetime.dwHighDateTime;
	currentTime.LowPart = filetime.dwLowDateTime;

	LONGLONG currentTimeMs = currentTime.QuadPart / 1000;

	if (ulFirstCallTick == 0) {
		// GetTickCount��һ�ֺ�����GetTickCount���أ�retrieve���Ӳ���ϵͳ������������elapsed���ĺ����������ķ���ֵ��DWORD��
		ulFirstCallTick = GetTickCount();
	}
	if (ulFirstCallTickMs == 0) {
		ulFirstCallTickMs = currentTimeMs;
	}
	return ulFirstCallTick + (ULONG)(currentTimeMs - ulFirstCallTickMs);
}

/**
* �������ܣ�����16λ���л��֣��õ�У��ͣ�����icmpУ���Ƕ�icmp��ͷ�����ݶε�У��
* 1����icmp��ͷ����16bit�ֳɶ���������������
* @param pBuffer
* @param size �ֶθ���
* @return У���
*/
USHORT Ping::calCheckSum(USHORT *pBuffer, int size) {
	ULONG checkSum = 0;
	while (size > 1) {
		checkSum = checkSum + *pBuffer++;
		size = size - sizeof(USHORT);
	}
	if (size) {
		checkSum = checkSum + *(UCHAR*)pBuffer;
	}
	// ����16λ�͵�16λ������ӣ���������
	checkSum = (checkSum >> 16) + (checkSum & 0xffff);
	checkSum = checkSum + (checkSum >> 16);
	return (USHORT)(~checkSum);
}

int main() {
	Ping testPing;
	char *destination = "115.28.224.101";
	TRACERTItems reply;
	cout << "Tracing route to" << destination << endl;
	while (true) {
		testPing.CPing(destination, &reply);
		Sleep(500);
	}
	return 0;
}