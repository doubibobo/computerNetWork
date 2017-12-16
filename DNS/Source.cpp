#include <stdlib.h>
#include <stdio.h>
#include <WinSock2.h>
#include <iphlpapi.h>
#include <windows.h>
#include <string.h>

#pragma comment(lib, "iphlpapi.lib")
#pragma comment(lib, "ws2_32.lib")

#define PORT 53			// DNS协议的端口号
#define TIMEOUT 3000	// 定义超时时间


// 定义DNS报文的首部
typedef struct {
	unsigned short id;			// 定义会话标识（请求和应答报文建立连接）
	unsigned short flags;		// DNS标志（第一位：0为查询，1为响应；第二位到第五位：0表示标准查询，1表示反向查询，2表示服务器状态请求；）
	unsigned short questNum;	// 问题数目（）
	unsigned short answerNum;	// 回答	资源记录数目
	unsigned short authorNum;	// 授权	资源记录数目
	unsigned short additionNum;	// 附加	资源记录数目
}DNSHDR, *pDNSHDR;

// 定义DNS报文查询记录
typedef struct {
	unsigned short type;
	unsigned short queryclass;
}QUERYHDR, *pQUERYHDR;

// 定义DNS报文应答记录
typedef struct {
	unsigned short type;
	unsigned short classes;
	unsigned long ttl;
	unsigned short length;
}RESPONSE, *pRESPONSE;

// 生成dns查询报文，保存在dnssendbuffer里面，host为要查询的域名字符串
int getDNSPacket(pDNSHDR pdnsHDR, pQUERYHDR pqueryHDR, char* hostname, char* DNSsendBuff);

// 对收到的保存在srecvBuff中的数据进行解析
void decodeDNSPacket(char* DNSrecvBuff);

// 将获得的dns服务器信息保存在dnsserver中
void getDnsServer(char* dnsServer);

