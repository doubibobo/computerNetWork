package demo;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by zhuch on 2017/7/6.
 */
// 这是断点续传服务端
public class FTPServer {
    // 文件传送线程
    class Sender extends Thread {
        // 网络输入流
        private InputStream in;
        // 网络输出流
        private OutputStream out;
        // 下载的文件名
        private String filename;
        // 剩余要读取的长度
        private int total;
        // 方法功能：从内部得到文件的剩余长度
        public int getRestLength() {
            return total;
        }
        // 发送文件线程构造方法
        public Sender(String filename, Socket socket) {
            try {
                this.out = socket.getOutputStream();
                this.in = socket.getInputStream();
                this.filename = filename;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 执行方法
        public void run () {
            try {
                System.out.println("start to download file!");
                int temp = 0;
                StringWriter sw = new StringWriter();
                while ((temp = in.read()) != 0) {
                    sw.write(temp);
                }
                // 获取客户端的命令
                String cmds = sw.toString();
                System.out.println("cmd: " + cmds);

                // 判断命令序号
                if ("get".equals(cmds)) {
                    // 初始化文件
                    File file = new File(this.filename);
                    System.out.println(file.length());
                    RandomAccessFile accessFile = new RandomAccessFile(file, "r");
                    StringWriter sw1 = new StringWriter();
                    while ((temp = in.read()) != 0) {
                        sw1.write(temp);
                        sw1.flush();
                    }
                    System.out.println(sw1.toString());
                    // 获取断点的位置
                    int startIndex = 0;
                    if (!sw1.toString().isEmpty()) {
                        startIndex = Integer.parseInt(sw1.toString());
                        long length = file.length();
                        byte[] fileLength = String.valueOf(length).getBytes();
                        out.write(fileLength);
                        out.write(0);
                        out.flush();
                        // 设置缓冲区
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
           // 创建服务器监听端口
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
