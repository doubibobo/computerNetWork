#include <iostream>
#include <string>
#include <cmath>
using namespace std;
// 函数功能：判断一个数的位数
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
	// 用户输入的字符
	char number;
	// 用来进行预算的无符号整数
	unsigned int realDo;
	int crcNumber;
	int theQ = 0;
	cout << "请输入所要传输的数据：" << endl;
	cin >> number;
	cout << "请输入选择的生成多项式：" << endl;
	// 将需要传输的字符转化成便于计算的无符号整数
	realDo = number;
	cout << realDo << endl;
	// 首先根据ASCII码表（0-255），判断字符的大小，进而判断字符的位数，这里面number是4字节，32位
	int bits = getTheBit(number);
	// 设置生成多项式，默认为1101
	int birth = 13;
	int firstFour = 0;
	// 首先进行补零操作，得到真正的被除数
	realDo = realDo << 3;
	// 设置循环的次数，即转化原来的位数 bits
	for (int i = 0; i < bits; i++) {
		// 中间的变量
		unsigned int cache = realDo;
		unsigned int cacheT = realDo;
		// 取得后面的几位数
		cacheT = cacheT << (sizeof(unsigned int)*8 - bits + 1 + i);
		cacheT = cacheT >> (sizeof(unsigned int)*8 - bits + 1 + i);
		// 取得这个数字前面的四位数
		cache = cache >> (bits-1-i);
		// 取得四位数的最高位，此时右移三位
		unsigned int height = cache >> 3;
		cache = height == 1 ? birth ^ cache : 0 ^ cache;
		theQ = (theQ << 1) + height;
		crcNumber = cache;
		cout << "第" << i << "次运算" << endl;
		cout << "异或运算结果 = " << cache << endl;
		// 如果仅仅求余，可以忽略商，得到前面的四位之后，与后面的位数进行合并
		cache = cache << (bits-1-i);
		realDo = cache + cacheT;
		
		cout << "realDo1 = " << realDo << endl;
		// 运算结果左移一位,首先左移sizeof(unsigned int)*8 - (bits + 3)，右移sizeof(unsigned int)*8 - (bits + 3)
		realDo = realDo << (sizeof(unsigned int) * 8 - (bits - 1 + 3 - i));
		realDo = realDo >> (sizeof(unsigned int) * 8 - (bits - 1 + 3 - i));
		cout << "realDo = " << realDo << endl;
		cout << "cacheT = " << cacheT << endl;
	}
	cout << crcNumber << endl;
	cout << theQ << endl;
	// 大致思路：
	// 首先判断待转化字符的位数，根据位数和生成多项式确定商的位数，即需要右方向移动的位数
	// 其次待循环计算完成之后，移动位数即可
	// 商为0还是1由最高位进行决定
	// 要首先输入一个字符的形式
	system("pause");
	return 0;
}
