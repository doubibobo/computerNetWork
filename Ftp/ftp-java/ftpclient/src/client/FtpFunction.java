package client;

import java.net.Socket;
import java.sql.BatchUpdateException;
import java.awt.Dimension;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.util.*;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.plaf.ProgressBarUI;

/**
 *
 * @author Administrator
 */
public class FtpFunction {

	private Socket connectSocket;// ¿ØÖÆÁ¬½Ó£¬ÓÃÓÚ´«ËÍºÍÏìÓ¦ÃüÁî
	private Socket dataSocket;// Êý¾ÝÁ¬½Ó£¬ÓÃÓÚÊý¾Ý´«Êä
	private BufferedReader inData;// ¿ØÖÆÁ¬½ÓÖÐÓÃÓÚ¶ÁÈ¡·µ»ØÐÅÏ¢µÄÊý¾ÝÁ÷
	private BufferedWriter outData;// ¿ØÖÆÁ¬½ÓÖÐÓÃÓÚ´«ËÍÓÃ»§ÃüÁîµÄÊý¾ÝÁ÷
	private String response = null;// ½«·µ»ØÐÅÏ¢·â×°³É×Ö·û´®
	private String remoteHost;// Ô¶³ÌÖ÷»úÃû
	private int remotePort;// Í¨ÐÅ¶Ë¿ÚºÅ
	private String remotePath;// Ô¶³ÌÂ·¾¶
	private String user;// ÓÃ»§Ãû
	private String passWord;// ÓÃ»§¿ÚÁî
	File rootPath = new File("/");// ¸ùÂ·¾¶
	File currentPath = rootPath;// µ±Ç°Â·¾¶
	private boolean logined;// ÅÐ¶ÏÊÇ·ñµÇÂ¼·þÎñÆ÷µÄ±êÖ¾
	private boolean debug;
	
	private int dataPort = 33333;
	
