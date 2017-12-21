package LogicClient;

import ftpClient.MyProgressBar;
import javafx.scene.control.ProgressBar;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.nio.file.Files;
import java.sql.BatchUpdateException;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.plaf.ProgressBarUI;

/**
 *
 * @author Administrator
 */
public class FunctionOfFtp {

	private Socket connectSocket;// 控制连接，用于传送和响应命令
	private Socket dataSocket;// 数据连接，用于数据传输
	private BufferedReader inData;// 控制连接中用于读取返回信息的数据流
	private BufferedWriter outData;// 控制连接中用于传送用户命令的数据流
	private String response = null;// 将返回信息封装成字符串
	private String remoteHost;// 远程主机名
	private int remotePort;// 通信端口号
	private String remotePath;// 远程路径
	private String user;// 用户名
	private String passWord;// 用户口令
	File rootPath = new File("/");// 根路径
	File currentPath = rootPath;// 当前路径
	private boolean logined;// 判断是否登录服务器的标志
	private boolean debug;
	
	private int dataPort = 66666;
	
	public FunctionOfFtp() {
		remoteHost = "localhost";
		remotePort = 21;
		remotePath = "/";
		user = "yy";
		passWord = "123";
		logined = false;
		debug = false;
	}
	
	public Socket getConnectSocket(){
		return connectSocket;
	}
	
	public boolean isLogin(){
		return logined;
	}

	// 设置服务器域名（IP地址）
	public void setRemoteHost(String remoteHost) {
		this.remoteHost = remoteHost;
	}

	// 返回服务器域名（IP地址）
	public String getRemoteHost() {
		return remoteHost;
	}

	// 设置服务器端口(PORT)
	public void setRemotePort(int remotePort) {
		this.remotePort = remotePort;
	}

	// 返回服务器端口（PORT）
	public int getRemotePort() {
		return remotePort;
	}

	// 设置远程目录路径
	public void setRemotePath(String remotePath) {
		this.remotePath = remotePath;
	}

	//获取远程目录路径
	public String getRemotePath() {
		return remotePath;
	}

	// 设置远程服务器的用户名
	public void setUser(String user) {
		this.user = user;
	}

