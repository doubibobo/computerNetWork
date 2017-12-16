#include <stdlib.h>
#include <stdio.h>
#include <WinSock2.h>
#include <iphlpapi.h>
#include <windows.h>
#include <string.h>

#pragma comment(lib, "iphlpapi.lib")
#pragma comment(lib, "ws2_32.lib")

#define PORT 53			// DNSЭ��Ķ˿ں�
#define TIMEOUT 3000	// ���峬ʱʱ��


// ����DNS���ĵ��ײ�
typedef struct {
	unsigned short id;			// ����Ự��ʶ�������Ӧ���Ľ������ӣ�
	unsigned short flags;		// DNS��־����һλ��0Ϊ��ѯ��1Ϊ��Ӧ���ڶ�λ������λ��0��ʾ��׼��ѯ��1��ʾ�����ѯ��2��ʾ������״̬���󣻣�
	unsigned short questNum;	// ������Ŀ����
	unsigned short answerNum;	// �ش�	��Դ��¼��Ŀ
	unsigned short authorNum;	// ��Ȩ	��Դ��¼��Ŀ
	unsigned short additionNum;	// ����	��Դ��¼��Ŀ
}DNSHDR, *pDNSHDR;

// ����DNS���Ĳ�ѯ��¼
typedef struct {
	unsigned short type;
	unsigned short queryclass;
}QUERYHDR, *pQUERYHDR;

// ����DNS����Ӧ���¼
typedef struct {
	unsigned short type;
	unsigned short classes;
	unsigned long ttl;
	unsigned short length;
}RESPONSE, *pRESPONSE;

// ����dns��ѯ���ģ�������dnssendbuffer���棬hostΪҪ��ѯ�������ַ���
int getDNSPacket(pDNSHDR pdnsHDR, pQUERYHDR pqueryHDR, char* hostname, char* DNSsendBuff);

// ���յ��ı�����srecvBuff�е����ݽ��н���
void decodeDNSPacket(char* DNSrecvBuff);

// ����õ�dns��������Ϣ������dnsserver��
void getDnsServer(char* dnsServer);

int main() {
	char dnsServer[20];			// ��������������
	char hostName[100];				// ��������
	char dnsSendBuffer[3000];		// ���ͻ�����
	char dnsRecvBuffer[3000];		// ���ջ�����

	pDNSHDR pdnsHDR = (pDNSHDR)malloc(sizeof(DNSHDR));
	pQUERYHDR pqueryHDR = (pQUERYHDR)malloc(sizeof(QUERYHDR));

	scanf("%s", hostName);
	printf("%s\n", hostName);

	// ����dns���ݰ���װ�ĳ��Ȳ�����
	// int bufferLength = getDNSPacket(pdnsHDR, pqueryHDR, hostName, dnsSendBuffer);
	// printf("%d", bufferLength);

	// decodeDNSPacket(dnsRecvBuffer);

	// ��ñ���dns��������ַ
	getDnsServer(dnsServer);

	sockaddr_in add;		//����ص�ַ
	SOCKET listenSocket;	//����������õ�socket
	int len;

	// ��ʼ��WinSock
	WORD versionRequest = MAKEWORD(2, 2);
	WSADATA wsaData;
	
	if (WSAStartup(versionRequest, &wsaData) != 0) {
		printf("��ʼ��ʧ�ܣ�");
		return 0;
	}
	else {
		listenSocket = socket(PF_INET, SOCK_DGRAM, 0);
		if (listenSocket == INVALID_SOCKET) {
			printf("����socketʧ�ܣ�");
			fflush(0);
			return 0;
		}
		else {
			add.sin_family = PF_INET;
			add.sin_addr.s_addr = htonl(INADDR_ANY);
			add.sin_port = htons(PORT);
		}

		// ���м����˿ڵİ�
		if (bind(listenSocket, (struct sockaddr*)&add, sizeof(add)) != 0) {
			printf("�˿ڼ���ʧ��");
			fflush(0);
			closesocket(listenSocket);
			return 0;
		}
		else {
			// ����UDP���ݱ�
			int sent;
			hostent* hostData;
			printf("%s\n", dnsServer);
			if (atoi(dnsServer)) {
				// �����IP��ַ�ı�׼��ʽ
				u_long ip = inet_addr(dnsServer);
				// ���ض�Ӧ�ڸ�����ַ��������Ϣ
				hostData = gethostbyaddr((char*)&ip, 4, PF_INET);
			}
			else {
				printf("DNS������������������");
				return 0;
			}
			if (!hostData) {
				printf("��ȡ������Ϣʧ�ܣ�");
				fflush(0);
				return 0;
			}
			else {
				sockaddr_in dest;			// ��дĿ�ĵ�ַ��Ϣ
				dest.sin_family = PF_INET;
				// ��hosten�ṹ�������h_addr_listת��Ϊin_addr���͵ĵ�ַ��Ϣ
				dest.sin_addr = *(in_addr*)(hostData->h_addr_list[0]);
				dest.sin_port = htons(PORT);

				int bufferLength = getDNSPacket(pdnsHDR, pqueryHDR, hostName, dnsSendBuffer);
				sent = sendto(listenSocket, dnsSendBuffer, bufferLength, 0, (sockaddr*)&dest, sizeof(sockaddr_in));
				if (sent != bufferLength) {
					printf("������Ϣʧ�ܣ�");
					fflush(0);
					return 0;
				}
				else {
					sockaddr_in theDnsServer;
					int addr_len = sizeof(theDnsServer);
					int result;
					while (1)
					{
						result = recvfrom(listenSocket, dnsRecvBuffer, sizeof(dnsRecvBuffer)-1, 0, (sockaddr*)&theDnsServer, &addr_len);
						if (result > 0) {
							dnsRecvBuffer[result] = 0;
							decodeDNSPacket(dnsRecvBuffer);
							break;
						}
					}
				}
			}
		}

		return 0;

	}

	return 0;
}

