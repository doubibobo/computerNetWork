#include <WinSock2.h>
#include <iphlpapi.h>	// �ṩһЩ��ȡ������Ϣ�ĺ�����������tcp��ip��
#include <iostream>
using namespace std;


#pragma comment(lib, "iphlpapi.lib")

int main() {
	// adapter ��������IP_ADAPTER_INFO�����洢�������������Ϣ��������ָ������Ϣ
	PIP_ADAPTER_INFO pIpAdapterInfo = new IP_ADAPTER_INFO();
	// ��ýṹ��Ĵ�С������GetAdapterInfo()�Ĳ���
	unsigned long stSize = sizeof(IP_ADAPTER_INFO);
	// ����GetAdapterInfo()����
	int nRel = GetAdaptersInfo(pIpAdapterInfo, &stSize);
	// ��¼����������
	int adapterNumber = 0;
	// ��¼ÿһ�����������IP��ַ��Ŀ
	int ipNumber = 0;
	if (ERROR_BUFFER_OVERFLOW == nRel) {
		// �����������˵��GetAdaptersInfo()�������ݵ��ڴ�ռ䲻����ͬʱ����stSize���洢����Ҫ�ռ�Ĵ�С
		// �ͷ�ԭ���Ŀռ�
		delete pIpAdapterInfo;
		// ���������ڴ�ռ������洢������������Ϣ
		pIpAdapterInfo = (PIP_ADAPTER_INFO) new BYTE[stSize];
		// �ٴε���GetAdapterInfo()����
		int nRel = GetAdaptersInfo(pIpAdapterInfo, &stSize);
		cout << "�����������" << endl;
		cout << nRel << endl;
	} 
	//if (ERROR_SUCCESS == nRel) {
	if (ERROR_BUFFER_OVERFLOW == nRel) {
		// �����������Ϣ����Ϊ����������ѭ�������ж�
		while (pIpAdapterInfo) {
			cout << "������Ŀ��" << ++adapterNumber << endl;
			cout << "�������ƣ�" << pIpAdapterInfo->AdapterName << endl;
			cout << "����������" << pIpAdapterInfo->Description << endl;
			switch (pIpAdapterInfo->Type) 
			{
			case MIB_IF_TYPE_OTHER:
				cout << "�������ͣ�OTHER" << endl;
				break;
			case MIB_IF_TYPE_ETHERNET:
				cout << "�������ͣ�Ethernet" << endl;
				break;
			case MIB_IF_TYPE_TOKENRING:
				cout << "�������ͣ�tokenring" << endl;
				break;
			case MIB_IF_TYPE_FDDI:
				cout << "�������ͣ�fddi" << endl;
				break;
			case MIB_IF_TYPE_PPP:
				cout << "�������ͣ�ppp" << endl;
				break;
			case MIB_IF_TYPE_LOOPBACK:
				cout << "�������ͣ�loopback" << endl;
				break;
			case MIB_IF_TYPE_SLIP:
				cout << "�������ͣ�slip" << endl;
				break;
			default:
				break;
			}
			cout << "����Mac��ַ��";
			for (DWORD i = 0; i < pIpAdapterInfo->AddressLength; i++) {
				if (i<pIpAdapterInfo->AddressLength-1) {
					printf("%02X-", pIpAdapterInfo->Address[i]);
				}
				else {
					printf("%02X-\n", pIpAdapterInfo->Address[i]);
				}
			}
			cout << "����IP��ַ���£�" << endl;
			PIP_ADDR_STRING pIpAddrString = &(pIpAdapterInfo->IpAddressList);
			do {
				cout << "�������ϵ�IP������" << ++ipNumber << endl;
				cout << "IP��ַ��" << pIpAddrString->IpAddress.String << endl;
				cout << "������ַ��" << pIpAddrString->IpMask.String << endl;
				cout << "���ص�ַ��" << pIpAdapterInfo->GatewayList.IpAddress.String << endl;
				pIpAddrString = pIpAddrString->Next;
			} while (pIpAddrString);
			pIpAdapterInfo = pIpAdapterInfo->Next;
			cout << "--------------------------------------------------------------------" << endl;
		}
	}
	// �ͷ��ڴ�ռ�
	if (pIpAdapterInfo) {
		delete pIpAdapterInfo;
	}
	system("pause");
	return 0;
}