	// 设置远程服务器的密码
	public void setPW(String password) {
		this.passWord = password;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	/**
	 * 用户在客户端进行连接
	 */
	public void connect() {
		try {
			if (connectSocket == null) {//如果当前没有控制连接，则生成获得新的控制连接
				//一个新的控制连接包括远程主机号即域名以及通信端口号，服务器上应该设置监听21号端口
				connectSocket = new Socket(remoteHost, remotePort);
				inData = new BufferedReader(new InputStreamReader(connectSocket.getInputStream()));// 输入信息(字符输入流)
				//客户端需要读取服务器端的信息（包括远程主机号（域名），以及通信端口号）
				outData = new BufferedWriter(new OutputStreamWriter(connectSocket.getOutputStream()));// 输出信息(字符输出流)
				//在读取了远程主机号（域名）以及通信端口号后，向指定的服务器通过通信端口传送相应的指令。
				response = readTheAction();
				//读取服务器返回客户端的信息
				JOptionPane.showConfirmDialog(null, "服务器成功连接！", "连接信息", JOptionPane.CLOSED_OPTION,
						JOptionPane.INFORMATION_MESSAGE);
			}
			else{
				JOptionPane.showConfirmDialog(null, "你已连接！", "连接信息", JOptionPane.CLOSED_OPTION,
						JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (Exception e) {
			JOptionPane.showConfirmDialog(null, " 连接失败", " 连接信息", JOptionPane.CLOSED_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * 用户在客户端进行登陆
	 */
	public void login() {
		try {
			//需要先有控制连接的建立
			if (connectSocket == null) {
				JOptionPane.showConfirmDialog(null, " 服务器尚未连接，请先连接！", " 连接信息", JOptionPane.CLOSED_OPTION,
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			if(logined){
				JOptionPane.showConfirmDialog(null, " 你已登录！", " 登录信息", JOptionPane.CLOSED_OPTION,
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			//在客户端与服务器端实现连接后才可以实现用户的登录。
			sendCommand("USER " + user);
			response = readTheAction();
			//将用户名信息传送到服务器端，服务器端可以获得相应的密码，然后与用户在客户端输入的密码相比较
			//如果正确则为登录成功，如果错误则登录失败
			if (!response.startsWith("331")) {
//				cleanup();
				JOptionPane.showConfirmDialog(null, " 用户名或密码错误！", " 连接信息", JOptionPane.CLOSED_OPTION,
						JOptionPane.INFORMATION_MESSAGE);
			}
			sendCommand("PASS " + passWord);
			response = readTheAction();

////			System.err.println(response);
			if (!response.startsWith("230")) {
//				cleanup();
				JOptionPane.showConfirmDialog(null, " 用户名或密码错误！", " 连接信息", JOptionPane.CLOSED_OPTION,
						JOptionPane.INFORMATION_MESSAGE);
//				System.out.println(response);
				return;
			}
			//当密码正确，则logined的值变化为true，之后显示为登录成功
			logined = true;
			JOptionPane.showConfirmDialog(null, " 登陆成功！", " 连接信息", JOptionPane.CLOSED_OPTION,
					JOptionPane.INFORMATION_MESSAGE);


//			cwd(remotePath);


		} catch (Exception e) {
			JOptionPane.showConfirmDialog(null, " 登陆失败！", " 登陆信息", JOptionPane.CLOSED_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
		}//抓取登录失败的异常
	}

	/**
	 * 服务器端监听端口4700，数据采用被动方式进行连接
	 * （所谓被动方式是指服务器监听数据传输端口）
	 * @param mask
	 * @return
	 * @throws IOException
	 */
	public ArrayList<String> list(String mask) throws IOException {
		if (!logined) {
			System.out.println("用户未登陆。");
		}
		ArrayList<String> fileList = new ArrayList<String>();
		try {
//			dataSocket = createDataSocket();
			if (mask == null || mask.equals("") || mask.equals(" ")) {
				sendCommand("LIST");
			} else {
				sendCommand("LIST " + mask);
			}
			//开启数据流
			Socket socket = new Socket(remoteHost, 4700);
			BufferedReader dataIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String line;
			while ((line = dataIn.readLine()) != null) {
				fileList.add(line);//向服务器的目录列表添加新输入的行

			}
			dataIn.close();// 关闭数据流
			socket.close();

			for (String fileName : fileList) {
				System.out.println(fileName);
			}

			response = readTheAction();//获取返回信息并封装
			if (!response.startsWith("226")) {
				System.out.println(response);
			}
//			BufferedReader dataIn = new BufferedReader(new InputStreamReader(dataSocket.getInputStream()));
//			String line;
//			while ((line = dataIn.readLine()) != null) {
//				fileList.add(line);
//			}
//
//			dataIn.close();// 关闭数据流
//			dataSocket.close();// 关闭数据连接
//			response = readLine();

		} catch (IOException ioex) {
			ioex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return fileList;
	}

	/// 关闭FTP连接，退出登录并终止连接QUIT
	public synchronized void close() throws IOException {
		try {
			sendCommand("QUIT ");
		} finally {
			cleanup();
//			System.out.println("正在关闭......");
//			JOptionPane.showConfirmDialog(null, " 已断开连接！", " 连接信息", JOptionPane.CLOSED_OPTION,
//					JOptionPane.INFORMATION_MESSAGE);
		}
	}
	//清空控制连接信息，并使登录连接恢复初始状态
	private void cleanup() {
		try {
			inData.close();
			outData.close();
			connectSocket.close();
			connectSocket = null;
			logined = false;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	///
	/// If the value of mode is true, set binary mode for downloads.
	/// Else, set Ascii mode.
	///

	/**
	 * 设置服务器端二进制传输
	 * @param mode
	 * @throws IOException
	 */
	public void setBinaryMode(Boolean mode) throws IOException {

		if (mode) {
			sendCommand("TYPE I ");
		} else {
			sendCommand("TYPE A ");
		}
		response = readTheAction();
		if (!response.startsWith("200")) {
			throw new IOException("Caught Error " + response);
		}
	}

	// 显示当前远程工作目录PWD
	public synchronized String pwd() throws IOException {
		if(connectSocket == null){
			JOptionPane.showConfirmDialog(null, "服务器尚未连接！", "连接信息", JOptionPane.CLOSED_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
			return null;
		}
		if(!logined){
			JOptionPane.showConfirmDialog(null, "尚未登录！", "登录信息", JOptionPane.CLOSED_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
			return null;
		}
		sendCommand("XPWD ");
		String dir = null;
		response = readTheAction();//服务器响应
		if (response.startsWith("257")) { // 服务器响应信息如：257 "/C:/TEMP/" is current
											// directory.截取两引号之间的内容
			int fristQuote = response.indexOf('\"');
			int secondQuote = response.indexOf('\"', fristQuote + 1);
			if (secondQuote > 0) {
				dir = response.substring(fristQuote + 1, secondQuote);
			}
		}
//		System.out.println(dir);
		return dir;
	}

	// CWD 改变远程系统的工作目录
	public synchronized boolean cwd(String dir) throws IOException {
		if (dir.equals("/")) {// 根路径
			System.out.println("当前路径是根目录！");
		}
		if (!logined) {
			login();
		}
		sendCommand("CWD " + dir);
		response = readTheAction();
//		System.out.println(response);
		if (response.startsWith("250")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 上传的文件操作
	 * @param fsend
	 * @return
	 * @throws IOException
	 */
	public synchronized boolean upload(File fsend) throws IOException {
		
		String localFileName = fsend.getName();//获取文件名字
		
		sendCommand("STOR " + localFileName);

		
		Socket socket = new Socket(remoteHost, 4700);
		BufferedInputStream fin = new BufferedInputStream(new FileInputStream(fsend));
		PrintStream dataOutput = new PrintStream(socket.getOutputStream());
		byte[] buf = new byte[1024];
		int l = 0;
		while ((l = fin.read(buf, 0, 1024)) != -1) {
			dataOutput.write(buf, 0, l);
			
		}
		fin.close();
		dataOutput.close();
		socket.close();
		
		response = readTheAction();//接收服务器端的信息，用于返回客户端判断文件是否上传成功

		if (response.startsWith("226")) {
			JOptionPane.showConfirmDialog(null, " 文件上传成功！", " 上传信息", JOptionPane.CLOSED_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
		}
		return (response.startsWith("226"));//如果文件以226开头则为上传成功
	}

	/**
	 * 下载文件 RETR, 服务器开启监听端口
	 * 采用被动方式进行连接
	 * TODO 这边要和服务器端配合加断点续传功能
	 * @param remoteFile 服务器的文件路径
	 * @param localFile 服务器下载到本地的文件路径，默认保存在
	 * @return
	 * @throws IOException
	 */
	public synchronized boolean download(String remoteFile, String localFile) throws IOException {

		System.out.println(remoteFile);
//		dataSocket = createDataSocket();
		sendCommand("RETR " + remoteFile);
//		response = readLine();
//		if (!response.startsWith("1")) {
//			System.out.println(response);
//		}
		
		
		Socket socket = new Socket(remoteHost, 4700);
//		BufferedInputStream fin = new BufferedInputStream(new FileInputStream("E:/HDU/1002/main.c"));
//		PrintStream dataOutput = new PrintStream(socket.getOutputStream());
		BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
		JFileChooser jfc = new JFileChooser();//获取选定的文件
		if(jfc.showSaveDialog(new Label()) == JFileChooser.APPROVE_OPTION){
			File fsave = jfc.getSelectedFile();
//			ProgressBar pBar = new ProgressBar("下载", bis);
//			Thread thread = new Thread(pBar);
//			thread.start();
			FileOutputStream fos = new FileOutputStream(fsave);
			byte[] buf = new byte[1024];
			int l = 0;
			while ((l = bis.read(buf, 0, 1024)) != -1) {
				fos.write(buf, 0, l);
			}
//			try {
//				if(!thread.isAlive())
//					thread.join();
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			pb.closeBar();
			fos.flush();
			fos.close();
		}

		
		
		System.out.println(localFile);
//		BufferedInputStream dataIn = new BufferedInputStream(dataSocket.getInputStream());
//		new File(localFile).createNewFile();
//		FileOutputStream fileOut = new FileOutputStream(localFile);
//		byte[] buffer = new byte[4096];
//		int bytesRead = 0;
//		do {
//			bytesRead = dataIn.read(buffer);
//			if (bytesRead != -1) {
//				fileOut.write(buffer, 0, bytesRead);
//			}
//		} while (bytesRead != -1);
//		fileOut.flush();
//		fileOut.close();
//		dataSocket.close();
		response = readTheAction();

		if (response.startsWith("226")) {
			JOptionPane.showConfirmDialog(null, "下载成功", " 下载信息", JOptionPane.CLOSED_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showConfirmDialog(null, "文件不存在，下载失败", " 下载信息", JOptionPane.CLOSED_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
		}
		return (response.startsWith("226"));
	}

	/**
	 * 方法功能：断点续传测试方法(在下载端口运行)
	 * 包含进度条
	 * 采用被动方式进行连接
	 * 采用二进制方式进行传输
	 * @param remoteFile	要下载的服务器端的文件路径
	 * @param myProgressBar	进度条
	 * @return
	 * @throws IOException
	 */
	public synchronized boolean download1(String remoteFile, JProgressBar myProgressBar, JButton stop, JButton goOn) throws IOException {
        // 要保存的文件路径
        File file;
        JFileChooser jfc = new JFileChooser();//获取选定的文件
        if(jfc.showSaveDialog(new Label()) == JFileChooser.APPROVE_OPTION) {
            file = jfc.getSelectedFile();
        } else {
            file = new File("d:");
        }
        Thread theDownloadThread = new Thread(new Runnable() {
            @Override
            public void run() {
                // 建立数据传输端口
                System.out.println(remoteFile);
                sendCommand("RETR " + remoteFile);
                try {
                    // 创建服务器端的套接字
                    Socket socket = new Socket(remoteHost, 4878);

                    OutputStream theDownloadOut = socket.getOutputStream();
                    InputStream theDownloadIn = socket.getInputStream();


                        // 判断文件要开始传送的位置
                        int startIndex = 0;
                        // file是接收之后要保存的文件的路径
                        // 在此判断文件是否存在，原因有二：
                        // 1、文件传送了部分但不是全部
                        // 2、文件没有进行过传送
                        if (file.exists()) {
                            startIndex = (int) file.length();
                        }
                        System.out.println("Client startIndex: " + startIndex);
                        // 文件写出流
                        RandomAccessFile accessFile = new RandomAccessFile(file, "rw");
                        // 将断点的信息发送至服务器端口
                        theDownloadOut.write(String.valueOf(startIndex).getBytes());
                        theDownloadOut.write(0);
                        theDownloadOut.flush();

                        // 记录文件的长度
                        int temp = 0;
                        StringWriter sw = new StringWriter();
                        // 从服务器端接收文件的长度
                        while ((temp = theDownloadIn.read()) != 0) {
                            sw.write(temp);
                            sw.flush();
                        }
                        // 返回要读取的文件的长度
                        int length = Integer.parseInt(sw.toString());
                        System.out.println("Client fileLength" + length);
                        // 设置二进制缓冲文件区域
                        byte[] buffer = new byte[1024*10];
                        // 获取剩余要读出的文件的长度
                        int total = length - startIndex;
                        accessFile.skipBytes(startIndex);
                        while (true) {
                            // 如果剩余的长度为0，结束此次操作
                            if (total == 0) {
								break;
							}
                            // 如果本次读取的长度是剩余的长度
                            int len = total;
                            if (len > buffer.length) {
                                // 缓冲区溢出，此次读出整个缓冲区的文件的长度
                                len = buffer.length;
                            }
                            // 获得真正读取的文件长度
                            int realLength = theDownloadIn.read(buffer, 0, len);
                            // 更改剩余的文件长度
                            total = total - realLength;
                            if (realLength > 0) {
                                // 将本次读出的数据写入到随机文件输出流中
                                accessFile.write(buffer, 0, realLength);
                            } else {
                                break;
                            }

                            // 获取文件下载的进度
                            float abc = (float) (length - total) / length * 100;
                            String the = String.valueOf(abc);
                            int index = the.lastIndexOf(".");
                            String strNum = the.substring(0, index);
                            int finished = Integer.valueOf(strNum);
                            System.out.println(finished);

                            System.out.println("finished"+finished);
                            System.out.println("valur"+myProgressBar.getValue());
                            myProgressBar.setValue(finished);
                            if (total == 0) {
                                myProgressBar.setForeground(Color.green);
                                goOn.setEnabled(false);
								stop.setEnabled(false);
                            }
                        }
                        theDownloadIn.close();
                        theDownloadOut.close();
                        accessFile.close();
                } catch (IOException ee) {
                    ee.printStackTrace();
                }
            }
        });

        theDownloadThread.start();

        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 首先对界面进行设置
                stop.setEnabled(false);
                goOn.setEnabled(true);
                theDownloadThread.suspend();
            }
        });

        goOn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goOn.setEnabled(false);
                stop.setEnabled(true);
                theDownloadThread.resume();
            }
        });

        JOptionPane.showConfirmDialog(null, "正在下载", " 下载信息", JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);

//		response = readTheAction();

//		if (response.startsWith("226")) {
//			JOptionPane.showConfirmDialog(null, "下载成功", " 下载信息", JOptionPane.CLOSED_OPTION,
//					JOptionPane.INFORMATION_MESSAGE);
//		} else {
//			JOptionPane.showConfirmDialog(null, "文件不存在，下载失败", " 下载信息", JOptionPane.CLOSED_OPTION,
//					JOptionPane.INFORMATION_MESSAGE);
//		}
//		return (response.startsWith("226"));

        return true;
	}

	/**
	 * 在远程服务器上创建一个目录
	 * @param dirName
	 * @throws IOException
	 */
	public void mkdir(String dirName) throws IOException {

		if(connectSocket == null){
			JOptionPane.showConfirmDialog(null, "服务器尚未连接！", "连接信息", JOptionPane.CLOSED_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if(!logined){
			JOptionPane.showConfirmDialog(null, "尚未登录！", "登录信息", JOptionPane.CLOSED_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if(dirName.equals("")){
			JOptionPane.showConfirmDialog(null, "请输入目录名！", "目录信息", JOptionPane.CLOSED_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		sendCommand("MKD " + dirName); // 创建目录
		response = readTheAction();//向服务器段发送申请创建目录的信息
		if(response.startsWith("550")){
			JOptionPane.showConfirmDialog(null, "目录  " + dirName + "  已经存在！", " 创建目录", JOptionPane.CLOSED_OPTION,
					JOptionPane.INFORMATION_MESSAGE); // 目录已经存在
		}
		else if (!response.startsWith("250")) { // FTP命令发送过程发生异常
			System.out.println(response);
		} else {
			JOptionPane.showConfirmDialog(null, "创建目录  " + dirName + "  成功！", " 创建目录", JOptionPane.CLOSED_OPTION,
					JOptionPane.INFORMATION_MESSAGE); // 成功创建目录
		}

	}

	/**
	 * 删除远程个服务器上的一个目录
	 * @param dirName
	 * @throws IOException
	 */
	public void delete(String dirName) throws IOException {
//		if (!logined) { // 如果尚未与服务器连接，则连接服务器
//			login();
//		}

		sendCommand("DELE " + dirName);
		response = readTheAction();//向服务器端发送删除指定目录的请求
		if (response.startsWith("550")) { // FTP命令发送过程发生异常
//			System.out.println(response);
			JOptionPane.showConfirmDialog(null, "文件" + dirName + "  不存在，删除失败", " 删除目录", JOptionPane.CLOSED_OPTION,
					JOptionPane.INFORMATION_MESSAGE); //文件不存在
		} 
		else if(response.startsWith("333")){
			JOptionPane.showConfirmDialog(null, "文件" + dirName + "  正被其他应用程序占用，删除失败", " 删除目录", JOptionPane.CLOSED_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
		}//删除文件需要判断是否文件正在被占用
		else {
			JOptionPane.showConfirmDialog(null, "删除文件" + dirName + "  成功！", " 删除目录", JOptionPane.CLOSED_OPTION,
					JOptionPane.INFORMATION_MESSAGE); // 成功删除目录
		}//如果文件存在且未被占用则可以被删除。

	}

	/**
	 * 建立数据连接（采用被动方式进行）
	 * @return
	 * @throws IOException
	 */
	private Socket createDataSocket() throws IOException {
		sendCommand("PASV ");
		//采用被动模式传输数据时，由服务器返回数据传输的临时端口号，使用该端口进行数据传输
		dataSocket = new Socket(remoteHost, 33333);//数据堆栈
		return dataSocket;
	}
//	private Socket createDataSocket() throws IOException {
//
//		sendCommand("PASV "); // 采用Pasv模式（被动模式），由服务器返回数据传输的临时端口号，使用该端口进行数据传输
//		
//		response = readLine();
//		System.err.println(response);
//		if (!response.startsWith("227")) { // FTP命令传输过程发生异常
//			System.out.println(response);
//		}
//		
//		
//		String clientIp = "";
//		int port = -1;
//		int opening = response.indexOf('('); // 采用Pasv模式服务器返回的信息如“227 Entering
//												// Passive Mode
//												// (127,0,0,1,64,2)”
//		int closing = response.indexOf(')', opening + 1); // 取"()"之间的内容：127,0,0,1,64,2
//															// ，前4个数字为本机IP地址，转换成127.0.0.1格式
//		if (closing > 0) { // 端口号由后2个数字计算得出：64*256+2=16386
//			String dataLink = response.substring(opening + 1, closing);
//
//			StringTokenizer arg = new StringTokenizer(dataLink, ",", false);
//			clientIp = arg.nextToken();
//
//			for (int i = 0; i < 3; i++) {
//				String hIp = arg.nextToken();
//				clientIp = clientIp + "." + hIp;
//			}
//			port = Integer.parseInt(arg.nextToken()) * 256 + Integer.parseInt(arg.nextToken());
//		}
//
//		return new Socket(clientIp, port);
//	}

	/**
	 * 读取服务器返回的响应信息
	 * @return
	 * @throws IOException
	 */
	private String readTheAction() throws IOException {
		String line = inData.readLine();
		if (debug) {
			System.out.println("< " + line);
		}
		return line;
	}

	/**
	 * 在控制连接上发送命令到服务器端
	 * @param line
	 */
	public void sendCommand(String line) {
		if (connectSocket == null) {
			System.out.println("FTP尚未连接"); // 未建立通信链接，抛出异常警告
		}
		try {
			outData.write(line + "\r\n"); // 发送命令
			outData.flush(); // 刷新输出流
			if (debug) {
				System.out.println("> " + line); // 同时控制台输出相应命令信息，以便分析
			}
		} catch (Exception e) {
			connectSocket = null;
			System.out.println(e);
			return;
		}
	}
}
