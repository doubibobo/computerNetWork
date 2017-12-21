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

	private Socket connectSocket;// �������ӣ����ڴ��ͺ���Ӧ����
	private Socket dataSocket;// �������ӣ��������ݴ���
	private BufferedReader inData;// �������������ڶ�ȡ������Ϣ��������
	private BufferedWriter outData;// �������������ڴ����û������������
	private String response = null;// ��������Ϣ��װ���ַ���
	private String remoteHost;// Զ��������
	private int remotePort;// ͨ�Ŷ˿ں�
	private String remotePath;// Զ��·��
	private String user;// �û���
	private String passWord;// �û�����
	File rootPath = new File("/");// ��·��
	File currentPath = rootPath;// ��ǰ·��
	private boolean logined;// �ж��Ƿ��¼�������ı�־
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

	// ���÷�����������IP��ַ��
	public void setRemoteHost(String remoteHost) {
		this.remoteHost = remoteHost;
	}

	// ���ط�����������IP��ַ��
	public String getRemoteHost() {
		return remoteHost;
	}

	// ���÷������˿�(PORT)
	public void setRemotePort(int remotePort) {
		this.remotePort = remotePort;
	}

	// ���ط������˿ڣ�PORT��
	public int getRemotePort() {
		return remotePort;
	}

	// ����Զ��Ŀ¼·��
	public void setRemotePath(String remotePath) {
		this.remotePath = remotePath;
	}

	//��ȡԶ��Ŀ¼·��
	public String getRemotePath() {
		return remotePath;
	}

	// ����Զ�̷��������û���
	public void setUser(String user) {
		this.user = user;
	}

	// ����Զ�̷�����������
	public void setPW(String password) {
		this.passWord = password;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	/**
	 * �û��ڿͻ��˽�������
	 */
	public void connect() {
		try {
			if (connectSocket == null) {//�����ǰû�п������ӣ������ɻ���µĿ�������
				//һ���µĿ������Ӱ���Զ�������ż������Լ�ͨ�Ŷ˿ںţ���������Ӧ�����ü���21�Ŷ˿�
				connectSocket = new Socket(remoteHost, remotePort);
				inData = new BufferedReader(new InputStreamReader(connectSocket.getInputStream()));// ������Ϣ(�ַ�������)
				//�ͻ�����Ҫ��ȡ�������˵���Ϣ������Զ�������ţ����������Լ�ͨ�Ŷ˿ںţ�
				outData = new BufferedWriter(new OutputStreamWriter(connectSocket.getOutputStream()));// �����Ϣ(�ַ������)
				//�ڶ�ȡ��Զ�������ţ��������Լ�ͨ�Ŷ˿ںź���ָ���ķ�����ͨ��ͨ�Ŷ˿ڴ�����Ӧ��ָ�
				response = readTheAction();
				//��ȡ���������ؿͻ��˵���Ϣ
				JOptionPane.showConfirmDialog(null, "�������ɹ����ӣ�", "������Ϣ", JOptionPane.CLOSED_OPTION,
						JOptionPane.INFORMATION_MESSAGE);
			}
			else{
				JOptionPane.showConfirmDialog(null, "�������ӣ�", "������Ϣ", JOptionPane.CLOSED_OPTION,
						JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (Exception e) {
			JOptionPane.showConfirmDialog(null, " ����ʧ��", " ������Ϣ", JOptionPane.CLOSED_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * �û��ڿͻ��˽��е�½
	 */
	public void login() {
		try {
			//��Ҫ���п������ӵĽ���
			if (connectSocket == null) {
				JOptionPane.showConfirmDialog(null, " ��������δ���ӣ��������ӣ�", " ������Ϣ", JOptionPane.CLOSED_OPTION,
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			if(logined){
				JOptionPane.showConfirmDialog(null, " ���ѵ�¼��", " ��¼��Ϣ", JOptionPane.CLOSED_OPTION,
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			//�ڿͻ������������ʵ�����Ӻ�ſ���ʵ���û��ĵ�¼��
			sendCommand("USER " + user);
			response = readTheAction();
			//���û�����Ϣ���͵��������ˣ��������˿��Ի����Ӧ�����룬Ȼ�����û��ڿͻ��������������Ƚ�
			//�����ȷ��Ϊ��¼�ɹ�������������¼ʧ��
			if (!response.startsWith("331")) {
//				cleanup();
				JOptionPane.showConfirmDialog(null, " �û������������", " ������Ϣ", JOptionPane.CLOSED_OPTION,
						JOptionPane.INFORMATION_MESSAGE);
			}
			sendCommand("PASS " + passWord);
			response = readTheAction();

////			System.err.println(response);
			if (!response.startsWith("230")) {
//				cleanup();
				JOptionPane.showConfirmDialog(null, " �û������������", " ������Ϣ", JOptionPane.CLOSED_OPTION,
						JOptionPane.INFORMATION_MESSAGE);
//				System.out.println(response);
				return;
			}
			//��������ȷ����logined��ֵ�仯Ϊtrue��֮����ʾΪ��¼�ɹ�
			logined = true;
			JOptionPane.showConfirmDialog(null, " ��½�ɹ���", " ������Ϣ", JOptionPane.CLOSED_OPTION,
					JOptionPane.INFORMATION_MESSAGE);


//			cwd(remotePath);


		} catch (Exception e) {
			JOptionPane.showConfirmDialog(null, " ��½ʧ�ܣ�", " ��½��Ϣ", JOptionPane.CLOSED_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
		}//ץȡ��¼ʧ�ܵ��쳣
	}

	/**
	 * �������˼����˿�4700�����ݲ��ñ�����ʽ��������
	 * ����ν������ʽ��ָ�������������ݴ���˿ڣ�
	 * @param mask
	 * @return
	 * @throws IOException
	 */
	public ArrayList<String> list(String mask) throws IOException {
		if (!logined) {
			System.out.println("�û�δ��½��");
		}
		ArrayList<String> fileList = new ArrayList<String>();
		try {
//			dataSocket = createDataSocket();
			if (mask == null || mask.equals("") || mask.equals(" ")) {
				sendCommand("LIST");
			} else {
				sendCommand("LIST " + mask);
			}
			//����������
			Socket socket = new Socket(remoteHost, 4700);
			BufferedReader dataIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String line;
			while ((line = dataIn.readLine()) != null) {
				fileList.add(line);//���������Ŀ¼�б�������������

			}
			dataIn.close();// �ر�������
			socket.close();

			for (String fileName : fileList) {
				System.out.println(fileName);
			}

			response = readTheAction();//��ȡ������Ϣ����װ
			if (!response.startsWith("226")) {
				System.out.println(response);
			}
//			BufferedReader dataIn = new BufferedReader(new InputStreamReader(dataSocket.getInputStream()));
//			String line;
//			while ((line = dataIn.readLine()) != null) {
//				fileList.add(line);
//			}
//
//			dataIn.close();// �ر�������
//			dataSocket.close();// �ر���������
//			response = readLine();

		} catch (IOException ioex) {
			ioex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return fileList;
	}

	/// �ر�FTP���ӣ��˳���¼����ֹ����QUIT
	public synchronized void close() throws IOException {
		try {
			sendCommand("QUIT ");
		} finally {
			cleanup();
//			System.out.println("���ڹر�......");
//			JOptionPane.showConfirmDialog(null, " �ѶϿ����ӣ�", " ������Ϣ", JOptionPane.CLOSED_OPTION,
//					JOptionPane.INFORMATION_MESSAGE);
		}
	}
	//��տ���������Ϣ����ʹ��¼���ӻָ���ʼ״̬
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
	 * ���÷������˶����ƴ���
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

	// ��ʾ��ǰԶ�̹���Ŀ¼PWD
	public synchronized String pwd() throws IOException {
		if(connectSocket == null){
			JOptionPane.showConfirmDialog(null, "��������δ���ӣ�", "������Ϣ", JOptionPane.CLOSED_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
			return null;
		}
		if(!logined){
			JOptionPane.showConfirmDialog(null, "��δ��¼��", "��¼��Ϣ", JOptionPane.CLOSED_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
			return null;
		}
		sendCommand("XPWD ");
		String dir = null;
		response = readTheAction();//��������Ӧ
		if (response.startsWith("257")) { // ��������Ӧ��Ϣ�磺257 "/C:/TEMP/" is current
											// directory.��ȡ������֮�������
			int fristQuote = response.indexOf('\"');
			int secondQuote = response.indexOf('\"', fristQuote + 1);
			if (secondQuote > 0) {
				dir = response.substring(fristQuote + 1, secondQuote);
			}
		}
//		System.out.println(dir);
		return dir;
	}

	// CWD �ı�Զ��ϵͳ�Ĺ���Ŀ¼
	public synchronized boolean cwd(String dir) throws IOException {
		if (dir.equals("/")) {// ��·��
			System.out.println("��ǰ·���Ǹ�Ŀ¼��");
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
	 * �ϴ����ļ�����
	 * @param fsend
	 * @return
	 * @throws IOException
	 */
	public synchronized boolean upload(File fsend) throws IOException {
		
		String localFileName = fsend.getName();//��ȡ�ļ�����
		
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
		
		response = readTheAction();//���շ������˵���Ϣ�����ڷ��ؿͻ����ж��ļ��Ƿ��ϴ��ɹ�

		if (response.startsWith("226")) {
			JOptionPane.showConfirmDialog(null, " �ļ��ϴ��ɹ���", " �ϴ���Ϣ", JOptionPane.CLOSED_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
		}
		return (response.startsWith("226"));//����ļ���226��ͷ��Ϊ�ϴ��ɹ�
	}

	/**
	 * �����ļ� RETR, ���������������˿�
	 * ���ñ�����ʽ��������
	 * TODO ���Ҫ�ͷ���������ϼӶϵ���������
	 * @param remoteFile ���������ļ�·��
	 * @param localFile ���������ص����ص��ļ�·����Ĭ�ϱ�����
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
		JFileChooser jfc = new JFileChooser();//��ȡѡ�����ļ�
		if(jfc.showSaveDialog(new Label()) == JFileChooser.APPROVE_OPTION){
			File fsave = jfc.getSelectedFile();
//			ProgressBar pBar = new ProgressBar("����", bis);
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
			JOptionPane.showConfirmDialog(null, "���سɹ�", " ������Ϣ", JOptionPane.CLOSED_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showConfirmDialog(null, "�ļ������ڣ�����ʧ��", " ������Ϣ", JOptionPane.CLOSED_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
		}
		return (response.startsWith("226"));
	}

	/**
	 * �������ܣ��ϵ��������Է���(�����ض˿�����)
	 * ����������
	 * ���ñ�����ʽ��������
	 * ���ö����Ʒ�ʽ���д���
	 * @param remoteFile	Ҫ���صķ������˵��ļ�·��
	 * @param myProgressBar	������
	 * @return
	 * @throws IOException
	 */
	public synchronized boolean download1(String remoteFile, JProgressBar myProgressBar, JButton stop, JButton goOn) throws IOException {
        // Ҫ������ļ�·��
        File file;
        JFileChooser jfc = new JFileChooser();//��ȡѡ�����ļ�
        if(jfc.showSaveDialog(new Label()) == JFileChooser.APPROVE_OPTION) {
            file = jfc.getSelectedFile();
        } else {
            file = new File("d:");
        }
        Thread theDownloadThread = new Thread(new Runnable() {
            @Override
            public void run() {
                // �������ݴ���˿�
                System.out.println(remoteFile);
                sendCommand("RETR " + remoteFile);
                try {
                    // �����������˵��׽���
                    Socket socket = new Socket(remoteHost, 4878);

                    OutputStream theDownloadOut = socket.getOutputStream();
                    InputStream theDownloadIn = socket.getInputStream();


                        // �ж��ļ�Ҫ��ʼ���͵�λ��
                        int startIndex = 0;
                        // file�ǽ���֮��Ҫ������ļ���·��
                        // �ڴ��ж��ļ��Ƿ���ڣ�ԭ���ж���
                        // 1���ļ������˲��ֵ�����ȫ��
                        // 2���ļ�û�н��й�����
                        if (file.exists()) {
                            startIndex = (int) file.length();
                        }
                        System.out.println("Client startIndex: " + startIndex);
                        // �ļ�д����
                        RandomAccessFile accessFile = new RandomAccessFile(file, "rw");
                        // ���ϵ����Ϣ�������������˿�
                        theDownloadOut.write(String.valueOf(startIndex).getBytes());
                        theDownloadOut.write(0);
                        theDownloadOut.flush();

                        // ��¼�ļ��ĳ���
                        int temp = 0;
                        StringWriter sw = new StringWriter();
                        // �ӷ������˽����ļ��ĳ���
                        while ((temp = theDownloadIn.read()) != 0) {
                            sw.write(temp);
                            sw.flush();
                        }
                        // ����Ҫ��ȡ���ļ��ĳ���
                        int length = Integer.parseInt(sw.toString());
                        System.out.println("Client fileLength" + length);
                        // ���ö����ƻ����ļ�����
                        byte[] buffer = new byte[1024*10];
                        // ��ȡʣ��Ҫ�������ļ��ĳ���
                        int total = length - startIndex;
                        accessFile.skipBytes(startIndex);
                        while (true) {
                            // ���ʣ��ĳ���Ϊ0�������˴β���
                            if (total == 0) {
								break;
							}
                            // ������ζ�ȡ�ĳ�����ʣ��ĳ���
                            int len = total;
                            if (len > buffer.length) {
                                // ������������˴ζ����������������ļ��ĳ���
                                len = buffer.length;
                            }
                            // ���������ȡ���ļ�����
                            int realLength = theDownloadIn.read(buffer, 0, len);
                            // ����ʣ����ļ�����
                            total = total - realLength;
                            if (realLength > 0) {
                                // �����ζ���������д�뵽����ļ��������
                                accessFile.write(buffer, 0, realLength);
                            } else {
                                break;
                            }

                            // ��ȡ�ļ����صĽ���
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
                // ���ȶԽ����������
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

        JOptionPane.showConfirmDialog(null, "��������", " ������Ϣ", JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);

//		response = readTheAction();

//		if (response.startsWith("226")) {
//			JOptionPane.showConfirmDialog(null, "���سɹ�", " ������Ϣ", JOptionPane.CLOSED_OPTION,
//					JOptionPane.INFORMATION_MESSAGE);
//		} else {
//			JOptionPane.showConfirmDialog(null, "�ļ������ڣ�����ʧ��", " ������Ϣ", JOptionPane.CLOSED_OPTION,
//					JOptionPane.INFORMATION_MESSAGE);
//		}
//		return (response.startsWith("226"));

        return true;
	}

	/**
	 * ��Զ�̷������ϴ���һ��Ŀ¼
	 * @param dirName
	 * @throws IOException
	 */
	public void mkdir(String dirName) throws IOException {

		if(connectSocket == null){
			JOptionPane.showConfirmDialog(null, "��������δ���ӣ�", "������Ϣ", JOptionPane.CLOSED_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if(!logined){
			JOptionPane.showConfirmDialog(null, "��δ��¼��", "��¼��Ϣ", JOptionPane.CLOSED_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if(dirName.equals("")){
			JOptionPane.showConfirmDialog(null, "������Ŀ¼����", "Ŀ¼��Ϣ", JOptionPane.CLOSED_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		sendCommand("MKD " + dirName); // ����Ŀ¼
		response = readTheAction();//��������η������봴��Ŀ¼����Ϣ
		if(response.startsWith("550")){
			JOptionPane.showConfirmDialog(null, "Ŀ¼  " + dirName + "  �Ѿ����ڣ�", " ����Ŀ¼", JOptionPane.CLOSED_OPTION,
					JOptionPane.INFORMATION_MESSAGE); // Ŀ¼�Ѿ�����
		}
		else if (!response.startsWith("250")) { // FTP����͹��̷����쳣
			System.out.println(response);
		} else {
			JOptionPane.showConfirmDialog(null, "����Ŀ¼  " + dirName + "  �ɹ���", " ����Ŀ¼", JOptionPane.CLOSED_OPTION,
					JOptionPane.INFORMATION_MESSAGE); // �ɹ�����Ŀ¼
		}

	}

	/**
	 * ɾ��Զ�̸��������ϵ�һ��Ŀ¼
	 * @param dirName
	 * @throws IOException
	 */
	public void delete(String dirName) throws IOException {
//		if (!logined) { // �����δ����������ӣ������ӷ�����
//			login();
//		}

		sendCommand("DELE " + dirName);
		response = readTheAction();//��������˷���ɾ��ָ��Ŀ¼������
		if (response.startsWith("550")) { // FTP����͹��̷����쳣
//			System.out.println(response);
			JOptionPane.showConfirmDialog(null, "�ļ�" + dirName + "  �����ڣ�ɾ��ʧ��", " ɾ��Ŀ¼", JOptionPane.CLOSED_OPTION,
					JOptionPane.INFORMATION_MESSAGE); //�ļ�������
		} 
		else if(response.startsWith("333")){
			JOptionPane.showConfirmDialog(null, "�ļ�" + dirName + "  ��������Ӧ�ó���ռ�ã�ɾ��ʧ��", " ɾ��Ŀ¼", JOptionPane.CLOSED_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
		}//ɾ���ļ���Ҫ�ж��Ƿ��ļ����ڱ�ռ��
		else {
			JOptionPane.showConfirmDialog(null, "ɾ���ļ�" + dirName + "  �ɹ���", " ɾ��Ŀ¼", JOptionPane.CLOSED_OPTION,
					JOptionPane.INFORMATION_MESSAGE); // �ɹ�ɾ��Ŀ¼
		}//����ļ�������δ��ռ������Ա�ɾ����

	}

	/**
	 * �����������ӣ����ñ�����ʽ���У�
	 * @return
	 * @throws IOException
	 */
	private Socket createDataSocket() throws IOException {
		sendCommand("PASV ");
		//���ñ���ģʽ��������ʱ���ɷ������������ݴ������ʱ�˿ںţ�ʹ�øö˿ڽ������ݴ���
		dataSocket = new Socket(remoteHost, 33333);//���ݶ�ջ
		return dataSocket;
	}
//	private Socket createDataSocket() throws IOException {
//
//		sendCommand("PASV "); // ����Pasvģʽ������ģʽ�����ɷ������������ݴ������ʱ�˿ںţ�ʹ�øö˿ڽ������ݴ���
//		
//		response = readLine();
//		System.err.println(response);
//		if (!response.startsWith("227")) { // FTP�������̷����쳣
//			System.out.println(response);
//		}
//		
//		
//		String clientIp = "";
//		int port = -1;
//		int opening = response.indexOf('('); // ����Pasvģʽ���������ص���Ϣ�硰227 Entering
//												// Passive Mode
//												// (127,0,0,1,64,2)��
//		int closing = response.indexOf(')', opening + 1); // ȡ"()"֮������ݣ�127,0,0,1,64,2
//															// ��ǰ4������Ϊ����IP��ַ��ת����127.0.0.1��ʽ
//		if (closing > 0) { // �˿ں��ɺ�2�����ּ���ó���64*256+2=16386
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
	 * ��ȡ���������ص���Ӧ��Ϣ
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
	 * �ڿ��������Ϸ��������������
	 * @param line
	 */
	public void sendCommand(String line) {
		if (connectSocket == null) {
			System.out.println("FTP��δ����"); // δ����ͨ�����ӣ��׳��쳣����
		}
		try {
			outData.write(line + "\r\n"); // ��������
			outData.flush(); // ˢ�������
			if (debug) {
				System.out.println("> " + line); // ͬʱ����̨�����Ӧ������Ϣ���Ա����
			}
		} catch (Exception e) {
			connectSocket = null;
			System.out.println(e);
			return;
		}
	}
}
