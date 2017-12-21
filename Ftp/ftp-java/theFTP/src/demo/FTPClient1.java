package demo;

import javax.swing.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

import ftpClient.ftpClient;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

/**
 * Created by zhuch on 2017/7/6.
 */
// ���Ƕϵ������ͻ���
public class FTPClient1 {
    private int finished;
    private Socket socket;

    /**
     * �������ܣ����һ���ļ�
     * @param filepath
     * @param ftpClient
     * @throws Exception
     */
    public void Get(String filepath, JProgressBar ftpClient) throws Exception {
        socket = new Socket();
        // ��������
        socket.connect(new InetSocketAddress("127.0.0.1", 8888));
        // ��ȡ������
        OutputStream out = socket.getOutputStream();
        // ��ȡ������
        InputStream in = socket.getInputStream();
        // �ļ�����Э������
        // getBytes�����String����Ϊbyte���У���������洢��һ���µ�byte������
        byte[] cmd = "get".getBytes();
        out.write(cmd);
        out.write(0);//0��Ϊ�ָ���
        int startIndex = 0;
        // Ҫ���͵��ļ�
        File file = new File(filepath);
        // �ж��ļ��Ƿ���ڵ�ԭ�����ļ����������ֿ��ܣ�һ�����Ѿ�������ϣ���һ���ǿ�ʼ���ͣ�����û�д�����ϵ����
        if (file.exists()) {
            startIndex = (int)file.length();
        }
        System.out.println("Client startIndex:" + startIndex);
        // �ļ�д����
        RandomAccessFile accessFile = new RandomAccessFile(file, "rw");
        // ��¼�ϵ㣬��ȡ�����ַ����ı�ʾ��ʽ
        out.write(String.valueOf(startIndex).getBytes());
        out.write(0);
        out.flush();
        // �ļ�����
        int temp = 0;
        StringWriter sw = new StringWriter();
        // �ӷ������˽������ݵĲ���
        while ((temp = in.read()) != 0) {
            sw.write(temp);
            sw.flush();
        }
        int length = Integer.parseInt(sw.toString());
        System.out.println("Client fileLength : " + length);
        // �������ļ�������
        byte[] buffer = new byte[1024*10];
        // ʣ��Ҫ��ȡ���ļ��ĳ���
        int total = length - startIndex;
        accessFile.skipBytes(startIndex);
        while(true) {
            // ���ʣ�೤��Ϊ0�������
            if (total == 0) {
                break;
            }
            // ���豾�ζ�ȡ�ĳ���Ϊʣ�೤��
            int len = total;
            if (len > buffer.length) {
                // ������������޸ı��ζ�ȡ�ĳ���Ϊ�������ĳ���
                len = buffer.length;
            }
            // ��ȡ�ļ������������Ķ�ȡ����
            int realLength = in.read(buffer, 0, len);
            // ��ʣ��Ҫ��ȡ�ĳ��ȼ�ȥ����Ҫ��ȡ��
            total = total - realLength;
            // �����ζ�ȡ���ļ�д�뵽�������
            if (realLength > 0) {
                accessFile.write(buffer, 0, realLength);
            } else {
                break;
            }
            float abc = (float)(length -total) / length*100;
            // �Ƚ�������ת��Ϊ�ַ���
            // Ȼ����С����Ϊ���ȡ�ַ���
            String the = String.valueOf(abc);
            int index = the.lastIndexOf(".");
            String strNum = the.substring(0, index);
            finished = Integer.valueOf(strNum);

            System.out.println(finished);
            ftpClient.setValue(finished);
            System.out.println(ftpClient.getValue()+"ftpclient");
            System.out.println("finished = " + finished);
            System.out.println("finish : " + ((float)(length -total) / length) *100 + " %");
        }
        System.out.println("finished");
        // �ر������
        accessFile.close();
        out.close();
        in.close();
    }

    public void stop () {
        try {
            this.socket.close();
        } catch (IOException E) {
            E.printStackTrace();
        }
    }
    public int getFinished () {
        return finished;
    }

    /**
     * ������
     * @param args
     */
//    public static void main(String[] args) {
//        FTPClient1 client = new FTPClient1();
//        try {
//            client.Get("G:\\ff");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
