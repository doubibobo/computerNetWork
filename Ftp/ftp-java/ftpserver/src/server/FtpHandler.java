package server;

import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.net.*;
import java.util.*;

class FtpHandler extends Thread {
	private Socket ctrlSocket = null; // ���ƶ�
	private Socket dataSocket = null; // ���ݶ�
	private String cmd = new String(""); // ָ������
	private String param = new String(""); // ָ�����
	private String user = new String("");
	private String password = new String("");
	private String remoteHost = new String(""); // �ͻ���ַ
	private int remotePort = 0;
	private String dir = FtpServer.initDir;// Ĭ��Ŀ¼ΪD��
	private String rootdir = "D:/"; // Ĭ�ϸ�Ŀ¼
	private int state = 0; // �û������ָ������
	private String reply; // ������Ϣ
	private PrintWriter ctrlOutput;
	private int type = 0; // �ļ�����
	private String requestfile = "";// ��Զ�̷�������Ҫ���ص��ļ���ַ
	private boolean isrest = false;
	private ServerSocket serverSocket;
	private BufferedInputStream bis;
	private FileOutputStream fos;
	private PrintWriter pw;

	private FileInputStream fis;
	private PrintStream ps;

	private BufferedReader in;
	private PrintWriter out;

	private static ArrayList<String> onlineUser = new ArrayList<String>();
	private static ArrayList<Socket> socketUser = new ArrayList<Socket>();

	private final String USERLIST_FILE = "user.txt";	// �趨����û���Ϣ���ļ�

	private FtpServerHahaha serverHahaha = null;

	/**
	 * ���췽��
	 * @param s
	 * @param serverHahaha1
	 * @throws IOException
	 */
	public FtpHandler(Socket s, FtpServerHahaha serverHahaha1) throws IOException {
		ctrlSocket = s;
		serverHahaha = serverHahaha1;
	}

	/**
	 * �������߳�ִ�з���
	 */
	public void run() {
		String str = "";
		int parseResult;
		try {
			BufferedReader ctrlInput = new BufferedReader(new InputStreamReader(ctrlSocket.getInputStream()));
			ctrlOutput = new PrintWriter(ctrlSocket.getOutputStream(), true);
			remoteHost = ctrlSocket.getInetAddress().getHostAddress();
			state = FtpState.FS_WAIT_LOGIN;
			boolean finished = false;
			while (!finished) {
				str = ctrlInput.readLine();
				if (str == null)
					finished = true;
				else {
					parseResult = parseInput(str); // ָ��ת��Ϊָ���
					theLog(user, "ָ��:" + cmd + " ����:" + param);
					if(state == FtpState.FS_WAIT_LOGIN){
						if(parseResult == 27)
							finished = commandLIST(); //��δ��¼���������ʾ�ļ��б�
						else if(parseResult == 14)
							finished = commandRETR1();
						else
							finished = commandUSER();
					}
					else if(state == FtpState.FS_WAIT_PASS)
						finished = commandPASS();
					else if(state == FtpState.FS_LOGIN){
						switch (parseResult)// ָ��ſ���,���������Ƿ�������еĹؼ�
						{
						case -1:
							errCMD(); // �﷨��
							break;
						case 4:
							finished = commandCDUP(); // ����һ��Ŀ¼
							break;
						case 6:
							finished = commandCWD(); // ��ָ����Ŀ¼
							break;
						case 7:
							finished = commandQUIT(); // �˳�
							break;
						case 9:
							finished = commandPORT(); // �ͻ���IP:��ַ+TCP �˿ں�
							break;
						case 11:
							finished = commandTYPE(); // �ļ���������(ascII �� bin)
							break;
						case 14:
							finished = commandRETR1(); // �ӷ������л���ļ�
							break;
						case 15:
							finished = commandSTOR(); // ��������з����ļ�
							break;
						case 22:
							finished = commandABOR(); // �رմ���������dataSocket
							break;
						case 23:
							finished = commandDELE(); // ɾ���������ϵ�ָ���ļ�
							break;
						case 25:
							finished = commandMKD(); // ����Ŀ¼
							break;
						case 27:
							finished = commandLIST(); // �ļ���Ŀ¼���б�
							break;
						case 26:
						case 33:
							finished = commandPWD(); // "��ǰĿ¼" ��Ϣ
							break;
						case 32:
							finished = commandNOOP(); // "������ȷ" ��Ϣ
							break;
						case 100:
							finished = commandPASV();
							break;
						}
		
					}
						
				}
				ctrlOutput.println(reply);
				ctrlOutput.flush();
			}
			ctrlSocket.close();
		} catch (Exception e) {
			// e.printStackTrace();
			theLog(user, "���ӶϿ�");
		}
//		} catch (IOException e) { // �û��رտͻ�����ɴ��쳣���رո��û��׽��֡�
//			String leaveUser = closeSocket();
//			Date t = new Date();
//			log("�û�" + leaveUser + "�Ѿ��뿪, " + "�˳�ʱ��:" + t);	
//			try {
//			} catch (IOException e1) {
//				e1.printStackTrace();
//			}
//			System.out.println("[SYSTEM] " + leaveUser + " ���뿪!");
//		}
	}

