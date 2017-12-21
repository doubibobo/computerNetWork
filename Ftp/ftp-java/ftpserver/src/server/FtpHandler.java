package server;

import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.net.*;
import java.util.*;

class FtpHandler extends Thread {
	private Socket ctrlSocket = null; // 控制端
	private Socket dataSocket = null; // 数据端
	private String cmd = new String(""); // 指令类型
	private String param = new String(""); // 指令参数
	private String user = new String("");
	private String password = new String("");
	private String remoteHost = new String(""); // 客户地址
	private int remotePort = 0;
	private String dir = FtpServer.initDir;// 默认目录为D盘
	private String rootdir = "D:/"; // 默认根目录
	private int state = 0; // 用户的相关指令请求
	private String reply; // 返回信息
	private PrintWriter ctrlOutput;
	private int type = 0; // 文件类型
	private String requestfile = "";// 在远程服务器上要下载的文件地址
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

	private final String USERLIST_FILE = "user.txt";	// 设定存放用户信息的文件

	private FtpServerHahaha serverHahaha = null;

	/**
	 * 构造方法
	 * @param s
	 * @param serverHahaha1
	 * @throws IOException
	 */
	public FtpHandler(Socket s, FtpServerHahaha serverHahaha1) throws IOException {
		ctrlSocket = s;
		serverHahaha = serverHahaha1;
	}

