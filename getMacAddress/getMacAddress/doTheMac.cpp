#include <WinSock2.h>
#include <iphlpapi.h>	// 提供一些获取网络信息的函数如网卡、tcp、ip等
#include <iostream>
using namespace std;


#pragma comment(lib, "iphlpapi.lib")

int main() {
	// adapter 适配器，IP_ADAPTER_INFO用来存储适配器的相关信息，在这里指网卡信息
	PIP_ADAPTER_INFO pIpAdapterInfo = new IP_ADAPTER_INFO();
	// 获得结构体的大小，用作GetAdapterInfo()的参数
	unsigned long stSize = sizeof(IP_ADAPTER_INFO);
	// 调用GetAdapterInfo()函数
	int nRel = GetAdaptersInfo(pIpAdapterInfo, &stSize);
	// 记录网卡的数量
	int adapterNumber = 0;
	// 记录每一张网卡上面的IP地址数目
	int ipNumber = 0;
	if (ERROR_BUFFER_OVERFLOW == nRel) {
		// 缓冲区溢出，说明GetAdaptersInfo()参数传递的内存空间不够，同时传出stSize，存储所需要空间的大小
		// 释放原来的空间
		delete pIpAdapterInfo;
		// 重新申请内存空间用来存储所有网卡的信息
		pIpAdapterInfo = (PIP_ADAPTER_INFO) new BYTE[stSize];
		// 再次调用GetAdapterInfo()函数
		int nRel = GetAdaptersInfo(pIpAdapterInfo, &stSize);
		cout << "缓冲区溢出！" << endl;
		cout << nRel << endl;
	} 
	//if (ERROR_SUCCESS == nRel) {
	if (ERROR_BUFFER_OVERFLOW == nRel) {
		// 输出网卡的信息，若为多张网卡，循环进行判断
		while (pIpAdapterInfo) {
			cout << "网卡数目：" << ++adapterNumber << endl;
			cout << "网卡名称：" << pIpAdapterInfo->AdapterName << endl;
			cout << "网卡描述：" << pIpAdapterInfo->Description << endl;
			switch (pIpAdapterInfo->Type) 
			{
			case MIB_IF_TYPE_OTHER:
				cout << "网卡类型：OTHER" << endl;
				break;
			case MIB_IF_TYPE_ETHERNET:
				cout << "网卡类型：Ethernet" << endl;
				break;
			case MIB_IF_TYPE_TOKENRING:
				cout << "网卡类型：tokenring" << endl;
				break;
			case MIB_IF_TYPE_FDDI:
				cout << "网卡类型：fddi" << endl;
				break;
			case MIB_IF_TYPE_PPP:
				cout << "网卡类型：ppp" << endl;
				break;
			case MIB_IF_TYPE_LOOPBACK:
				cout << "网卡类型：loopback" << endl;
				break;
			case MIB_IF_TYPE_SLIP:
				cout << "网卡类型：slip" << endl;
				break;
			default:
				break;
			}
			cout << "网卡Mac地址：";
			for (DWORD i = 0; i < pIpAdapterInfo->AddressLength; i++) {
				if (i<pIpAdapterInfo->AddressLength-1) {
					printf("%02X-", pIpAdapterInfo->Address[i]);
				}
				else {
					printf("%02X-\n", pIpAdapterInfo->Address[i]);
				}
			}
			cout << "网卡IP地址如下：" << endl;
			PIP_ADDR_STRING pIpAddrString = &(pIpAdapterInfo->IpAddressList);
			do {
				cout << "此网卡上的IP数量：" << ++ipNumber << endl;
				cout << "IP地址：" << pIpAddrString->IpAddress.String << endl;
				cout << "子网地址：" << pIpAddrString->IpMask.String << endl;
				cout << "网关地址：" << pIpAdapterInfo->GatewayList.IpAddress.String << endl;
				pIpAddrString = pIpAddrString->Next;
			} while (pIpAddrString);
			pIpAdapterInfo = pIpAdapterInfo->Next;
			cout << "--------------------------------------------------------------------" << endl;
		}
	}
	// 释放内存空间
	if (pIpAdapterInfo) {
		delete pIpAdapterInfo;
	}
	system("pause");
	return 0;
}