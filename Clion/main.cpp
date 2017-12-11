#include <iostream>
#include <windows.h>

#pragma comment(lib, "Ws2_32.lib")

#define DEF_PACKET_SIZE 32
#define ECHO_REQUEST 8
#define ECHO_REPLY 0

using namespace std;

struct IPHeader{
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

struct ICMPHeader{
    BYTE icmp_type; //icmp的类型
    BYTE icmp_code; //ICMP代码，与type相结合，表示具体的信息
    USHORT icmp_checkSum;   //ICMP校验和
    USHORT icmp_identifier; //ICMP标识符，一般填入本进程的标识符
    USHORT icmp_number;     //ICMP的序号
    ULONG icmp_data;    //ICMP数据部分，4个字节的时间戳
};

struct PINGItems{
    USHORT reply_number;     //序号信息
    DWORD  reply_time;      //记录回复时间
    DWORD  reply_bytes;     //记录字节数目
    DWORD  reply_ttl;       //记录生存时间
};

class Ping{
public:
    Ping();
    ~Ping();
    BOOL CPing(DWORD destIP, PINGItems *pingItems = NULL, DWORD timeout = 2000);
    BOOL CPing(char *destIP, PINGItems *pingItems = NULL, DWORD timeout = 2000);
private:
    BOOL pingCore(DWORD destIP, PINGItems *pingItems, DWORD timeout);
    USHORT calCheckSum(USHORT *pBuffer, int size);      //计算icmp校验和
    ULONG getTickCountCalibrate();
    SOCKET socket;
    WSAEVENT event;
    USHORT userCurrentProcID;   // 定义本进程的ID号
    char *ICMPData;             // 定义icmp的数据段
    BOOL initSucc;              // 定义是否构造成功
    static USHORT PacketNumber; // 定义报文的序号
};

/**
 * 初始化构造函数
 */
Ping::Ping():ICMPData(NULL), initSucc(FALSE){
    // WSADATA被用来存储被WSAStartup函数调用后返回的Windows Sockets数据
    WSADATA wsadata;
    // WSAStartup，即WSA(Windows Sockets Asynchronous，Windows异步套接字)的启动命令
    // MAKEWORD 宏 平台：SDK这个宏创建一个无符号16位整型，通过连接两个给定的无符号参数。
    WSAStartup(MAKEWORD(1, 1), &wsadata);
    // 创建一个初始状态为失信的匿名的需要手动重置的事件对象。
    event = WSACreateEvent();
    userCurrentProcID = (USHORT)GetCurrentProcessId();
    if ((socket = WSASocket(AF_INET, SOCK_RAW, IPPROTO_ICMP,NULL,0,0)) != SOCKET_ERROR) {
        WSAEventSelect(socket, event, FD_READ);
        initSucc = TRUE;
        ICMPData = (char*)malloc(DEF_PACKET_SIZE+ sizeof(ICMPHeader));
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

BOOL Ping::CPing(DWORD destIP, PINGItems *pingItems, DWORD timeout) {
    return pingCore(destIP, pingItems, timeout);
}

BOOL Ping::CPing(char *destIP, PINGItems *pingItems, DWORD timeout) {
    if (NULL != destIP) {
        return pingCore(inet_addr(destIP), pingItems, timeout);
    } else {
        return FALSE;
    }
}

BOOL Ping::pingCore(DWORD destIP, PINGItems *pingItems, DWORD timeout) {
    // 首先判断初始化是否成功
    if (!initSucc) {
        return FALSE;
    }

    // 初始化成功，开始配置socket
    sockaddr_in sockaddrDest;
    sockaddrDest.sin_family = AF_INET;
    sockaddrDest.sin_addr.s_addr = destIP;
    int socketAddrDestSize = sizeof(sockaddrDest);

    // 构建ICMP包
    int ICMPDataSize = sizeof(ICMPHeader)+DEF_PACKET_SIZE;

    // 调用时间戳函数，得到icmp报文段中的时间戳
    ULONG sendTimeStamp = getTickCountCalibrate();
    USHORT thePacketNumber = ++PacketNumber;
    memset(ICMPData, 0, ICMPDataSize);

    ICMPHeader *ptrICMPHeader = (ICMPHeader*)ICMPData;
    ptrICMPHeader->icmp_type = ECHO_REQUEST;
    ptrICMPHeader->icmp_code = 0;
    ptrICMPHeader->icmp_identifier = userCurrentProcID;
    ptrICMPHeader->icmp_number = PacketNumber;
    ptrICMPHeader->icmp_data = sendTimeStamp;
    ptrICMPHeader->icmp_checkSum = calCheckSum((USHORT*)ICMPData, ICMPDataSize);

    // 构造ICMP报文成功，现在使用Socket进行发送
    if (sendto(socket, ICMPData, ICMPDataSize, 0, (struct sockaddr*)&sockaddrDest, socketAddrDestSize) == SOCKET_ERROR) {
        return FALSE;
    }
    // 判断是否需要接收相应报文
    if (pingItems == NULL) {
        return TRUE;
    }
    char buffer[256] = {"\0"};
    while(true) {
        // 接收响应的报文
        if (WSAWaitForMultipleEvents(1, &event, FALSE, 100, FALSE) != WSA_WAIT_TIMEOUT) {
            // 当没有超时的时候
            WSANETWORKEVENTS wsanetworkevents;
            WSAEnumNetworkEvents(socket, event, &wsanetworkevents);
            if (wsanetworkevents.lNetworkEvents & FD_READ) {
                // 得到系统当前时间
                ULONG theTimeStamp = getTickCountCalibrate();
                // 拿到接收消息的报文大小
                int nPacketSize = recvfrom(socket, buffer, 256, 0, (struct sockaddr*)&sockaddrDest, &socketAddrDestSize);
                if (nPacketSize != SOCKET_ERROR) {
                    IPHeader *ipHeader = (IPHeader *)buffer;
                    // 得到IP数据报的首部长度
                    USHORT userIPHeaderLen = (USHORT)((ipHeader->ip_version_IHL & 0x0f) * 4);
                    // 得到ICMP的报文开始段
                    ICMPHeader *icmpHeader = (ICMPHeader*)(buffer + userIPHeaderLen);
                    if (icmpHeader->icmp_identifier == userCurrentProcID && icmpHeader->icmp_type == ECHO_REPLY && icmpHeader->icmp_number == thePacketNumber) {
                        // 如果是当前进程发出的报文、IMCP的响应报文、本次请求的响应报文
                        pingItems->reply_number = thePacketNumber;
                        pingItems->reply_time = theTimeStamp - icmpHeader->icmp_data;
                        pingItems->reply_bytes = nPacketSize - userIPHeaderLen - sizeof(ICMPHeader);
                        pingItems->reply_ttl = ipHeader->ip_ttl;
                        return TRUE;
                    }
                }
            }
        }
        // 超时的情况
        if (getTickCountCalibrate() - sendTimeStamp >= timeout) {
            return FALSE;
        }
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

    LONGLONG currentTimeMs = currentTime.QuadPart/1000;

    if(ulFirstCallTick == 0) {
        // GetTickCount是一种函数。GetTickCount返回（retrieve）从操作系统启动所经过（elapsed）的毫秒数，它的返回值是DWORD。
        ulFirstCallTick = GetTickCount();
    }
    if(ulFirstCallTickMs == 0) {
        ulFirstCallTickMs = currentTimeMs;
    }
    return ulFirstCallTick + (ULONG)(currentTimeMs-ulFirstCallTickMs);
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
    while(size > 1) {
        checkSum = checkSum + *pBuffer++;
        size = size - sizeof(USHORT);
    }
    if(size) {
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
    PINGItems reply;
    cout << "Ping " << destination << " with " << DEF_PACKET_SIZE << " bytes of data:" << endl;
    while(true) {
        testPing.CPing(destination, &reply);
        cout << "Reply from" << destination << ":" << "bytes=" << reply.reply_bytes << " time=" << reply.reply_time << "ms ttl=" << reply.reply_ttl << endl;
        Sleep(500);
    }
    return 0;
}