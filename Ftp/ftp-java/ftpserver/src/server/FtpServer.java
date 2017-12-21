package server;

import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class FtpServer {
	public static String initDir = "D:/";
	public static ArrayList<FtpHandler> users = new ArrayList<FtpHandler>();
	private static int counter = 0;
	BufferedReader in;
	PrintWriter out;
	Socket socketOfTheClient;
	ServerSocket s;

	FtpServerHahaha ftpServerHahaha = null;

	public static final int SERVER_PORT = 22;	//服务器端口号

	/**
	 * 方法功能：获取服务器的主机名称和地址
	 */
	public void getServerInformation() {
		try {
			System.out.println(InetAddress.getLocalHost());
		} catch (Exception e) {
			System.out.println("Cound not get Server IP." + e);
		}
	}

	/**
	 * 方法功能：启动服务器的方法
	 */
	public void linkTheServer() {
		try {
			// 创建监听端口，监听已经指定的SERVER_PORT端口
			s = new ServerSocket(SERVER_PORT);
			ftpServerHahaha = new FtpServerHahaha();
			getServerInformation();
			ftpServerHahaha.jLabel2.setText("服务器已经启动");
			ftpServerHahaha.setVisible(true);

			// 初始化用来记录用户信息和操作的数据表信息
			DefaultTableModel defaultTableModel = (DefaultTableModel) ftpServerHahaha.UserOnline.getModel();
			defaultTableModel.setRowCount(0);

			while (true) {
				// 得到客户端的socket
				socketOfTheClient = s.accept();
				in = new BufferedReader(new InputStreamReader(socketOfTheClient.getInputStream()));
				out = new PrintWriter(socketOfTheClient.getOutputStream(),true);
				++counter;
				out.println("第  "+counter+" 个用户!");
				//创建服务线程
				FtpHandler h = new FtpHandler(socketOfTheClient, ftpServerHahaha);
				h.start();
				users.add(h);
			}
		} catch (BindException e) {
			System.out.println("服务器启动失败……请稍后再试");
			System.exit(0);
		} catch (IOException e) {
			System.out.println("ERROR! Cound not start server." + e);
		}
	}

	public static void main(String args[]) {
		new FtpServer().linkTheServer();
	}
}