int main() {
	char dnsServer[20];			// 本地域名服务器
	char hostName[100];				// 主机域名
	char dnsSendBuffer[3000];		// 发送缓冲区
	char dnsRecvBuffer[3000];		// 接收缓冲区

	pDNSHDR pdnsHDR = (pDNSHDR)malloc(sizeof(DNSHDR));
	pQUERYHDR pqueryHDR = (pQUERYHDR)malloc(sizeof(QUERYHDR));

	scanf("%s", hostName);
	printf("%s\n", hostName);

	// 计算dns数据包封装的长度并返回
	// int bufferLength = getDNSPacket(pdnsHDR, pqueryHDR, hostName, dnsSendBuffer);
	// printf("%d", bufferLength);

	// decodeDNSPacket(dnsRecvBuffer);

	// 获得本地dns服务器地址
	getDnsServer(dnsServer);

	sockaddr_in add;		//绑定相关地址
	SOCKET listenSocket;	//发送与接收用的socket
	int len;

	// 初始化WinSock
	WORD versionRequest = MAKEWORD(2, 2);
	WSADATA wsaData;
	
	if (WSAStartup(versionRequest, &wsaData) != 0) {
		printf("初始化失败！");
		return 0;
	}
	else {
		listenSocket = socket(PF_INET, SOCK_DGRAM, 0);
		if (listenSocket == INVALID_SOCKET) {
			printf("创建socket失败！");
			fflush(0);
			return 0;
		}
		else {
			add.sin_family = PF_INET;
			add.sin_addr.s_addr = htonl(INADDR_ANY);
			add.sin_port = htons(PORT);
		}

		// 进行监听端口的绑定
		if (bind(listenSocket, (struct sockaddr*)&add, sizeof(add)) != 0) {
			printf("端口监听失败");
			fflush(0);
			closesocket(listenSocket);
			return 0;
		}
		else {
			// 发送UDP数据报
			int sent;
			hostent* hostData;
			printf("%s\n", dnsServer);
			if (atoi(dnsServer)) {
				// 如果是IP地址的标准形式
				u_long ip = inet_addr(dnsServer);
				// 返回对应于给定地址的主机信息
				hostData = gethostbyaddr((char*)&ip, 4, PF_INET);
			}
			else {
				printf("DNS本地域名服务器错误！");
				return 0;
			}
			if (!hostData) {
				printf("获取主机信息失败！");
				fflush(0);
				return 0;
			}
			else {
				sockaddr_in dest;			// 填写目的地址信息
				dest.sin_family = PF_INET;
				// 将hosten结构体里面的h_addr_list转化为in_addr类型的地址信息
				dest.sin_addr = *(in_addr*)(hostData->h_addr_list[0]);
				dest.sin_port = htons(PORT);

				int bufferLength = getDNSPacket(pdnsHDR, pqueryHDR, hostName, dnsSendBuffer);
				sent = sendto(listenSocket, dnsSendBuffer, bufferLength, 0, (sockaddr*)&dest, sizeof(sockaddr_in));
				if (sent != bufferLength) {
					printf("发送消息失败！");
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

// 生成dns查询报文，保存在dnssendbuffer里面，host为要查询的域名字符串
int getDNSPacket(pDNSHDR pdnsHDR, pQUERYHDR pqueryHDR, char* hostname, char* DNSsendBuff) {
	if (!(strcmp(hostname, "exit"))) {
		return -1;
	}
	else {
		int sendByte = 0;
		printf("%d	%d\n", sizeof(DNSsendBuff), sizeof(DNSHDR));

		ZeroMemory(DNSsendBuff, sizeof(DNSsendBuff));		// 使用0来填充一块区域
		pdnsHDR->id = htons(0x0000);						// 标识字段设置为0，host to network short
		pdnsHDR->flags = htons(0x0100);						// 要求进行递归查询
		pdnsHDR->questNum = htons(0x0001);					// 记录一个查询记录
		pdnsHDR->answerNum = htons(0x0000);					// 没有回答记录和其他的记录
		pdnsHDR->authorNum = htons(0x0000);
		pdnsHDR->additionNum = htons(0x0000);
		
		// 将报文头部复制到DNSsendBuff中
		memcpy(DNSsendBuff, pdnsHDR, sizeof(DNSHDR));

		printf("----------------\n");

		// 记录目前发送缓冲区的数据量
		sendByte += sizeof(DNSHDR);

		// 对域名字符串进行解析并且进行形式的变换，将所有的'.'进行替换，并且后移一个单位
		char* pTrace = hostname;
		char* pHostname = hostname;
		int strLen = strlen(hostname);
		unsigned char charNum = 0;
		// 首先进行字符串位的调整
		while (*pTrace != '\0')
		{
			pTrace++;
		}
		while (pTrace != hostname)
		{
			*(pTrace + 1) = *pTrace;
			pTrace--;
		}
		// 将首字符进行替换
		*(pTrace + 1) = *pTrace;
		// 将查询字符串后移一个字符的位置
		pTrace++;
		// 开始统计两个..之间的字符数目
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
		// 将末尾的信息进行填入，如www.google.com，在上面进行遍历的时候，最后一个求不出相应的结果
		*pHostname = charNum;
		// 将解析好的域名填入发送缓存
		memcpy(DNSsendBuff + sizeof(DNSHDR), hostname, strLen + 2);
		sendByte += strLen + 2;
		// 定义查询的类型
		pqueryHDR->type = htons(0x0001);
		// 定义查询类
		pqueryHDR->queryclass = htons(0x0001);
		// 将域名后的查询类型和查询类进行填入
		memcpy(DNSsendBuff + sizeof(DNSHDR) + strLen + 2, pqueryHDR, sizeof(QUERYHDR));
		sendByte += sizeof(QUERYHDR);
		return sendByte;
	}
}

// 对收到的保存在srecvBuff中的数据进行解析
void decodeDNSPacket(char* DNSrecvBuff) {
	pDNSHDR pdnsHDR = (pDNSHDR)DNSrecvBuff;
	int queryNum, answerNum, authNum, additionNum;
	queryNum = ntohs(pdnsHDR->questNum);		// network to host
	answerNum = ntohs(pdnsHDR->answerNum);
	authNum = ntohs(pdnsHDR->authorNum);
	additionNum = ntohs(pdnsHDR->additionNum);
	// 将flag字段右移十五位，表示是查询报文还是响应报文，0为查询，1为响应
	if (pdnsHDR->flags >> 15) {
		// flags低位值为3，表示服务器回应没有与请求域名相应的记录
		if ((pdnsHDR->flags & 0x0007) == 3) {
			printf("No corresponding domain name entry.\n\n");
			return;
		}
		else {
			// 查看报文头部的AA字段
			if ((pdnsHDR->flags >> 10) == 0x0001) {
				printf("Authoritative answer:\n");
			}
			else {
				printf("None-authoritative answer:\n");
			}
		}

		char* pTraceResponse;
		pTraceResponse = DNSrecvBuff + sizeof(DNSHDR);
		// 将指针移动到域名字段
		while (*pTraceResponse)
		{
			pTraceResponse++;
		}
		// 将指针移动到域名之后
		pTraceResponse++;
		// 跳过查询类型和查询类两个字段，指针指向第一个应答记录
		pTraceResponse += sizeof(long);
		in_addr address;
		pRESPONSE pResponse;
		printf("Address:");
		for (int i = 1; i < answerNum; i++) {
			// 指针跳过应答记录的域名字段
			pTraceResponse += sizeof(short);
			pResponse = (pRESPONSE)pTraceResponse;
			// 如果是响应报文段
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
				// 指向下一个IP地址
				pTraceResponse += sizeof(long);
			}
			// 查询主机的一个别名，直接跳过这一记录字段
			else if (ntohs(pResponse->type) == 5)
			{
				pTraceResponse += sizeof(RESPONSE);
				pTraceResponse += ntohs(pResponse->length);
			}
		}
		printf("\n\n");
	}
	else {
		// 标记字段最高位不为1，表示不是一个应答报文，不做任何处理
		printf("Invalid DNS resolution!\n\n");
	}
}

// 将获得的dns服务器信息保存在dnsserver中，目的是获取本机的dns服务器地址
void getDnsServer(char* dnsServer) {
	// 获得所需要的缓冲区的大小
	DWORD length = 0;
	if (GetNetworkParams(NULL, &length) != ERROR_BUFFER_OVERFLOW) {
		return;
	}

	FIXED_INFO* pFixedInfo = (FIXED_INFO*) new BYTE[length];
	// 获得本地计算机的参数
	if (GetNetworkParams(pFixedInfo, &length) != ERROR_SUCCESS) {
		delete[] pFixedInfo;
		return;
	}
	
	IP_ADDR_STRING* pCurrentDnsServer = &pFixedInfo->DnsServerList;
	if (pCurrentDnsServer != NULL) {
		char* tmp = pCurrentDnsServer->IpAddress.String;	// 本地dns服务器的IP地址
		strcpy(dnsServer, tmp);
	}
}



