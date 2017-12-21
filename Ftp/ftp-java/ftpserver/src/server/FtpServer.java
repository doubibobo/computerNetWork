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

	public static final int SERVER_PORT = 22;	//�������˿ں�

	/**
	 * �������ܣ���ȡ���������������ƺ͵�ַ
	 */
	public void getServerInformation() {
		try {
			System.out.println(InetAddress.getLocalHost());
		} catch (Exception e) {
			System.out.println("Cound not get Server IP." + e);
		}
	}

	/**
	 * �������ܣ������������ķ���
	 */
	public void linkTheServer() {
		try {
			// ���������˿ڣ������Ѿ�ָ����SERVER_PORT�˿�
			s = new ServerSocket(SERVER_PORT);
			ftpServerHahaha = new FtpServerHahaha();
			getServerInformation();
			ftpServerHahaha.jLabel2.setText("�������Ѿ�����");
			ftpServerHahaha.setVisible(true);

			// ��ʼ��������¼�û���Ϣ�Ͳ��������ݱ���Ϣ
			DefaultTableModel defaultTableModel = (DefaultTableModel) ftpServerHahaha.UserOnline.getModel();
			defaultTableModel.setRowCount(0);

			while (true) {
				// �õ��ͻ��˵�socket
				socketOfTheClient = s.accept();
				in = new BufferedReader(new InputStreamReader(socketOfTheClient.getInputStream()));
				out = new PrintWriter(socketOfTheClient.getOutputStream(),true);
				++counter;
				out.println("��  "+counter+" ���û�!");
				//���������߳�
				FtpHandler h = new FtpHandler(socketOfTheClient, ftpServerHahaha);
				h.start();
				users.add(h);
			}
		} catch (BindException e) {
			System.out.println("����������ʧ�ܡ������Ժ�����");
			System.exit(0);
		} catch (IOException e) {
			System.out.println("ERROR! Cound not start server." + e);
		}
	}

	public static void main(String args[]) {
		new FtpServer().linkTheServer();
	}
}
