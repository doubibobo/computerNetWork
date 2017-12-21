package demo;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by zhuch on 2017/7/6.
 */
// ���Ƕϵ����������
public class FTPServer {
    // �ļ������߳�
    class Sender extends Thread {
        // ����������
        private InputStream in;
        // ���������
        private OutputStream out;
        // ���ص��ļ���
        private String filename;
        // ʣ��Ҫ��ȡ�ĳ���
        private int total;
        // �������ܣ����ڲ��õ��ļ���ʣ�೤��
        public int getRestLength() {
            return total;
        }
        // �����ļ��̹߳��췽��
        public Sender(String filename, Socket socket) {
            try {
                this.out = socket.getOutputStream();
                this.in = socket.getInputStream();
                this.filename = filename;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // ִ�з���
        public void run () {
            try {
                System.out.println("start to download file!");
                int temp = 0;
                StringWriter sw = new StringWriter();
                while ((temp = in.read()) != 0) {
                    sw.write(temp);
                }
                // ��ȡ�ͻ��˵�����
                String cmds = sw.toString();
                System.out.println("cmd: " + cmds);

                // �ж��������
                if ("get".equals(cmds)) {
                    // ��ʼ���ļ�
                    File file = new File(this.filename);
                    System.out.println(file.length());
                    RandomAccessFile accessFile = new RandomAccessFile(file, "r");
                    StringWriter sw1 = new StringWriter();
                    while ((temp = in.read()) != 0) {
                        sw1.write(temp);
                        sw1.flush();
                    }
                    System.out.println(sw1.toString());
                    // ��ȡ�ϵ��λ��
                    int startIndex = 0;
                    if (!sw1.toString().isEmpty()) {
                        startIndex = Integer.parseInt(sw1.toString());
                        long length = file.length();
                        byte[] fileLength = String.valueOf(length).getBytes();
                        out.write(fileLength);
                        out.write(0);
                        out.flush();
                        // ���û�����
                        byte[] buffer = new byte[1024*10];
                        System.out.println("startIndex: " + startIndex);
                        accessFile.skipBytes(startIndex);
                        total = (int)length;
                        while (true) {
                            if (total == 0) {
                                break;
                            }
                            int len = total - startIndex;
                            if (len > buffer.length) {
                                len = buffer.length;
                            }
                            int rlength = accessFile.read(buffer, 0, len);
                            total = total - rlength;
                            if (rlength > 0) {
                                out.write(buffer, 0, rlength);
                                out.flush();
                            } else {
                                break;
                            }
                        }

                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            super.run();
        }
    }

    public void run(String filename, Socket socket) {
        Sender theSender = new Sender(filename, socket);
        theSender.start();
    }
    public static void main(String[] args) {
       try {
           // ���������������˿�
           ServerSocket server = new ServerSocket(8888);
           String fileName = "G:\\hzaucomm.zip";
           for (; ; ) {
               Socket socket = server.accept();
               FTPServer ftpServer = new FTPServer();
               ftpServer.run(fileName, socket);
           }
       } catch (IOException e) {

       }
    }
}