	/**
	 * ���ݿ�����Ϣ���������ź�ת��Ϊ�ַ�����Ϣ��theControlString��
	 * @param theControlString
	 * @return
	 */
	int parseInput(String theControlString) {
		int p = 0;
		int i = -1;
		// p�����������ָ��Ϳ��Ʋ����ķֽ����Ϣ
		p = theControlString.indexOf(" ");
		if (p == -1) {
			cmd = theControlString;
		} else {
			cmd = theControlString.substring(0, p);
		}
		if (p >= theControlString.length() || p == -1) {
			param = "";
		} else {
			param = theControlString.substring(p + 1, theControlString.length());
		}
		cmd = cmd.toUpperCase();

		switch (cmd) {
			case "CDUP": i = 4; break;
			case "CWD": i = 6; break;
			case "QUIT": i = 7; break;
			case "PORT": i = 9; break;
			case "TYPE": i = 11; break;
			case "RETR": i = 14; break;
			case "STOR": i = 15; break;
			case "ABOR": i = 22; break;
			case "DELE": i = 23; break;
			case "MKD": i = 25; break;
			case "PWD": i = 26; break;
			case "LIST": i = 27; break;
			case "NOOP": i = 32; break;
			case "XPWD": i = 33; break;
			case "PASV": i = 100; break;
		}
		return i;
	}

	/**
	 * �ж��û����Ƿ���ȷ
	 * @return
	 */
	boolean commandUSER() {
		if (cmd.equals("USER")) {
			reply = "331 �û�����ȷ,����������";
			user = param;
			state = FtpState.FS_WAIT_PASS;
			return false;
		} else {
			reply = "501 �����﷨����,�û�����ƥ��";
			return false;
		}
	}

	/**
	 * �ж��û������Ƿ���ȷ
	 * @return
	 */
	boolean commandPASS() {
		password = param;
		if(isUserLogin(user, password)){
			reply = "230 �û���¼";
			state = FtpState.FS_LOGIN;
			theLog("�û�: " + user + "  ���룺" + password + "  IP: " + remoteHost,  "��¼");
			onlineUser.add(user);
			return false;
		} else {
			reply = "501 �����﷨����,���벻ƥ��";
			state = FtpState.FS_WAIT_LOGIN;
			return false;
		}
	}

	/**
	 * �����ź��������
	 */
	void errCMD() {
		reply = "500 �﷨����";
	}

	/**
	 * �������ܣ����ص���һ��Ŀ¼
	 * @return
	 */
	boolean commandCDUP() {
		dir = FtpServer.initDir;
		File f = new File(dir);
		// ��ǰ�ļ��еĸ��ļ��в�Ϊ�ն��Ҳ��Ǵ��̸�Ŀ¼
		if (f.getParent() != null && (!dir.equals(rootdir))) {
			dir = f.getParent();
			reply = "200 ������ȷ";
		} else {
			reply = "550 ��ǰĿ¼�޸�·��";
		}

		return false;
	}