	public FtpFunction() {
		remoteHost = "localhost";
		remotePort = 21;
		remotePath = "/";
		user = "user";
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

	// ÉèÖÃ·þÎñÆ÷ÓòÃû£¨IPµØÖ·£©
	public void setRemoteHost(String remoteHost) {
		this.remoteHost = remoteHost;
	}

	// ·µ»Ø·þÎñÆ÷ÓòÃû£¨IPµØÖ·£©
	public String getRemoteHost() {
		return remoteHost;
	}

	// ÉèÖÃ¶Ë¿Ú
	public void setRemotePort(int remotePort) {
		this.remotePort = remotePort;
	}

	// ·µ»Ø¶Ë¿Ú
	public int getRemotePort() {
		return remotePort;
	}

	// The remote directory path
	public void setRemotePath(String remotePath) {
		this.remotePath = remotePath;
	}

	/// The current remote directory path.
	public String getRemotePath() {
		return remotePath;
	}

	// ÓÃ»§Ãû
	public void setUser(String user) {
		this.user = user;
	}

	// ÃÜÂë
	public void setPW(String password) {
		this.passWord = password;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public void connect() {
		try {
			if (connectSocket == null) {

				connectSocket = new Socket(remoteHost, remotePort);
				inData = new BufferedReader(new InputStreamReader(connectSocket.getInputStream()));// ÊäÈëÐÅÏ¢(×Ö·ûÊäÈëÁ÷)

				outData = new BufferedWriter(new OutputStreamWriter(connectSocket.getOutputStream()));// Êä³öÐÅÏ¢(×Ö·ûÊä³öÁ÷)
				response = readLine();
				JOptionPane.showConfirmDialog(null, "·þÎñÆ÷³É¹¦Á¬½Ó£¡", "Á¬½ÓÐÅÏ¢", JOptionPane.CLOSED_OPTION,
						JOptionPane.INFORMATION_MESSAGE);
			}
			else{
				JOptionPane.showConfirmDialog(null, "ÄãÒÑÁ¬½Ó£¡", "Á¬½ÓÐÅÏ¢", JOptionPane.CLOSED_OPTION,
						JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (Exception e) {
			JOptionPane.showConfirmDialog(null, " Á¬½ÓÊ§°Ü", " Á¬½ÓÐÅÏ¢", JOptionPane.CLOSED_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public void login() {
		try {
			if (connectSocket == null) {
				JOptionPane.showConfirmDialog(null, " ·þÎñÆ÷ÉÐÎ´Á¬½Ó£¬ÇëÏÈÁ¬½Ó£¡", " Á¬½ÓÐÅÏ¢", JOptionPane.CLOSED_OPTION,
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			if(logined){
				JOptionPane.showConfirmDialog(null, " ÄãÒÑµÇÂ¼£¡", " µÇÂ¼ÐÅÏ¢", JOptionPane.CLOSED_OPTION,
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			sendCommand("USER " + user);
			response = readLine();
			
			if (!response.startsWith("331")) {
//				cleanup();
				JOptionPane.showConfirmDialog(null, " ÓÃ»§Ãû»òÃÜÂë´íÎó£¡", " Á¬½ÓÐÅÏ¢", JOptionPane.CLOSED_OPTION,
						JOptionPane.INFORMATION_MESSAGE);
			}
			sendCommand("PASS " + passWord);
			response = readLine();
			
////			System.err.println(response);
			if (!response.startsWith("230")) {
//				cleanup();
				JOptionPane.showConfirmDialog(null, " ÓÃ»§Ãû»òÃÜÂë´íÎó£¡", " Á¬½ÓÐÅÏ¢", JOptionPane.CLOSED_OPTION,
						JOptionPane.INFORMATION_MESSAGE);
//				System.out.println(response);
				return;
			}
			logined = true;
			JOptionPane.showConfirmDialog(null, " µÇÂ½³É¹¦£¡", " Á¬½ÓÐÅÏ¢", JOptionPane.CLOSED_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
			
			
//			cwd(remotePath);
			
			
		} catch (Exception e) {
			JOptionPane.showConfirmDialog(null, " µÇÂ½Ê§°Ü£¡", " µÇÂ½ÐÅÏ¢", JOptionPane.CLOSED_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	// »ñÈ¡Ô¶³Ì·þÎñÆ÷µÄÄ¿Â¼ÁÐ±í
	public ArrayList<String> list(String mask) throws IOException {
//		if (!logined) {
//			System.out.println("·þÎñÆ÷ÉÐÎ´Á¬½Ó¡£");
//			// login();
//		}
		ArrayList<String> fileList = new ArrayList<String>();
		try {
//			dataSocket = createDataSocket();
			if (mask == null || mask.equals("") || mask.equals(" ")) {
				sendCommand("LIST");
			} else {
				sendCommand("LIST " + mask);
			}
			
			Socket socket = new Socket(remoteHost, 4700);
			BufferedReader dataIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String line;
			while ((line = dataIn.readLine()) != null) {
				fileList.add(line);
			}
			dataIn.close();// ¹Ø±ÕÊý¾ÝÁ÷
			socket.close();
			
			
			response = readLine();
			if (!response.startsWith("226")) {
				System.out.println(response);
			}
//			BufferedReader dataIn = new BufferedReader(new InputStreamReader(dataSocket.getInputStream()));
//			String line;
//			while ((line = dataIn.readLine()) != null) {
//				fileList.add(line);
//			}
//
//			dataIn.close();// ¹Ø±ÕÊý¾ÝÁ÷
//			dataSocket.close();// ¹Ø±ÕÊý¾ÝÁ¬½Ó
//			response = readLine();

		} catch (IOException ioex) {
			ioex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return fileList;
	}

	///
	/// Close the FTP connection.
	/// ÍË³öµÇÂ¼²¢ÖÕÖ¹Á¬½ÓQUIT
	public synchronized void close() throws IOException {
		try {
			sendCommand("QUIT ");
		} finally {
			cleanup();
//			System.out.println("ÕýÔÚ¹Ø±Õ......");
//			JOptionPane.showConfirmDialog(null, " ÒÑ¶Ï¿ªÁ¬½Ó£¡", " Á¬½ÓÐÅÏ¢", JOptionPane.CLOSED_OPTION,
//					JOptionPane.INFORMATION_MESSAGE);
		}
	}

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
	///
	public void setBinaryMode(Boolean mode) throws IOException {

		if (mode) {
			sendCommand("TYPE I ");
		} else {
			sendCommand("TYPE A ");
		}
		response = readLine();
		if (!response.startsWith("200")) {
			throw new IOException("Caught Error " + response);
		}
	}

	// ÏÔÊ¾µ±Ç°Ô¶³Ì¹¤×÷Ä¿Â¼PWD
	public synchronized String pwd() throws IOException {
		if(connectSocket == null){
			JOptionPane.showConfirmDialog(null, "·þÎñÆ÷ÉÐÎ´Á¬½Ó£¡", "Á¬½ÓÐÅÏ¢", JOptionPane.CLOSED_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
			return null;
		}
		if(!logined){
			JOptionPane.showConfirmDialog(null, "ÉÐÎ´µÇÂ¼£¡", "µÇÂ¼ÐÅÏ¢", JOptionPane.CLOSED_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
			return null;
		}
		sendCommand("XPWD ");
		String dir = null;
		response = readLine();
		if (response.startsWith("257")) { // ·þÎñÆ÷ÏìÓ¦ÐÅÏ¢Èç£º257 "/C:/TEMP/" is current
											// directory.½ØÈ¡Á½ÒýºÅÖ®¼äµÄÄÚÈÝ
			int fristQuote = response.indexOf('\"');
			int secondQuote = response.indexOf('\"', fristQuote + 1);
			if (secondQuote > 0) {
				dir = response.substring(fristQuote + 1, secondQuote);
			}
		}
//		System.out.println(dir);
		return dir;
	}

	// CWD ¸Ä±äÔ¶³ÌÏµÍ³µÄ¹¤×÷Ä¿Â¼
	public synchronized boolean cwd(String dir) throws IOException {
		if (dir.equals("/")) {// ¸ùÂ·¾¶
			System.out.println("µ±Ç°Â·¾¶ÊÇ¸ùÄ¿Â¼£¡");
		}
		if (!logined) {
			login();
		}
		sendCommand("CWD " + dir);
		response = readLine();
//		System.out.println(response);
		if (response.startsWith("250")) {
			return true;
		} else {
			return false;
		}
	}

	// ÉÏ´«ÎÄ¼þ
	public synchronized boolean upload(File fsend) throws IOException {
////		System.err.println(localFileName);
//		dataSocket = createDataSocket();
		
		
//		int i = localFileName.lastIndexOf("/");
//		if (i == -1) {
//			i = localFileName.lastIndexOf("\\");
//		}
//		String element_1 = "";
//		if (i != -1) {
//			element_1 = localFileName.substring(i + 1);
//		}
//		sendCommand("STOR " + element_1);
		
//		response = readLine();
//		if (!response.startsWith("1")) {
//			System.out.println(response);
//		}
		
		
		
		String localFileName = fsend.getName();
		
		sendCommand("STOR " + localFileName);
		
//		FileInputStream dataIn = new FileInputStream(localFileName);
//		BufferedOutputStream dataOut = new BufferedOutputStream(dataSocket.getOutputStream());
//		byte[] buffer = new byte[4096];
//		int bytesRead = 0;
//		do {
//			bytesRead = dataIn.read(buffer);
//			if (bytesRead != -1) {
//				dataOut.write(buffer, 0, bytesRead);
//			}
//		} while (bytesRead != -1);
//		dataOut.flush();
//		dataOut.close();
//		dataIn.close();
//		dataSocket.close();// ¹Ø±Õ´ËÊý¾ÝÁ¬½Ó
		
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
		
		response = readLine();

		if (response.startsWith("226")) {
			JOptionPane.showConfirmDialog(null, " ÎÄ¼þÉÏ´«³É¹¦£¡", " ÉÏ´«ÐÅÏ¢", JOptionPane.CLOSED_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
		}
		return (response.startsWith("226"));
	}

	// ÏÂÔØÎÄ¼þ RETR
	public synchronized boolean download(String remoteFile, String localFile) throws IOException {

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
		JFileChooser jfc = new JFileChooser();
		if(jfc.showSaveDialog(new Label()) == JFileChooser.APPROVE_OPTION){
			File fsave = jfc.getSelectedFile();
//			ProgressBar pBar = new ProgressBar("ÏÂÔØ", bis);
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
		bis.close();
		socket.close();
		
		
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
		response = readLine();

		if (response.startsWith("226")) {
			JOptionPane.showConfirmDialog(null, "ÏÂÔØ³É¹¦", " ÏÂÔØÐÅÏ¢", JOptionPane.CLOSED_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
		}
		else{
			JOptionPane.showConfirmDialog(null, "ÎÄ¼þ²»´æÔÚ£¬ÏÂÔØÊ§°Ü", " ÏÂÔØÐÅÏ¢", JOptionPane.CLOSED_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
		}
		return (response.startsWith("226"));
	}

	// ÔÚÔ¶³Ì·þÎñÆ÷ÉÏ´´½¨Ò»¸öÄ¿Â¼
	public void mkdir(String dirName) throws IOException {

		if(connectSocket == null){
			JOptionPane.showConfirmDialog(null, "·þÎñÆ÷ÉÐÎ´Á¬½Ó£¡", "Á¬½ÓÐÅÏ¢", JOptionPane.CLOSED_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if(!logined){
			JOptionPane.showConfirmDialog(null, "ÉÐÎ´µÇÂ¼£¡", "µÇÂ¼ÐÅÏ¢", JOptionPane.CLOSED_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if(dirName.equals("")){
			JOptionPane.showConfirmDialog(null, "ÇëÊäÈëÄ¿Â¼Ãû£¡", "Ä¿Â¼ÐÅÏ¢", JOptionPane.CLOSED_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		sendCommand("MKD " + dirName); // ´´½¨Ä¿Â¼
		response = readLine();
		if(response.startsWith("550")){
			JOptionPane.showConfirmDialog(null, "Ä¿Â¼  " + dirName + "  ÒÑ¾­´æÔÚ£¡", " ´´½¨Ä¿Â¼", JOptionPane.CLOSED_OPTION,
					JOptionPane.INFORMATION_MESSAGE); // Ä¿Â¼ÒÑ¾­´æÔÚ
		}
		else if (!response.startsWith("250")) { // FTPÃüÁî·¢ËÍ¹ý³Ì·¢ÉúÒì³£
			System.out.println(response);
		} else {
			JOptionPane.showConfirmDialog(null, "´´½¨Ä¿Â¼  " + dirName + "  ³É¹¦£¡", " ´´½¨Ä¿Â¼", JOptionPane.CLOSED_OPTION,
					JOptionPane.INFORMATION_MESSAGE); // ³É¹¦´´½¨Ä¿Â¼
		}

	}

	// É¾³ýÔ¶³Ì¸ö·þÎñÆ÷ÉÏµÄÒ»¸öÄ¿Â¼
	public void rmdir(String dirName) throws IOException {
//		if (!logined) { // Èç¹ûÉÐÎ´Óë·þÎñÆ÷Á¬½Ó£¬ÔòÁ¬½Ó·þÎñÆ÷
//			login();
//		}
		if(dirName.equals("")){
			JOptionPane.showConfirmDialog(null, "ÇëÊäÈëÄ¿Â¼Ãû£¡", "Ä¿Â¼ÐÅÏ¢", JOptionPane.CLOSED_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		sendCommand("DELE " + dirName);
		response = readLine();
		if (response.startsWith("550")) { // FTPÃüÁî·¢ËÍ¹ý³Ì·¢ÉúÒì³£
//			System.out.println(response);
			JOptionPane.showConfirmDialog(null, "ÎÄ¼þ" + dirName + "  ²»´æÔÚ£¬É¾³ýÊ§°Ü", " É¾³ýÄ¿Â¼", JOptionPane.CLOSED_OPTION,
					JOptionPane.INFORMATION_MESSAGE); //ÎÄ¼þ²»´æÔÚ
		} 
		else if(response.startsWith("333")){
			JOptionPane.showConfirmDialog(null, "ÎÄ¼þ" + dirName + "  Õý±»ÆäËûÓ¦ÓÃ³ÌÐòÕ¼ÓÃ£¬É¾³ýÊ§°Ü", " É¾³ýÄ¿Â¼", JOptionPane.CLOSED_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
		}
		else {
			JOptionPane.showConfirmDialog(null, "É¾³ýÎÄ¼þ" + dirName + "  ³É¹¦£¡", " É¾³ýÄ¿Â¼", JOptionPane.CLOSED_OPTION,
					JOptionPane.INFORMATION_MESSAGE); // ³É¹¦É¾³ýÄ¿Â¼
		}

	}

	// ½¨Á¢Êý¾ÝÁ¬½Ó
	private Socket createDataSocket() throws IOException {
		sendCommand("PASV ");
		dataSocket = new Socket(remoteHost, 33333);
		return dataSocket;
	}
//	private Socket createDataSocket() throws IOException {
//
//		sendCommand("PASV "); // ²ÉÓÃPasvÄ£Ê½£¨±»¶¯Ä£Ê½£©£¬ÓÉ·þÎñÆ÷·µ»ØÊý¾Ý´«ÊäµÄÁÙÊ±¶Ë¿ÚºÅ£¬Ê¹ÓÃ¸Ã¶Ë¿Ú½øÐÐÊý¾Ý´«Êä
//		
//		response = readLine();
//		System.err.println(response);
//		if (!response.startsWith("227")) { // FTPÃüÁî´«Êä¹ý³Ì·¢ÉúÒì³£
//			System.out.println(response);
//		}
//		
//		
//		String clientIp = "";
//		int port = -1;
//		int opening = response.indexOf('('); // ²ÉÓÃPasvÄ£Ê½·þÎñÆ÷·µ»ØµÄÐÅÏ¢Èç¡°227 Entering
//												// Passive Mode
//												// (127,0,0,1,64,2)¡±
//		int closing = response.indexOf(')', opening + 1); // È¡"()"Ö®¼äµÄÄÚÈÝ£º127,0,0,1,64,2
//															// £¬Ç°4¸öÊý×ÖÎª±¾»úIPµØÖ·£¬×ª»»³É127.0.0.1¸ñÊ½
//		if (closing > 0) { // ¶Ë¿ÚºÅÓÉºó2¸öÊý×Ö¼ÆËãµÃ³ö£º64*256+2=16386
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

	// ÓÃÓÚ¶ÁÈ¡·þÎñÆ÷·µ»ØµÄÏìÓ¦ÐÅÏ¢
	private String readLine() throws IOException {
		String line = inData.readLine();
		if (debug) {
			System.out.println("< " + line);
		}
		return line;
	}

	// ÓÃÓÚ·¢ËÍÃüÁî
	public void sendCommand(String line) {
		if (connectSocket == null) {
			System.out.println("FTPÉÐÎ´Á¬½Ó"); // Î´½¨Á¢Í¨ÐÅÁ´½Ó£¬Å×³öÒì³£¾¯¸æ
		}
		try {
			outData.write(line + "\r\n"); // ·¢ËÍÃüÁî
			outData.flush(); // Ë¢ÐÂÊä³öÁ÷
			if (debug) {
				System.out.println("> " + line); // Í¬Ê±¿ØÖÆÌ¨Êä³öÏàÓ¦ÃüÁîÐÅÏ¢£¬ÒÔ±ã·ÖÎö
			}
		} catch (Exception e) {
			connectSocket = null;
			System.out.println(e);
			return;
		}
	}
}
