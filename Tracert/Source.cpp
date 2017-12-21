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

// IP数据报头部
struct IPHeader {
	BYTE ip_version_IHL;//标识版本号和首部的长度，首部的长度以四个字节为单位
	BYTE ip_tos;        //标识服务类型
	USHORT ip_totalLength;  //总长度
	USHORT ip_identification;   //标识符号
	USHORT ip_flags_fragementOffset;    //标识号以及偏移位号
	BYTE ip_ttl;    // 生存时间
	BYTE ip_protocol;   // 协议选择
	USHORT ip_checkSum; // 首部校验和
	ULONG ip_source;     // 源IP地址
	ULONG ip_address;    // 目的IP地址
};

// ICMP数据报头部
struct ICMPHeader {
	BYTE icmp_type; //icmp的类型
	BYTE icmp_code; //ICMP代码，与type相结合，表示具体的信息
	USHORT icmp_checkSum;   //ICMP校验和
	USHORT icmp_identifier; //ICMP标识符，一般填入本进程的标识符
	USHORT icmp_number;     //ICMP的序号
	ULONG icmp_data;    //ICMP数据部分，4个字节的时间戳
};

struct PINGItems {
	USHORT reply_number;     //序号信息
	DWORD  reply_time;      //记录回复时间
	DWORD  reply_bytes;     //记录字节数目
	DWORD  reply_ttl;       //记录生存时间
};

struct TRACERTItems {
	USHORT reply_number;	// 序号信息
	DWORD  reply_time;		// 记录往返时间
	in_addr dwIPaddr;		// 记录返回的IP地址
};

class Ping {
public:
	Ping();
	~Ping();
	BOOL CPing(DWORD destIP, TRACERTItems *tracertItems = NULL, DWORD timeout = 2000);
	BOOL CPing(char *destIP, TRACERTItems *tracertItems = NULL, DWORD timeout = 2000);
private:
	BOOL pingCore(DWORD destIP, TRACERTItems *tracertItems, DWORD timeout);
	USHORT calCheckSum(USHORT *pBuffer, int size);      //计算icmp校验和
	ULONG getTickCountCalibrate();
	SOCKET socket;
	WSAEVENT event;
	USHORT userCurrentProcID;   // 定义本进程的ID号
	char *ICMPData;             // 定义icmp的数据段
	BOOL initSucc;              // 定义是否构造成功
	static USHORT PacketNumber; // 定义报文的序号
};

USHORT Ping::PacketNumber;