// ����dns��ѯ���ģ�������dnssendbuffer���棬hostΪҪ��ѯ�������ַ���
int getDNSPacket(pDNSHDR pdnsHDR, pQUERYHDR pqueryHDR, char* hostname, char* DNSsendBuff) {
	if (!(strcmp(hostname, "exit"))) {
		return -1;
	}
	else {
		int sendByte = 0;
		printf("%d	%d\n", sizeof(DNSsendBuff), sizeof(DNSHDR));

		ZeroMemory(DNSsendBuff, sizeof(DNSsendBuff));		// ʹ��0�����һ������
		pdnsHDR->id = htons(0x0000);						// ��ʶ�ֶ�����Ϊ0��host to network short
		pdnsHDR->flags = htons(0x0100);						// Ҫ����еݹ��ѯ
		pdnsHDR->questNum = htons(0x0001);					// ��¼һ����ѯ��¼
		pdnsHDR->answerNum = htons(0x0000);					// û�лش��¼�������ļ�¼
		pdnsHDR->authorNum = htons(0x0000);
		pdnsHDR->additionNum = htons(0x0000);
		
		// ������ͷ�����Ƶ�DNSsendBuff��
		memcpy(DNSsendBuff, pdnsHDR, sizeof(DNSHDR));

		printf("----------------\n");

		// ��¼Ŀǰ���ͻ�������������
		sendByte += sizeof(DNSHDR);

		// �������ַ������н������ҽ�����ʽ�ı任�������е�'.'�����滻�����Һ���һ����λ
		char* pTrace = hostname;
		char* pHostname = hostname;
		int strLen = strlen(hostname);
		unsigned char charNum = 0;
		// ���Ƚ����ַ���λ�ĵ���
		while (*pTrace != '\0')
		{
			pTrace++;
		}
		while (pTrace != hostname)
		{
			*(pTrace + 1) = *pTrace;
			pTrace--;
		}
		// �����ַ������滻
		*(pTrace + 1) = *pTrace;
		// ����ѯ�ַ�������һ���ַ���λ��
		pTrace++;
		// ��ʼͳ������..֮����ַ���Ŀ
		while (*pTrace != '\0')
		{
			if (*pTrace == '.') {
				*pHostname = charNum;
				charNum = 0;
				pHostname = pTrace;
			}
			else {
				charNum++;
			}
			pTrace++;
		}
		// ��ĩβ����Ϣ�������룬��www.google.com����������б�����ʱ�����һ���󲻳���Ӧ�Ľ��
		*pHostname = charNum;
		// �������õ��������뷢�ͻ���
		memcpy(DNSsendBuff + sizeof(DNSHDR), hostname, strLen + 2);
		sendByte += strLen + 2;
		// �����ѯ������
		pqueryHDR->type = htons(0x0001);
		// �����ѯ��
		pqueryHDR->queryclass = htons(0x0001);
		// ��������Ĳ�ѯ���ͺͲ�ѯ���������
		memcpy(DNSsendBuff + sizeof(DNSHDR) + strLen + 2, pqueryHDR, sizeof(QUERYHDR));
		sendByte += sizeof(QUERYHDR);
		return sendByte;
	}
}

