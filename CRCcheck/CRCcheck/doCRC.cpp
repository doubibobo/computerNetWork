#include <iostream>
#include <string>
#include <cmath>
using namespace std;
// �������ܣ��ж�һ������λ��
int getTheBit(int number) {
	int count = 0;
	for (int i = 0; i < 31; i++) {
		if (number >= pow(2, i) && number < pow(2, i+1)) {
			count = i+1;
			break;
		}
	}
	return count;
}
int main() {
	// �û�������ַ�
	char number;
	// ��������Ԥ����޷�������
	unsigned int realDo;
	int crcNumber;
	int theQ = 0;
	cout << "��������Ҫ��������ݣ�" << endl;
	cin >> number;
	cout << "������ѡ������ɶ���ʽ��" << endl;
	// ����Ҫ������ַ�ת���ɱ��ڼ�����޷�������
	realDo = number;
	cout << realDo << endl;
	// ���ȸ���ASCII���0-255�����ж��ַ��Ĵ�С�������ж��ַ���λ����������number��4�ֽڣ�32λ
	int bits = getTheBit(number);
	// �������ɶ���ʽ��Ĭ��Ϊ1101
	int birth = 13;
	int firstFour = 0;
	// ���Ƚ��в���������õ������ı�����
	realDo = realDo << 3;
	// ����ѭ���Ĵ�������ת��ԭ����λ�� bits
	for (int i = 0; i < bits; i++) {
		// �м�ı���
		unsigned int cache = realDo;
		unsigned int cacheT = realDo;
		// ȡ�ú���ļ�λ��
		cacheT = cacheT << (sizeof(unsigned int)*8 - bits + 1 + i);
		cacheT = cacheT >> (sizeof(unsigned int)*8 - bits + 1 + i);
		// ȡ���������ǰ�����λ��
		cache = cache >> (bits-1-i);
		// ȡ����λ�������λ����ʱ������λ
		unsigned int height = cache >> 3;
		cache = height == 1 ? birth ^ cache : 0 ^ cache;
		theQ = (theQ << 1) + height;
		crcNumber = cache;
		cout << "��" << i << "������" << endl;
		cout << "��������� = " << cache << endl;
		// ����������࣬���Ժ����̣��õ�ǰ�����λ֮��������λ�����кϲ�
		cache = cache << (bits-1-i);
		realDo = cache + cacheT;
		
		cout << "realDo1 = " << realDo << endl;
		// ����������һλ,��������sizeof(unsigned int)*8 - (bits + 3)������sizeof(unsigned int)*8 - (bits + 3)
		realDo = realDo << (sizeof(unsigned int) * 8 - (bits - 1 + 3 - i));
		realDo = realDo >> (sizeof(unsigned int) * 8 - (bits - 1 + 3 - i));
		cout << "realDo = " << realDo << endl;
		cout << "cacheT = " << cacheT << endl;
	}
	cout << crcNumber << endl;
	cout << theQ << endl;
	// ����˼·��
	// �����жϴ�ת���ַ���λ��������λ�������ɶ���ʽȷ���̵�λ��������Ҫ�ҷ����ƶ���λ��
	// ��δ�ѭ���������֮���ƶ�λ������
	// ��Ϊ0����1�����λ���о���
	// Ҫ��������һ���ַ�����ʽ
	system("pause");
	return 0;
}