	/**
	 * 服务器线程执行方法
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
					parseResult = parseInput(str); // 指令转化为指令号
					theLog(user, "指令:" + cmd + " 参数:" + param);
					if(state == FtpState.FS_WAIT_LOGIN){
						if(parseResult == 27)
							finished = commandLIST(); //在未登录的情况下显示文件列表
						else if(parseResult == 14)
							finished = commandRETR1();
						else
							finished = commandUSER();
					}
					else if(state == FtpState.FS_WAIT_PASS)
						finished = commandPASS();
					else if(state == FtpState.FS_LOGIN){
						switch (parseResult)// 指令号开关,决定程序是否继续运行的关键
						{
						case -1:
							errCMD(); // 语法错
							break;
						case 4:
							finished = commandCDUP(); // 到上一层目录
							break;
						case 6:
							finished = commandCWD(); // 到指定的目录
							break;
						case 7:
							finished = commandQUIT(); // 退出
							break;
						case 9:
							finished = commandPORT(); // 客户端IP:地址+TCP 端口号
							break;
						case 11:
							finished = commandTYPE(); // 文件类型设置(ascII 或 bin)
							break;
						case 14:
							finished = commandRETR1(); // 从服务器中获得文件
							break;
						case 15:
							finished = commandSTOR(); // 向服务器中发送文件
							break;
						case 22:
							finished = commandABOR(); // 关闭传输用连接dataSocket
							break;
						case 23:
							finished = commandDELE(); // 删除服务器上的指定文件
							break;
						case 25:
							finished = commandMKD(); // 建立目录
							break;
						case 27:
							finished = commandLIST(); // 文件和目录的列表
							break;
						case 26:
						case 33:
							finished = commandPWD(); // "当前目录" 信息
							break;
						case 32:
							finished = commandNOOP(); // "命令正确" 信息
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
			theLog(user, "连接断开");
		}
//		} catch (IOException e) { // 用户关闭客户端造成此异常，关闭该用户套接字。
//			String leaveUser = closeSocket();
//			Date t = new Date();
//			log("用户" + leaveUser + "已经离开, " + "退出时间:" + t);	
//			try {
//			} catch (IOException e1) {
//				e1.printStackTrace();
//			}
//			System.out.println("[SYSTEM] " + leaveUser + " 已离开!");
//		}
	}

	/**
	 * 根据控制信息，将控制信号转化为字符串信息（theControlString）
	 * @param theControlString
	 * @return
	 */
	int parseInput(String theControlString) {
		int p = 0;
		int i = -1;
		// p用来保存控制指令和控制参数的分界点信息
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
	 * 判断用户名是否正确
	 * @return
	 */
	boolean commandUSER() {
		if (cmd.equals("USER")) {
			reply = "331 用户名正确,请输入密码";
			user = param;
			state = FtpState.FS_WAIT_PASS;
			return false;
		} else {
			reply = "501 参数语法错误,用户名不匹配";
			return false;
		}
	}

	/**
	 * 判断用户密码是否正确
	 * @return
	 */
	boolean commandPASS() {
		password = param;
		if(isUserLogin(user, password)){
			reply = "230 用户登录";
			state = FtpState.FS_LOGIN;
			theLog("用户: " + user + "  密码：" + password + "  IP: " + remoteHost,  "登录");
			onlineUser.add(user);
			return false;
		} else {
			reply = "501 参数语法错误,密码不匹配";
			state = FtpState.FS_WAIT_LOGIN;
			return false;
		}
	}

	/**
	 * 控制信号输入错误
	 */
	void errCMD() {
		reply = "500 语法错误";
	}

	/**
	 * 方法功能：返回到上一层目录
	 * @return
	 */
	boolean commandCDUP() {
		dir = FtpServer.initDir;
		File f = new File(dir);
		// 当前文件夹的父文件夹不为空而且不是磁盘根目录
		if (f.getParent() != null && (!dir.equals(rootdir))) {
			dir = f.getParent();
			reply = "200 命令正确";
		} else {
			reply = "550 当前目录无父路径";
		}

		return false;
	}

	/**
	 * 方法功能：更改工作目录
	 * @return
	 */
	boolean commandCWD() {
		// 该命令改变工作目录到用户指定的目录
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
					reply = "550 此路径不存在";
					// return false;
				} else {
					s1 = new File(dir).getParent();
					if (s1 != null) {
						dir = s1;
						theLog(user, "改变目录成功");
						reply = "250 请求的文件处理结束, 当前目录变为: " + dir;
					} else
						reply = "550 此路径不存在";
				}
			} else if (param.equals(".") || param.equals(".\\")) {
			} else {
				dir = param;
				theLog(user, "改变目录成功");
				reply = "250 请求的文件处理结束, 工作路径变为 " + dir;
			}
		} else if (f1.isDirectory() && f1.exists()) {
			dir = s + param;
			theLog(user, "改变目录成功");
			reply = "250 请求的文件处理结束, 工作路径变为 " + dir;
		} else
			reply = "501 参数语法错误";

		return false;
	}

	/**
	 * 方法功能：退出连接
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
			theLog(user, "已退出登录");
		}
		reply = "221 服务关闭连接";
		return true;
	}

	/*
	 * 使用该命令时，客户端必须发送客户端用于接收数据的32位IP 地址和16位 的TCP 端口号。
	 * 这些信息以8位为一组，使用十进制传输，中间用逗号隔开。
	 */
	boolean commandPORT() {
		int p1 = 0;
		int p2 = 0;
		int[] a = new int[6];// 存放ip+tcp
		int i = 0; //
		try {
			while ((p2 = param.indexOf(",", p1)) != -1)// 前5位
			{
				a[i] = Integer.parseInt(param.substring(p1, p2));
				p2 = p2 + 1;
				p1 = p2;
				i++;
			}
			a[i] = Integer.parseInt(param.substring(p1, param.length()));// 最后一位
		} catch (NumberFormatException e) {
			reply = "501 参数语法错误";
			return false;
		}

		remoteHost = a[0] + "." + a[1] + "." + a[2] + "." + a[3];
		remotePort = a[4] * 256 + a[5];
		reply = "200 命令正确";
		return false;
	}

	/**
	 * 采用被动方式进行传输
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
		reply = "227 进入被动模式";
		System.out.println("进入被动模式");
		return false;
	}


	/**
	 * 方法功能：获得diskFile下所有的文件对象。
	 * @param diskFile
	 * @return
	 */
	private File[] getAllTheServerFile(File diskFile) {
		// 用来存储远程的磁盘文件列表，默认在D盘即控制访问权限在D盘
		File selDisk = new File("D:");
		File[] files = selDisk.listFiles();
		return files;
	}

	/**
	 * 返回文件和目录的列表
	 * @return
	 */
	boolean commandLIST() {
		try {
			serverSocket = new ServerSocket(4700);
			Socket socket = serverSocket.accept();
			pw = new PrintWriter(socket.getOutputStream());
			File f = new File(dir);

			String[] dirStructure = f.list();// 指定路径中的文件名数组,不包括当前路径或父路径
			// 获得文件在服务器上的路径，在客户端保存
			String[] thePathOfAllTheSonFiles = new String[dirStructure.length];
			boolean[] isOrNotDirectory = new boolean[dirStructure.length];
			int count = 0;
			File[] allTheSonFile = f.listFiles();

			for (File theFileName : allTheSonFile) {
				System.out.println(theFileName.getAbsolutePath());
				thePathOfAllTheSonFiles[count] = theFileName.getAbsolutePath();
				// 判断远程目录是否为文件夹
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
			theLog(user, "显示文件列表");
			reply = "226 传输数据连接结束";

			// 主动方式进行的操作，服务器端连接客户端
//				dataSocket = new Socket(remoteHost, remotePort, InetAddress.getLocalHost(), 20);
//				PrintWriter dout = new PrintWriter(dataSocket.getOutputStream(), true);
//				if (param.equals("") || param.equals("LIST")) {
//					ctrlOutput.println("150 文件状态正常,ls以 ASCII 方式操作");
//					File f = new File(dir);
//					String[] dirStructure = f.list();// 指定路径中的文件名数组,不包括当前路径或父路径
//					String fileType;
//					for (int i = 0; i < dirStructure.length; i++) {
//						if (dirStructure[i].indexOf(".") != -1) {
//							fileType = "- "; // 父目录(在linux下)
//						} else {
//							fileType = "d "; // 本目录的文件和子目录
//						}
//						dout.println(dirStructure[i]);// (fileType+dirStructure[i]);
//					}
//				}
//				dout.close();
//				dataSocket.close();
//				reply = "226 传输数据连接结束";
		} catch (Exception e) {
			e.printStackTrace();
			reply = "451 文件传输错误";
			return false;
		}
		return false;
	}

	/**
	 * 执行传输文件方式的设置
	 * @return
	 */
	boolean commandTYPE() {
		if (param.equals("A")) {
			type = FtpState.FTYPE_ASCII;// 0
			reply = "200 转 ASCII 模式";
		} else if (param.equals("I")) {
			type = FtpState.FTYPE_IMAGE;// 1
			reply = "200 转 BINARY 模式";
		} else {
			reply = "504 命令不能执行这种参数";
		}
		return false;
	}

	/**
	 * 采用断点续传的方式从服务器端获得文件
	 * @return
	 */
	boolean commandRETR1() {
		// param 用来得到当前指令的控制信息，即得到相应的文件
		requestfile = param;
		File f = new File(requestfile);
		if (! f.exists()) {
			reply = "550 文件不存在";
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
				// 接收客户端发送过来的断点的位置
				StringWriter sw = new StringWriter();
				while ((temp = downloadIn.read()) != 0) {
					sw.write(temp);
					sw.flush();
				}
				System.out.println(sw.toString());

				// 设定断点的位置
				int startIndex = 0;

				if (! sw.toString().isEmpty()) {
					startIndex = Integer.parseInt(sw.toString());
					System.out.println(startIndex);
					// 文件的总长度
					long length = f.length();
					// 将文件的总长度用字符串的形式保存
					byte[] fileLength = String.valueOf(length).getBytes();
					downloadOut.write(fileLength);
					downloadOut.write(0);
					downloadOut.flush();

					// 设置读取文件的缓冲区，以10B为区间
					byte[] buffer = new byte[1024*10];
					System.out.println("startIndex: " + startIndex);
					accessFile.skipBytes(startIndex);
					// 要剩余文件的总长度
					int total = (int) length - startIndex;
					while(true) {
						if (total == 0) {
							break;
						}
						// 要上传的剩余的总长度
						int len = total;
						if (len > buffer.length) {
							len = buffer.length;
						}
						// 设置实际传送的长度
						int realLength = accessFile.read(buffer, 0, len);
						// 计算剩余项目的长度
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
				theLog(user, "文件下载成功");
				reply = "226 传输数据连接结束";
			} catch (IOException EE) {
				EE.printStackTrace();
			}

		} catch (IOException E) {
			E.printStackTrace();
			reply = "451 请求失败: 传输出故障";
			return false;
		}
		System.out.println(f.length());
		return false;
	}

	/**
	 * 不采用断点续传，从服务器端口获得文件
	 * @return
	 */
	boolean commandRETR() {
		requestfile = param;
		File f = new File(requestfile);
		// 首先判断服务器端的文件是否存在
		if (!f.exists()) {
			reply = "550 文件不存在";
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
			theLog(user, "文件下载成功");
			reply = "226 传输数据连接结束";
		} catch (IOException e) {
			e.printStackTrace();
			reply = "451 请求失败: 传输出故障";
			return false;
		}
//			if (isrest) {
		//
//					} else {
//						if (type == FtpState.FTYPE_IMAGE) // bin
//						{
//							try {
//								ctrlOutput.println("150 文件状态正常,以二进治方式打开文件:  " + requestfile);
//								dataSocket = new Socket(remoteHost, remotePort, InetAddress.getLocalHost(), 20);
//								BufferedInputStream fin = new BufferedInputStream(new FileInputStream(requestfile));
//								PrintStream dataOutput = new PrintStream(dataSocket.getOutputStream(), true);
//								byte[] buf = new byte[1024]; // 目标缓冲区
//								int l = 0;
//								while ((l = fin.read(buf, 0, 1024)) != -1) // 缓冲区未读满
//								{
//									dataOutput.write(buf, 0, l); // 写入套接字
//								}
//								fin.close();
//								dataOutput.close();
//								dataSocket.close();
//								reply = "226 传输数据连接结束";
		//
//							} catch (Exception e) {
//								e.printStackTrace();
//								reply = "451 请求失败: 传输出故障";
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
//								reply = "226 传输数据连接结束";
//							} catch (Exception e) {
//								e.printStackTrace();
//								reply = "451 请求失败: 传输出故障";
//								return false;
//							}
//						}
//					}
				return false;
			}

	/**
	 * 上传文件操作
	 * @return
	 */
	boolean commandSTOR() {
		if (param.equals("")) {
			reply = "501 参数语法错误";
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
			theLog(user, "文件存储成功");
			reply = "226 传输数据连接结束";
		} catch (IOException e) {
			e.printStackTrace();
			reply = "451 请求失败: 传输出故障";
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
//					reply = "226 传输数据连接结束";
//				} catch (Exception e) {
//					e.printStackTrace();
//					reply = "451 请求失败: 传输出故障";
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
//					reply = "226 传输数据连接结束";
//				} catch (Exception e) {
//					e.printStackTrace();
//					reply = "451 请求失败: 传输出故障";
//					return false;
//				}
//			}
		return false;
	}
		
	boolean commandPWD() {
		theLog(user,"显示目录成功");
		reply = "257 " + "\"" + dir + "\"" + " 是当前目录.";
		return false;
	}
		
	boolean commandNOOP() {
		reply = "200 命令正确.";
		return false;
	}
		
	// 强关dataSocket 流
	boolean commandABOR() {
		try {
			dataSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
			reply = "451 请求失败: 传输出故障";
			return false;
		}
		reply = "421 服务不可用, 关闭数据传送连接";
		return false;
	}

	/**
	 * 通过递归删除远程服务器上的文件
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
	 * 删除服务器上的特定文件，从窗口中获取文件名
	 * @return
	 */
	boolean commandDELE() {
		File f = new File(param);
		if(deleteFile(f)){
			theLog(user,"删除文件成功");
			reply = "250 请求的文件处理结束,成功删除服务器上文件";
		}
		else{
			theLog(user,"删除文件失败");
			reply = "333 该文件正被其他程序占用，删除失败";
		}
		return false;
	}

	/**
	 * 新建文件夹
	 * @return
	 */
	boolean commandMKD() {
		String s1 = param.toLowerCase();
		String s2 = rootdir.toLowerCase();
		if (s1.startsWith(s2)) {
			File f = new File(param);
			if (f.exists()) {
				reply = "550 请求的动作未执行,目录已存在";
				return false;
			} else {
				f.mkdirs();
				theLog(user, "创建目录成功");
				reply = "250 请求的文件处理结束, 目录建立";
			}
		} else {
			File f = new File(addTail(dir) + param);
			if (f.exists()) {
				reply = "550 请求的动作未执行,目录已存在";
				return false;
			} else {
				f.mkdirs();
				theLog(user, "创建目录成功");
				reply = "250 请求的文件处理结束, 目录建立";
			}
		}
		return false;
	}

	/**
	 * 判断文件的用户名及密码操作
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
			out.println("warning|读写文件时出错!");
		} catch (IOException ie) {
			System.out.println("[ERROR] " + ie);
			out.println("warning|读写文件时出错!");
		}
		return false;
	}

	/**
	 * 记录登录人员的信息
	 * @param UserName
	 * @param theUserIP
	 */
	public void theLog(String UserName, String theUserIP) {
		// 获取表格的数据模型
		DefaultTableModel defaultTableModel = (DefaultTableModel) this.serverHahaha.UserOnline.getModel();
		defaultTableModel.addRow(new Object[] {UserName, theUserIP});
	}

	/**
	 * 释放连接
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