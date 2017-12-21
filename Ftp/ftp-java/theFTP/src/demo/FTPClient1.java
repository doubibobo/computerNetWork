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
// 这是断点续传客户端
public class FTPClient1 {
    private int finished;
    private Socket socket;

    /**
     * 方法功能：获得一个文件
     * @param filepath
     * @param ftpClient
     * @throws Exception
     */
    public void Get(String filepath, JProgressBar ftpClient) throws Exception {
        socket = new Socket();
        // 建立连接
        socket.connect(new InetSocketAddress("127.0.0.1", 8888));
        // 获取服务流
        OutputStream out = socket.getOutputStream();
        // 获取服务流
        InputStream in = socket.getInputStream();
        // 文件传输协议命令
        // getBytes命令将此String编码为byte序列，并将结果存储到一个新的byte数组中
        byte[] cmd = "get".getBytes();
        out.write(cmd);
        out.write(0);//0作为分隔符
        int startIndex = 0;
        // 要发送的文件
        File file = new File(filepath);
        // 判断文件是否存在的原因是文件存在有两种可能，一种是已经传送完毕，另一种是开始传送，但是没有传送完毕的情况
        if (file.exists()) {
            startIndex = (int)file.length();
        }
        System.out.println("Client startIndex:" + startIndex);
        // 文件写出流
        RandomAccessFile accessFile = new RandomAccessFile(file, "rw");
        // 记录断点，获取本地字符串的表示形式
        out.write(String.valueOf(startIndex).getBytes());
        out.write(0);
        out.flush();
        // 文件长度
        int temp = 0;
        StringWriter sw = new StringWriter();
        // 从服务器端接收数据的操作
        while ((temp = in.read()) != 0) {
            sw.write(temp);
            sw.flush();
        }
        int length = Integer.parseInt(sw.toString());
        System.out.println("Client fileLength : " + length);
        // 二进制文件缓冲区
        byte[] buffer = new byte[1024*10];
        // 剩余要读取的文件的长度
        int total = length - startIndex;
        accessFile.skipBytes(startIndex);
        while(true) {
            // 如果剩余长度为0，则结束
            if (total == 0) {
                break;
            }
            // 假设本次读取的长度为剩余长度
            int len = total;
            if (len > buffer.length) {
                // 缓冲区溢出，修改本次读取的长度为缓冲区的长度
                len = buffer.length;
            }
            // 读取文件，返回真正的读取长度
            int realLength = in.read(buffer, 0, len);
            // 将剩余要读取的长度减去本次要读取的
            total = total - realLength;
            // 将本次读取的文件写入到输出流中
            if (realLength > 0) {
                accessFile.write(buffer, 0, realLength);
            } else {
                break;
            }
            float abc = (float)(length -total) / length*100;
            // 先将浮点数转化为字符串
            // 然后以小数点为界截取字符串
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
        // 关闭相关流
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
     * 主方法
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
