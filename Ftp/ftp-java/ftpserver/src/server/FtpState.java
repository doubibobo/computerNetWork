package server;

public class FtpState {
	final static int FS_WAIT_LOGIN = 0;    //�ȴ������û���
    final static int FS_WAIT_PASS = 1;    //�ȴ���������
    final static int FS_LOGIN = 2;        //�Ѿ���½
    
    final static int FTYPE_ASCII = 0;
    final static int FTYPE_IMAGE  = 1;
}