/**
* 初始化构造函数
*/
Ping::Ping() :ICMPData(NULL), initSucc(FALSE) {
	// WSADATA被用来存储被WSAStartup函数调用后返回的Windows Sockets数据
	WSADATA wsadata;
	// WSAStartup，即WSA(Windows Sockets Asynchronous，Windows异步套接字)的启动命令
	// MAKEWORD 宏 平台：SDK这个宏创建一个无符号16位整型，通过连接两个给定的无符号参数。
	WSAStartup(MAKEWORD(1, 1), &wsadata);
	// 创建一个初始状态为失信的匿名的需要手动重置的事件对象。
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
* 析构函数
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
	// 首先判断初始化是否成功
	if (!initSucc) {
		return FALSE;
	}

	// 初始化成功，开始配置socket
	sockaddr_in sockaddrDest;
	sockaddrDest.sin_family = AF_INET;			// 又称PF_INET，表示使用Internet的TCP/IP协议族
	sockaddrDest.sin_addr.s_addr = destIP;
	int socketAddrDestSize = sizeof(sockaddrDest);

	// 构建ICMP包
	int ICMPDataSize = sizeof(ICMPHeader) + DEF_PACKET_SIZE;

	// 调用时间戳函数，得到icmp报文段中的时间戳
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

	//超时时间
	timeout = 3000;

	//设置接收超时时间
	setsockopt(socket, SOL_SOCKET, SO_RCVTIMEO, (char *)&timeout, sizeof(timeout));

	//设置发送超时时间
	setsockopt(socket, SOL_SOCKET, SO_SNDTIMEO, (char *)&timeout, sizeof(timeout));

	while (!bReachDestHost && maxHot--) {
		// 设置相应的ttl字段值
		setsockopt(socket, IPPROTO_IP, IP_TTL, (char *)&iTTL, sizeof(iTTL));
		ptrICMPHeader->icmp_number = PacketNumber;
		ptrICMPHeader->icmp_data = sendTimeStamp;
		ptrICMPHeader->icmp_checkSum = calCheckSum((USHORT*)ICMPData, ICMPDataSize);
		// 构造ICMP报文成功，现在使用Socket进行发送
		if (sendto(socket, ICMPData, ICMPDataSize, 0, (struct sockaddr*)&sockaddrDest, socketAddrDestSize) == SOCKET_ERROR) {
			cout << "now to send message!" << endl;
			return FALSE;
		}
		// 判断是否需要接收相应报文
		if (tracertItems == NULL) {
			return TRUE;
		}
		cout << "hahahaha" << endl;
		int count = 0;
		char buffer[256] = { "\0" };
		while (true) {
			// 接收响应的报文
			if (WSAWaitForMultipleEvents(1, &event, FALSE, 1000, FALSE) != WSA_WAIT_TIMEOUT) {
				// 当没有超时的时候
				WSANETWORKEVENTS wsanetworkevents;
				WSAEnumNetworkEvents(socket, event, &wsanetworkevents);
				if (wsanetworkevents.lNetworkEvents & FD_READ) {
					// 得到系统当前时间
					ULONG theTimeStamp = getTickCountCalibrate();
					cout << theTimeStamp << endl;
					// 拿到接收消息的报文大小 
					int nPacketSize = recvfrom(socket, buffer, 256, 0, (sockaddr*)&sockaddrDest, &socketAddrDestSize);
					if (nPacketSize != SOCKET_ERROR) {
						IPHeader *ipHeader = (IPHeader *)buffer;
						// 得到IP数据报的首部长度
						USHORT userIPHeaderLen = (USHORT)((ipHeader->ip_version_IHL & 0x0f) * 4);
						// 得到ICMP的报文开始段
						ICMPHeader *icmpHeader = (ICMPHeader*)(buffer + userIPHeaderLen);

						cout << "23" << endl;
						if (icmpHeader->icmp_identifier == userCurrentProcID && (icmpHeader->icmp_type == ECHO_REPLY || ECHO_TIMEOUT) && icmpHeader->icmp_number == thePacketNumber) {
							// 如果是当前进程发出的报文、IMCP的响应报文、本次请求的响应报文
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
			// 超时的情况
			if (getTickCountCalibrate() - sendTimeStamp >= timeout) {
				cout << "timeout!" << endl;
				return FALSE;
			}
		}
		cout << "what？？？" << endl;
		iTTL++;
	}
	return TRUE;
}

/**
* 函数功能：得到传递的时间差
* 函数算法：将系统时间转化成为UTC时间，根据UTC时间得到表示时间的64位整数
* @return  时间差
*/
ULONG Ping::getTickCountCalibrate() {
	static ULONG ulFirstCallTick = 0;
	static LONGLONG ulFirstCallTickMs = 0;

	SYSTEMTIME systemtime;
	FILETIME filetime;          // UTC时间
	GetLocalTime(&systemtime);  // 跟平时表示的时间方法一样：年月日
	SystemTimeToFileTime(&systemtime, &filetime);   //将系统时间转换成为常用时间表示方法

	LARGE_INTEGER currentTime;  // 结构体，表示一个64位有符号整数
	currentTime.HighPart = filetime.dwHighDateTime;
	currentTime.LowPart = filetime.dwLowDateTime;

	LONGLONG currentTimeMs = currentTime.QuadPart / 1000;

	if (ulFirstCallTick == 0) {
		// GetTickCount是一种函数。GetTickCount返回（retrieve）从操作系统启动所经过（elapsed）的毫秒数，它的返回值是DWORD。
		ulFirstCallTick = GetTickCount();
	}
	if (ulFirstCallTickMs == 0) {
		ulFirstCallTickMs = currentTimeMs;
	}
	return ulFirstCallTick + (ULONG)(currentTimeMs - ulFirstCallTickMs);
}

/**
* 函数功能：按照16位进行划分，得到校验和，期中icmp校验是对icmp报头和数据段的校验
* 1、将icmp报头按照16bit分成多个，进行相加运算
* @param pBuffer
* @param size 分段个数
* @return 校验和
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
	// 将高16位和低16位进行相加，运算两次
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