	/**
	 * �������ܣ����Ĺ���Ŀ¼
	 * @return
	 */
	boolean commandCWD() {
		// ������ı乤��Ŀ¼���û�ָ����Ŀ¼
		File f = new File(param);
		String s = "";
		String s1 = "";
		if (dir.endsWith("/"))
			s = dir;
		else
			s = dir + "/";
		File f1 = new File(s + param);

		if (f.isDirectory() && f.exists()) {
			if (param.equals("..") || param.equals("..\\")) {
				if (dir.compareToIgnoreCase(rootdir) == 0) {
					reply = "550 ��·��������";
					// return false;
				} else {
					s1 = new File(dir).getParent();
					if (s1 != null) {
						dir = s1;
						theLog(user, "�ı�Ŀ¼�ɹ�");
						reply = "250 ������ļ��������, ��ǰĿ¼��Ϊ: " + dir;
					} else
						reply = "550 ��·��������";
				}
			} else if (param.equals(".") || param.equals(".\\")) {
			} else {
				dir = param;
				theLog(user, "�ı�Ŀ¼�ɹ�");
				reply = "250 ������ļ��������, ����·����Ϊ " + dir;
			}
		} else if (f1.isDirectory() && f1.exists()) {
			dir = s + param;
			theLog(user, "�ı�Ŀ¼�ɹ�");
			reply = "250 ������ļ��������, ����·����Ϊ " + dir;
		} else
			reply = "501 �����﷨����";

		return false;
	}

	/**
	 * �������ܣ��˳�����
	 * @return
	 */
	boolean commandQUIT() {
		String tmp;
		boolean rm = false;
		for(int i = 0; i < onlineUser.size(); ++i){
			tmp = onlineUser.get(i);
			if(tmp != null && tmp.equals(user)){
				onlineUser.remove(i);
				rm = true;
				break;
			}
		}
		if(rm){
			theLog(user, "���˳���¼");
		}
		reply = "221 ����ر�����";
		return true;
	}

	/*
	 * ʹ�ø�����ʱ���ͻ��˱��뷢�Ϳͻ������ڽ������ݵ�32λIP ��ַ��16λ ��TCP �˿ںš�
	 * ��Щ��Ϣ��8λΪһ�飬ʹ��ʮ���ƴ��䣬�м��ö��Ÿ�����
	 */
	boolean commandPORT() {
		int p1 = 0;
		int p2 = 0;
		int[] a = new int[6];// ���ip+tcp
		int i = 0; //
		try {
			while ((p2 = param.indexOf(",", p1)) != -1)// ǰ5λ
			{
				a[i] = Integer.parseInt(param.substring(p1, p2));
				p2 = p2 + 1;
				p1 = p2;
				i++;
			}
			a[i] = Integer.parseInt(param.substring(p1, param.length()));// ���һλ
		} catch (NumberFormatException e) {
			reply = "501 �����﷨����";
			return false;
		}

		remoteHost = a[0] + "." + a[1] + "." + a[2] + "." + a[3];
		remotePort = a[4] * 256 + a[5];
		reply = "200 ������ȷ";
		return false;
	}