// ���յ��ı�����srecvBuff�е����ݽ��н���
void decodeDNSPacket(char* DNSrecvBuff) {
	pDNSHDR pdnsHDR = (pDNSHDR)DNSrecvBuff;
	int queryNum, answerNum, authNum, additionNum;
	queryNum = ntohs(pdnsHDR->questNum);		// network to host
	answerNum = ntohs(pdnsHDR->answerNum);
	authNum = ntohs(pdnsHDR->authorNum);
	additionNum = ntohs(pdnsHDR->additionNum);
	// ��flag�ֶ�����ʮ��λ����ʾ�ǲ�ѯ���Ļ�����Ӧ���ģ�0Ϊ��ѯ��1Ϊ��Ӧ
	if (pdnsHDR->flags >> 15) {
		// flags��λֵΪ3����ʾ��������Ӧû��������������Ӧ�ļ�¼
		if ((pdnsHDR->flags & 0x0007) == 3) {
			printf("No corresponding domain name entry.\n\n");
			return;
		}
		else {
			// �鿴����ͷ����AA�ֶ�
			if ((pdnsHDR->flags >> 10) == 0x0001) {
				printf("Authoritative answer:\n");
			}
			else {
				printf("None-authoritative answer:\n");
			}
		}

		char* pTraceResponse;
		pTraceResponse = DNSrecvBuff + sizeof(DNSHDR);
		// ��ָ���ƶ��������ֶ�
		while (*pTraceResponse)
		{
			pTraceResponse++;
		}
		// ��ָ���ƶ�������֮��
		pTraceResponse++;
		// ������ѯ���ͺͲ�ѯ�������ֶΣ�ָ��ָ���һ��Ӧ���¼
		pTraceResponse += sizeof(long);
		in_addr address;
		pRESPONSE pResponse;
		printf("Address:");
		for (int i = 1; i < answerNum; i++) {
			// ָ������Ӧ���¼�������ֶ�
			pTraceResponse += sizeof(short);
			pResponse = (pRESPONSE)pTraceResponse;
			// �������Ӧ���Ķ�
			if (ntohs(pResponse->type) == 1) {
				pTraceResponse += sizeof(RESPONSE);
				unsigned long ulIP = *(unsigned long*)pTraceResponse;
				address.S_un.S_addr = ulIP;
				if (i == answerNum) {
					printf("%s.", inet_ntoa(address));
				}
				else {
					printf("%s;", inet_ntoa(address));
				}
				// ָ����һ��IP��ַ
				pTraceResponse += sizeof(long);
			}
			// ��ѯ������һ��������ֱ��������һ��¼�ֶ�
			else if (ntohs(pResponse->type) == 5)
			{
				pTraceResponse += sizeof(RESPONSE);
				pTraceResponse += ntohs(pResponse->length);
			}
		}
		printf("\n\n");
	}
	else {
		// ����ֶ����λ��Ϊ1����ʾ����һ��Ӧ���ģ������κδ���
		printf("Invalid DNS resolution!\n\n");
	}
}

// ����õ�dns��������Ϣ������dnsserver�У�Ŀ���ǻ�ȡ������dns��������ַ
void getDnsServer(char* dnsServer) {
	// �������Ҫ�Ļ������Ĵ�С
	DWORD length = 0;
	if (GetNetworkParams(NULL, &length) != ERROR_BUFFER_OVERFLOW) {
		return;
	}

	FIXED_INFO* pFixedInfo = (FIXED_INFO*) new BYTE[length];
	// ��ñ��ؼ�����Ĳ���
	if (GetNetworkParams(pFixedInfo, &length) != ERROR_SUCCESS) {
		delete[] pFixedInfo;
		return;
	}
	
	IP_ADDR_STRING* pCurrentDnsServer = &pFixedInfo->DnsServerList;
	if (pCurrentDnsServer != NULL) {
		char* tmp = pCurrentDnsServer->IpAddress.String;	// ����dns��������IP��ַ
		strcpy(dnsServer, tmp);
	}
}



