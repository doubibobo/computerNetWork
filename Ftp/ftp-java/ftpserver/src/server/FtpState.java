package server;

public class FtpState {
	final static int FS_WAIT_LOGIN = 0;    //等待输入用户名
    final static int FS_WAIT_PASS = 1;    //等待输入密码
    final static int FS_LOGIN = 2;        //已经登陆
    
    final static int FTYPE_ASCII = 0;
    final static int FTYPE_IMAGE  = 1;
}