	/**
	 * ���ñ�����ʽ���д���
	 * @return
	 */
	boolean commandPASV() {
		try {
			serverSocket = new ServerSocket(33333);
			dataSocket = serverSocket.accept();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		reply = "227 ���뱻��ģʽ";
		System.out.println("���뱻��ģʽ");
		return false;
	}


	/**
	 * �������ܣ����diskFile�����е��ļ�����
	 * @param diskFile
	 * @return
	 */
	private File[] getAllTheServerFile(File diskFile) {
		// �����洢Զ�̵Ĵ����ļ��б�Ĭ����D�̼����Ʒ���Ȩ����D��
		File selDisk = new File("D:");
		File[] files = selDisk.listFiles();
		return files;
	}

	/**
	 * �����ļ���Ŀ¼���б�
	 * @return
	 */
	boolean commandLIST() {
		try {
			serverSocket = new ServerSocket(4700);
			Socket socket = serverSocket.accept();
			pw = new PrintWriter(socket.getOutputStream());
			File f = new File(dir);

			String[] dirStructure = f.list();// ָ��·���е��ļ�������,��������ǰ·����·��
			// ����ļ��ڷ������ϵ�·�����ڿͻ��˱���
			String[] thePathOfAllTheSonFiles = new String[dirStructure.length];
			boolean[] isOrNotDirectory = new boolean[dirStructure.length];
			int count = 0;
			File[] allTheSonFile = f.listFiles();

			for (File theFileName : allTheSonFile) {
				System.out.println(theFileName.getAbsolutePath());
				thePathOfAllTheSonFiles[count] = theFileName.getAbsolutePath();
				// �ж�Զ��Ŀ¼�Ƿ�Ϊ�ļ���
				if (theFileName.isDirectory()) {
					isOrNotDirectory[count] = true;
				} else {
					isOrNotDirectory[count] = false;
				}
				count++;
			}

			for(int i = 0; i < dirStructure.length; ++i){
				pw.println(dirStructure[i]);
				pw.println(thePathOfAllTheSonFiles[i]);
				pw.println(isOrNotDirectory[i]);
			}
			pw.close();
			serverSocket.close();
			theLog(user, "��ʾ�ļ��б�");
			reply = "226 �����������ӽ���";

			// ������ʽ���еĲ����������������ӿͻ���
//				dataSocket = new Socket(remoteHost, remotePort, InetAddress.getLocalHost(), 20);
//				PrintWriter dout = new PrintWriter(dataSocket.getOutputStream(), true);
//				if (param.equals("") || param.equals("LIST")) {
//					ctrlOutput.println("150 �ļ�״̬����,ls�� ASCII ��ʽ����");
//					File f = new File(dir);
//					String[] dirStructure = f.list();// ָ��·���е��ļ�������,��������ǰ·����·��
//					String fileType;
//					for (int i = 0; i < dirStructure.length; i++) {
//						if (dirStructure[i].indexOf(".") != -1) {
//							fileType = "- "; // ��Ŀ¼(��linux��)
//						} else {
//							fileType = "d "; // ��Ŀ¼���ļ�����Ŀ¼
//						}
//						dout.println(dirStructure[i]);// (fileType+dirStructure[i]);
//					}
//				}
//				dout.close();
//				dataSocket.close();
//				reply = "226 �����������ӽ���";
		} catch (Exception e) {
			e.printStackTrace();
			reply = "451 �ļ��������";
			return false;
		}
		return false;
	}

	/**
	 * ִ�д����ļ���ʽ������
	 * @return
	 */
	boolean commandTYPE() {
		if (param.equals("A")) {
			type = FtpState.FTYPE_ASCII;// 0
			reply = "200 ת ASCII ģʽ";
		} else if (param.equals("I")) {
			type = FtpState.FTYPE_IMAGE;// 1
			reply = "200 ת BINARY ģʽ";
		} else {
			reply = "504 �����ִ�����ֲ���";
		}
		return false;
	}

	/**
	 * ���öϵ������ķ�ʽ�ӷ������˻���ļ�
	 * @return
	 */
	boolean commandRETR1() {
		// param �����õ���ǰָ��Ŀ�����Ϣ�����õ���Ӧ���ļ�
		requestfile = param;
		File f = new File(requestfile);
		if (! f.exists()) {
			reply = "550 �ļ�������";
			return false;
		}
		try {
			serverSocket = new ServerSocket(4878);
			Socket socket = serverSocket.accept();

			OutputStream downloadOut = socket.getOutputStream();
			InputStream downloadIn = socket.getInputStream();

			try {
				RandomAccessFile accessFile = new RandomAccessFile(f, "r");
				int temp = 0;
				// ���տͻ��˷��͹����Ķϵ��λ��
				StringWriter sw = new StringWriter();
				while ((temp = downloadIn.read()) != 0) {
					sw.write(temp);
					sw.flush();
				}
				System.out.println(sw.toString());

				// �趨�ϵ��λ��
				int startIndex = 0;

				if (! sw.toString().isEmpty()) {
					startIndex = Integer.parseInt(sw.toString());
					System.out.println(startIndex);
					// �ļ����ܳ���
					long length = f.length();
					// ���ļ����ܳ������ַ�������ʽ����
					byte[] fileLength = String.valueOf(length).getBytes();
					downloadOut.write(fileLength);
					downloadOut.write(0);
					downloadOut.flush();

					// ���ö�ȡ�ļ��Ļ���������10BΪ����
					byte[] buffer = new byte[1024*10];
					System.out.println("startIndex: " + startIndex);
					accessFile.skipBytes(startIndex);
					// Ҫʣ���ļ����ܳ���
					int total = (int) length - startIndex;
					while(true) {
						if (total == 0) {
							break;
						}
						// Ҫ�ϴ���ʣ����ܳ���
						int len = total;
						if (len > buffer.length) {
							len = buffer.length;
						}
						// ����ʵ�ʴ��͵ĳ���
						int realLength = accessFile.read(buffer, 0, len);
						// ����ʣ����Ŀ�ĳ���
						total = total - realLength;
						if (realLength > 0) {
							downloadOut.write(buffer, 0, realLength);
							downloadOut.flush();
						} else {
							break;
						}
						System.out.println(realLength);
						System.out.println(total);
					}
					accessFile.close();
				}
				downloadIn.close();
				downloadOut.flush();
				downloadOut.close();
				serverSocket.close();
				theLog(user, "�ļ����سɹ�");
				reply = "226 �����������ӽ���";
			} catch (IOException EE) {
				EE.printStackTrace();
			}

		} catch (IOException E) {
			E.printStackTrace();
			reply = "451 ����ʧ��: ���������";
			return false;
		}
		System.out.println(f.length());
		return false;
	}

	/**
	 * �����öϵ��������ӷ������˿ڻ���ļ�
	 * @return
	 */
	boolean commandRETR() {
		requestfile = param;
		File f = new File(requestfile);
		// �����жϷ������˵��ļ��Ƿ����
		if (!f.exists()) {
			reply = "550 �ļ�������";
			return false;
		}
		try {
			serverSocket = new ServerSocket(4700);
			Socket socket = serverSocket.accept();
			bis = new BufferedInputStream(new FileInputStream(requestfile));
			ps = new PrintStream(socket.getOutputStream());
			byte[] buf = new byte[1024];
			int l = 0;
			while ((l = bis.read(buf, 0, 1024)) != -1) {
				ps.write(buf, 0, l);
			}
			bis.close();
			ps.flush();
			ps.close();
			serverSocket.close();
			theLog(user, "�ļ����سɹ�");
			reply = "226 �����������ӽ���";
		} catch (IOException e) {
			e.printStackTrace();
			reply = "451 ����ʧ��: ���������";
			return false;
		}
//			if (isrest) {
		//
//					} else {
//						if (type == FtpState.FTYPE_IMAGE) // bin
//						{
//							try {
//								ctrlOutput.println("150 �ļ�״̬����,�Զ����η�ʽ���ļ�:  " + requestfile);
//								dataSocket = new Socket(remoteHost, remotePort, InetAddress.getLocalHost(), 20);
//								BufferedInputStream fin = new BufferedInputStream(new FileInputStream(requestfile));
//								PrintStream dataOutput = new PrintStream(dataSocket.getOutputStream(), true);
//								byte[] buf = new byte[1024]; // Ŀ�껺����
//								int l = 0;
//								while ((l = fin.read(buf, 0, 1024)) != -1) // ������δ����
//								{
//									dataOutput.write(buf, 0, l); // д���׽���
//								}
//								fin.close();
//								dataOutput.close();
//								dataSocket.close();
//								reply = "226 �����������ӽ���";
		//
//							} catch (Exception e) {
//								e.printStackTrace();
//								reply = "451 ����ʧ��: ���������";
//								return false;
//							}
		//
//						}
//						if (type == FtpState.FTYPE_ASCII)// ascII
//						{
//							try {
//								ctrlOutput.println("150 Opening ASCII mode data connection for " + requestfile);
//								dataSocket = new Socket(remoteHost, remotePort, InetAddress.getLocalHost(), 20);
//								BufferedReader fin = new BufferedReader(new FileReader(requestfile));
//								PrintWriter dataOutput = new PrintWriter(dataSocket.getOutputStream(), true);
//								String s;
//								while ((s = fin.readLine()) != null) {
//									dataOutput.println(s); /// ???
//								}
//								fin.close();
//								dataOutput.close();
//								dataSocket.close();
//								reply = "226 �����������ӽ���";
//							} catch (Exception e) {
//								e.printStackTrace();
//								reply = "451 ����ʧ��: ���������";
//								return false;
//							}
//						}
//					}
				return false;
			}

	/**
	 * �ϴ��ļ�����
	 * @return
	 */
	boolean commandSTOR() {
		if (param.equals("")) {
			reply = "501 �����﷨����";
			return false;
		}
		requestfile = addTail(dir) + param;
		try {
			serverSocket = new ServerSocket(4700);
			Socket socket = serverSocket.accept();
			bis = new BufferedInputStream(socket.getInputStream());
			fos = new FileOutputStream(requestfile);
			byte[] buf = new byte[1024];
			int l = 0;
			while ((l = bis.read(buf, 0, 1024)) != -1) {
				fos.write(buf, 0, l);
			}
			bis.close();
			fos.flush();
			fos.close();
			serverSocket.close();
			theLog(user, "�ļ��洢�ɹ�");
			reply = "226 �����������ӽ���";
		} catch (IOException e) {
			e.printStackTrace();
			reply = "451 ����ʧ��: ���������";
			return false;
		}
//			if (type == FtpState.FTYPE_IMAGE)// bin
//			{
//				try {
//					ctrlOutput.println("150 Opening Binary mode data connection for " + requestfile);
//					dataSocket = new Socket(remoteHost, remotePort, InetAddress.getLocalHost(), 20);
//					BufferedOutputStream fout = new BufferedOutputStream(new FileOutputStream(requestfile));
//					BufferedInputStream dataInput = new BufferedInputStream(dataSocket.getInputStream());
//					byte[] buf = new byte[1024];
//					int l = 0;
//					while ((l = dataInput.read(buf, 0, 1024)) != -1) {
//						fout.write(buf, 0, l);
//					}
//					dataInput.close();
//					fout.close();
//					dataSocket.close();
//					reply = "226 �����������ӽ���";
//				} catch (Exception e) {
//					e.printStackTrace();
//					reply = "451 ����ʧ��: ���������";
//					return false;
//				}
//			}
//			if (type == FtpState.FTYPE_ASCII)// ascII
//			{
//				try {
//					ctrlOutput.println("150 Opening ASCII mode data connection for " + requestfile);
//					dataSocket = new Socket(remoteHost, remotePort, InetAddress.getLocalHost(), 20);
//					PrintWriter fout = new PrintWriter(new FileOutputStream(requestfile));
//					BufferedReader dataInput = new BufferedReader(new InputStreamReader(dataSocket.getInputStream()));
//					String line;
//					while ((line = dataInput.readLine()) != null) {
//						fout.println(line);
//					}
//					dataInput.close();
//					fout.close();
//					dataSocket.close();
//					reply = "226 �����������ӽ���";
//				} catch (Exception e) {
//					e.printStackTrace();
//					reply = "451 ����ʧ��: ���������";
//					return false;
//				}
//			}
		return false;
	}
		
	boolean commandPWD() {
		theLog(user,"��ʾĿ¼�ɹ�");
		reply = "257 " + "\"" + dir + "\"" + " �ǵ�ǰĿ¼.";
		return false;
	}
		
	boolean commandNOOP() {
		reply = "200 ������ȷ.";
		return false;
	}
		
	// ǿ��dataSocket ��
	boolean commandABOR() {
		try {
			dataSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
			reply = "451 ����ʧ��: ���������";
			return false;
		}
		reply = "421 ���񲻿���, �ر����ݴ�������";
		return false;
	}

	/**
	 * ͨ���ݹ�ɾ��Զ�̷������ϵ��ļ�
	 * @param f
	 * @return
	 */
	public static boolean deleteFile(File f){
		if(f.isDirectory()){
			String[] children = f.list();
			for(int i = 0; i < children.length; ++i){
				boolean success = deleteFile(new File(f, children[i]));
				if(!success){
					return false;
				}
			}
		}
		return f.delete();
	}

	/**
	 * ɾ���������ϵ��ض��ļ����Ӵ����л�ȡ�ļ���
	 * @return
	 */
	boolean commandDELE() {
		File f = new File(param);
		if(deleteFile(f)){
			theLog(user,"ɾ���ļ��ɹ�");
			reply = "250 ������ļ��������,�ɹ�ɾ�����������ļ�";
		}
		else{
			theLog(user,"ɾ���ļ�ʧ��");
			reply = "333 ���ļ�������������ռ�ã�ɾ��ʧ��";
		}
		return false;
	}

	/**
	 * �½��ļ���
	 * @return
	 */
	boolean commandMKD() {
		String s1 = param.toLowerCase();
		String s2 = rootdir.toLowerCase();
		if (s1.startsWith(s2)) {
			File f = new File(param);
			if (f.exists()) {
				reply = "550 ����Ķ���δִ��,Ŀ¼�Ѵ���";
				return false;
			} else {
				f.mkdirs();
				theLog(user, "����Ŀ¼�ɹ�");
				reply = "250 ������ļ��������, Ŀ¼����";
			}
		} else {
			File f = new File(addTail(dir) + param);
			if (f.exists()) {
				reply = "550 ����Ķ���δִ��,Ŀ¼�Ѵ���";
				return false;
			} else {
				f.mkdirs();
				theLog(user, "����Ŀ¼�ɹ�");
				reply = "250 ������ļ��������, Ŀ¼����";
			}
		}
		return false;
	}

	/**
	 * �ж��ļ����û������������
	 * @param name
	 * @param password
	 * @return
	 */
	private boolean isUserLogin(String name, String password) {
		String strRead;
		try {
			FileInputStream inputfile = new FileInputStream(USERLIST_FILE);
			BufferedReader inputdata = new BufferedReader(new InputStreamReader(inputfile));
			while ((strRead = inputdata.readLine()) != null) {
				if (strRead.equals(user + "|" + password)) {
					return true;
				}
			}
		} catch (FileNotFoundException fn) {
			System.out.println("[ERROR] User File has not exist!" + fn);
			out.println("warning|��д�ļ�ʱ����!");
		} catch (IOException ie) {
			System.out.println("[ERROR] " + ie);
			out.println("warning|��д�ļ�ʱ����!");
		}
		return false;
	}

	/**
	 * ��¼��¼��Ա����Ϣ
	 * @param UserName
	 * @param theUserIP
	 */
	public void theLog(String UserName, String theUserIP) {
		// ��ȡ��������ģ��
		DefaultTableModel defaultTableModel = (DefaultTableModel) this.serverHahaha.UserOnline.getModel();
		defaultTableModel.addRow(new Object[] {UserName, theUserIP});
	}

	/**
	 * �ͷ�����
	 * @return
	 */
	private String closeSocket() {
		String strUser = "";
		for (int i = 0; i < socketUser.size(); i++) {
			if (ctrlSocket.equals((Socket) socketUser.get(i))) {
				strUser = onlineUser.get(i).toString();
				socketUser.remove(i);
				onlineUser.remove(i);
			}
		}
		try {
			in.close();
			out.close();
			ctrlSocket.close();
		} catch (IOException e) {
			System.out.println("[ERROR] " + e);
		}

		return strUser;
	}
	
	String addTail(String s) {
		if (!s.endsWith("/"))
			s = s + "/";
		return s;
	}
}