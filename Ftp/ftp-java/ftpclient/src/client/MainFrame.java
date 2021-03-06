package client;

import java.awt.Image;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author tcl
 */
public class MainFrame extends javax.swing.JFrame {

	FtpFunction FtpFunction = new FtpFunction();

	/** Creates new form mainFrame */
	public MainFrame() {
		setTitle("客户端");
		setLocation(250, 150);
		initComponents();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jLabel1 = new javax.swing.JLabel();
		jTabbedPane1 = new javax.swing.JTabbedPane();
		jPanelLogin = new javax.swing.JPanel();
		jLabelUserName = new javax.swing.JLabel();
		jTextFieldUserName = new javax.swing.JTextField();
		jLabelPassword = new javax.swing.JLabel();
		jTextFieldPassword = new javax.swing.JTextField();
		jButtonLogin = new javax.swing.JButton();
		jButtonConnectServer = new javax.swing.JButton();
		jLabelServerIP = new javax.swing.JLabel();
		jTextFieldServerIP = new javax.swing.JTextField();
		jLabelPort = new javax.swing.JLabel();
		jTextFieldPort = new javax.swing.JTextField();
		jLabelRedHint = new javax.swing.JLabel();
		jButtonQuit = new javax.swing.JButton();
		jPanelUpload = new javax.swing.JPanel();
		jLabelFileUploadPath = new javax.swing.JLabel();
		jTextField3 = new javax.swing.JTextField();
		jButtonUpload = new javax.swing.JButton();
		jPanelDownload = new javax.swing.JPanel();
		jScrollPane1 = new javax.swing.JScrollPane();
		jTextAreaFileList = new javax.swing.JTextArea();
		jButtonFileList = new javax.swing.JButton();
		jLabelDownloadFilePath = new javax.swing.JLabel();
		jLabelFileSavePath = new javax.swing.JLabel();
		jTextFieldRemoteFile = new javax.swing.JTextField();
		jTextFieldLocalFile = new javax.swing.JTextField();
		jButtonDownloadFile = new javax.swing.JButton();
		jPanelDir = new javax.swing.JPanel();
		jLabelMKD = new javax.swing.JLabel();
		jTextFieldDirName = new javax.swing.JTextField();
		jButtonMKD = new javax.swing.JButton();
		jLabelCurDir = new javax.swing.JLabel();
		jTextFieldCurDir = new javax.swing.JTextField();
		jButtonCurDir = new javax.swing.JButton();
		jLabelDelFile = new javax.swing.JLabel();
		jTextFieldDelFileName = new javax.swing.JTextField();
		jButtonDelFile = new javax.swing.JButton();
		jLabelChangeDir = new javax.swing.JLabel();
		jTextFieldChangeDir = new javax.swing.JTextField();
		jButtonChangeDir = new javax.swing.JButton();

		jLabel1.setText("jLabel1");

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				if(FtpFunction.getConnectSocket() != null)
					FtpFunction.sendCommand("QUIT ");
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});

		jTabbedPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

		jLabelUserName.setText("用户名：");

		jLabelPassword.setText("密码：");

		jButtonLogin.setText("登陆");
		jButtonLogin.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonLoginActionPerformed(evt);
			}
		});

		jButtonConnectServer.setText("连接服务器");
		jButtonConnectServer.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonConnectServerActionPerformed(evt);
			}
		});

		jLabelServerIP.setText("远处服务器地址：");

		jTextFieldServerIP.setText("localhost");

		jLabelPort.setText("端口号：");

		jTextFieldPort.setText("21");

		jLabelRedHint.setFont(new java.awt.Font("宋体", 1, 12));
		jLabelRedHint.setForeground(new java.awt.Color(255, 0, 51));
		jLabelRedHint.setText("注意：请先连接服务器再登陆");

		jButtonQuit.setText("断开连接");
		jButtonQuit.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonQuitActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanelLogin);
		jPanelLogin.setLayout(jPanel1Layout);
		jPanel1Layout
				.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jPanel1Layout
								.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(
										javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(jPanel1Layout
												.createSequentialGroup().addGroup(jPanel1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(jPanel1Layout.createSequentialGroup()
																.addGap(117, 117, 117)
																.addGroup(jPanel1Layout
																		.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.TRAILING)
																		.addComponent(jLabelUserName).addComponent(jLabelPassword))
																.addGap(18, 18, 18)
																.addGroup(jPanel1Layout
																		.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																		.addComponent(jTextFieldPassword,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				88,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addComponent(jTextFieldUserName,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				117, Short.MAX_VALUE)))
														.addGroup(jPanel1Layout.createSequentialGroup()
																.addGap(123, 123, 123).addComponent(jLabelPort)
																.addGap(18, 18, 18).addComponent(jTextFieldPort,
																		javax.swing.GroupLayout.PREFERRED_SIZE, 58,
																		javax.swing.GroupLayout.PREFERRED_SIZE))
														.addGroup(jPanel1Layout.createSequentialGroup()
																.addGap(55, 55, 55)
																.addGroup(jPanel1Layout
																		.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																		.addComponent(jLabelRedHint)
																		.addGroup(jPanel1Layout.createSequentialGroup()
																				.addComponent(jLabelServerIP)
																				.addGap(37, 37, 37)
																				.addComponent(jTextFieldServerIP,
																						javax.swing.GroupLayout.PREFERRED_SIZE,
																						112,
																						javax.swing.GroupLayout.PREFERRED_SIZE)))))
												.addGap(94, 94, 94))
										.addGroup(jPanel1Layout.createSequentialGroup().addGap(193, 193, 193)
												.addComponent(jButtonLogin))
										.addGroup(jPanel1Layout.createSequentialGroup().addGap(184, 184, 184)
												.addComponent(jButtonConnectServer)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27,
														Short.MAX_VALUE)
												.addComponent(jButtonQuit).addGap(5, 5, 5)))
								.addGap(119, 119, 119)));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
						.addContainerGap().addComponent(jLabelRedHint)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabelServerIP).addComponent(jTextFieldServerIP, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(28, 28, 28)
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabelPort).addComponent(jTextFieldPort, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(13, 13, 13)
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jButtonConnectServer).addComponent(jButtonQuit))
						.addGap(18, 18, 18)
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabelUserName).addComponent(jTextFieldUserName, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(18, 18, 18)
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabelPassword).addComponent(jTextFieldPassword, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(18, 18, 18).addComponent(jButtonLogin).addGap(98, 98, 98)));

		jTabbedPane1.addTab("登陆", jPanelLogin);

		jLabelFileUploadPath.setText("文件路径：");

		jButtonUpload.setText("上传");
		jButtonUpload.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonUploadActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanelUpload);
		jPanelUpload.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel2Layout.createSequentialGroup().addGap(48, 48, 48).addComponent(jLabelFileUploadPath)
						.addGap(42, 42, 42)
						.addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 186,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(36, 36, 36).addComponent(jButtonUpload).addContainerGap(82, Short.MAX_VALUE)));
		jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel2Layout.createSequentialGroup().addGap(37, 37, 37)
						.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabelFileUploadPath)
								.addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jButtonUpload))
						.addContainerGap(301, Short.MAX_VALUE)));

		jTabbedPane1.addTab("上传文件", jPanelUpload);

		jTextAreaFileList.setColumns(20);
		jTextAreaFileList.setRows(5);
		jScrollPane1.setViewportView(jTextAreaFileList);

		jButtonFileList.setText("显示远程文件列表");
		jButtonFileList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonFileListActionPerformed(evt);
			}
		});

		jLabelDownloadFilePath.setText("所需下载的文件名：");

		jLabelFileSavePath.setText("下载后存放路径：");

		jButtonDownloadFile.setText("下载");
		jButtonDownloadFile.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonDownloadFileActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanelDownload);
		jPanelDownload.setLayout(jPanel3Layout);
		jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel3Layout
						.createSequentialGroup()
						.addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
								.addGroup(
										jPanel3Layout.createSequentialGroup().addGap(35, 35, 35).addGroup(jPanel3Layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(jButtonFileList)
												.addGroup(jPanel3Layout.createSequentialGroup()
														.addGroup(jPanel3Layout
																.createParallelGroup(
																		javax.swing.GroupLayout.Alignment.LEADING)
																.addComponent(jLabelDownloadFilePath).addComponent(jLabelFileSavePath))
														.addGap(18, 18, 18)
														.addGroup(
																jPanel3Layout
																		.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING,
																				false)
																		.addComponent(jTextFieldLocalFile)
																		.addComponent(jTextFieldRemoteFile,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				141, Short.MAX_VALUE))
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(jButtonDownloadFile))))
								.addGroup(jPanel3Layout.createSequentialGroup().addGap(50, 50, 50).addComponent(
										jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 379,
										javax.swing.GroupLayout.PREFERRED_SIZE)))
						.addContainerGap(84, Short.MAX_VALUE)));
		jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel3Layout.createSequentialGroup().addGap(25, 25, 25).addComponent(jButtonFileList)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 184,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(18, 18, 18)
						.addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabelDownloadFilePath).addComponent(jTextFieldRemoteFile,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(18, 18, 18)
						.addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabelFileSavePath)
								.addComponent(jTextFieldLocalFile, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jButtonDownloadFile))
						.addContainerGap(37, Short.MAX_VALUE)));

		jTabbedPane1.addTab("下载文件", jPanelDownload);

		jLabelMKD.setText("创建目录：");

		jButtonMKD.setText("创建");
		jButtonMKD.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonMKDActionPerformed(evt);
			}
		});

		jLabelCurDir.setText("当前目录：");

		jButtonCurDir.setText("显示");
		jButtonCurDir.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonCurDirActionPerformed(evt);
			}
		});

		jLabelDelFile.setText("删除目录：");

		jButtonDelFile.setText("删除");
		jButtonDelFile.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonDelFileActionPerformed(evt);
			}
		});

//		jLabelChangeDir.setText("改变远程工作目录：");
		jLabelChangeDir.setText("改变目录：  ");

		jButtonChangeDir.setText("改变");
		jButtonChangeDir.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonChangeDirActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanelDir);
		jPanelDir.setLayout(jPanel4Layout);
		jPanel4Layout
				.setHorizontalGroup(
						jPanel4Layout
								.createParallelGroup(
										javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(jPanel4Layout.createSequentialGroup().addGap(78, 78, 78)
										.addGroup(jPanel4Layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(jPanel4Layout.createSequentialGroup().addComponent(jLabelChangeDir)
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(jTextFieldChangeDir,
																javax.swing.GroupLayout.PREFERRED_SIZE, 137,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addGap(18, 18, 18).addComponent(jButtonChangeDir))
												.addGroup(jPanel4Layout.createSequentialGroup().addGroup(jPanel4Layout
														.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(jPanel4Layout.createSequentialGroup()
																.addComponent(jLabelMKD)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																.addComponent(jTextFieldDirName,
																		javax.swing.GroupLayout.PREFERRED_SIZE, 125,
																		javax.swing.GroupLayout.PREFERRED_SIZE))
														.addGroup(jPanel4Layout
																.createParallelGroup(
																		javax.swing.GroupLayout.Alignment.TRAILING,
																		false)
																.addGroup(javax.swing.GroupLayout.Alignment.LEADING,
																		jPanel4Layout.createSequentialGroup()
																				.addComponent(jLabelDelFile)
																				.addGap(18, 18, 18)
																				.addComponent(jTextFieldDelFileName))
																.addGroup(javax.swing.GroupLayout.Alignment.LEADING,
																		jPanel4Layout.createSequentialGroup()
																				.addComponent(jLabelCurDir)
																				.addGap(18, 18, 18)
																				.addComponent(jTextFieldCurDir,
																						javax.swing.GroupLayout.PREFERRED_SIZE,
																						116,
																						javax.swing.GroupLayout.PREFERRED_SIZE))))
														.addGap(35, 35, 35)
														.addGroup(jPanel4Layout
																.createParallelGroup(
																		javax.swing.GroupLayout.Alignment.LEADING)
																.addComponent(jButtonCurDir).addComponent(jButtonMKD)
																.addComponent(jButtonDelFile))))
										.addContainerGap(85, Short.MAX_VALUE)));
		jPanel4Layout.setVerticalGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel4Layout.createSequentialGroup().addGap(34, 34, 34)
						.addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabelMKD)
								.addComponent(jTextFieldDirName, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jButtonMKD))
						.addGap(27, 27, 27)
						.addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabelCurDir)
								.addComponent(jTextFieldCurDir, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jButtonCurDir))
						.addGap(38, 38, 38)
						.addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabelDelFile)
								.addComponent(jTextFieldDelFileName, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jButtonDelFile))
						.addGap(37, 37, 37)
						.addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabelChangeDir)
								.addComponent(jTextFieldChangeDir, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jButtonChangeDir))
						.addContainerGap(127, Short.MAX_VALUE)));

		jTabbedPane1.addTab("目录操作", jPanelDir);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 522, Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 393, Short.MAX_VALUE));

		pack();
		try{
			Image image=ImageIO.read(this.getClass().getResource("/image/a.jpg"));
			this.setIconImage(image);
		}catch(IOException e){
			e.printStackTrace();
		}
	}// </editor-fold>//GEN-END:initComponents
	// 用户登陆按钮

	private void jButtonLoginActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
		String user = jTextFieldUserName.getText().toString();
		String pw = jTextFieldPassword.getText().toString();
		FtpFunction.setUser(user);
		FtpFunction.setPW(pw);
		FtpFunction.login();
	}

	// 连接服务器
	private void jButtonConnectServerActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton2ActionPerformed
		String remoteHost = jTextFieldServerIP.getText().toString();
		int remotePort = Integer.parseInt(jTextFieldPort.getText().toString());
		FtpFunction.setRemoteHost(remoteHost);
		FtpFunction.setRemotePort(remotePort);
		FtpFunction.connect();
	}


	// 文件上传
	private void jButtonUploadActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton3ActionPerformed
		if(FtpFunction.getConnectSocket() == null){
			JOptionPane.showConfirmDialog(null, "服务器尚未连接！", "连接信息", JOptionPane.CLOSED_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if(!FtpFunction.isLogin()){
			JOptionPane.showConfirmDialog(null, "尚未登录！", "登录信息", JOptionPane.CLOSED_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		String localFileName = jTextField3.getText().toString();
		File fsend = null;
		JFileChooser jfc = new JFileChooser();
		if (jfc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
			fsend = jfc.getSelectedFile();
		}
		
		try {
			if(fsend != null)
				FtpFunction.upload(fsend);
		} catch (IOException ex) {
			Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
		}

	}// GEN-LAST:event_jButton3ActionPerformed

	private void jButtonFileListActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton5ActionPerformed
		try {
			if(FtpFunction.getConnectSocket() == null){
				JOptionPane.showConfirmDialog(null, "服务器尚未连接！", "连接信息", JOptionPane.CLOSED_OPTION,
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			ArrayList<String> fileList = FtpFunction.list("");
			jTextAreaFileList.setText("");
			for (int i = 0; i < fileList.size(); i++) {
				jTextAreaFileList.setText(jTextAreaFileList.getText().toString() + fileList.get(i) + "\n");
			}
		} catch (IOException ex) {
			Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
		}

	}// GEN-LAST:event_jButton5ActionPerformed

	private void jButtonMKDActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton4ActionPerformed
		String dirName = jTextFieldDirName.getText().toString();
		try {
			FtpFunction.mkdir(dirName);
			jTextFieldDirName.setText("");
		} catch (IOException ex) {
			Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
		}

	}// GEN-LAST:event_jButton4ActionPerformed
		// 断开连接

	private void jButtonQuitActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton6ActionPerformed
		try {
			if(FtpFunction.getConnectSocket() == null){
				JOptionPane.showConfirmDialog(null, "已经断开！", " 连接信息", JOptionPane.CLOSED_OPTION,
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			FtpFunction.close();
		} catch (IOException ex) {
			Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
		}
		JOptionPane.showConfirmDialog(null, " 成功断开连接！", " 连接信息", JOptionPane.CLOSED_OPTION,
				JOptionPane.INFORMATION_MESSAGE);

	}// GEN-LAST:event_jButton6ActionPerformed
		// 下载文件按钮

	private void jButtonDownloadFileActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton9ActionPerformed
		if(FtpFunction.getConnectSocket() == null){
			JOptionPane.showConfirmDialog(null, "服务器尚未连接！", " 连接信息", JOptionPane.CLOSED_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		String remoteFile = jTextFieldRemoteFile.getText().toString();
		String localFile = jTextFieldLocalFile.getText().toString();
		if(remoteFile.equals("")){
			JOptionPane.showConfirmDialog(null, "请输入文件名！", " 下载信息", JOptionPane.CLOSED_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if(remoteFile == null || !jTextAreaFileList.getText().contains(remoteFile)){
			JOptionPane.showConfirmDialog(null, "该文件不存在！", " 下载信息", JOptionPane.CLOSED_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		try {
			FtpFunction.download(remoteFile, localFile);
			jTextFieldRemoteFile.setText("");
		} catch (IOException ex) {
			Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
		}

	}// GEN-LAST:event_jButton9ActionPerformed
		// 显示当前目录

	private void jButtonCurDirActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton7ActionPerformed
		try {
			String dir = FtpFunction.pwd();
			if (dir != null) {
				jTextFieldCurDir.setText(dir);
			} 
//			else {
//				jTextFieldCurDir.setText("当前目录为根目录");
//			}
		} catch (IOException ex) {
			Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
		}

	}// GEN-LAST:event_jButton7ActionPerformed

	// 更改远程工作目录
	private void jButtonChangeDirActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton10ActionPerformed
		try {
			if(FtpFunction.getConnectSocket() == null){
				JOptionPane.showConfirmDialog(null, "服务器尚未连接！", " 连接信息", JOptionPane.CLOSED_OPTION,
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			if(!FtpFunction.isLogin()){
				JOptionPane.showConfirmDialog(null, "尚未登录！", " 登录信息", JOptionPane.CLOSED_OPTION,
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			String dir = jTextFieldChangeDir.getText().toString();
			if(dir.equals("")){
				JOptionPane.showConfirmDialog(null, "请输入目录名！", " 目录信息", JOptionPane.CLOSED_OPTION,
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			boolean change = FtpFunction.cwd(dir);
			if(change){
				JOptionPane.showConfirmDialog(null, "改变目录成功！", " 目录信息", JOptionPane.CLOSED_OPTION,
						JOptionPane.INFORMATION_MESSAGE);
			}
			else{
				JOptionPane.showConfirmDialog(null, "该目录不存在，改变目录失败！", " 目录信息", JOptionPane.CLOSED_OPTION,
						JOptionPane.INFORMATION_MESSAGE);
			}
			jTextFieldChangeDir.setText("");
		} catch (IOException ex) {
			Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
		}

	}// GEN-LAST:event_jButton10ActionPerformed

	private void jButtonDelFileActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton8ActionPerformed
		try {
			if(FtpFunction.getConnectSocket() == null){
				JOptionPane.showConfirmDialog(null, "服务器尚未连接！", " 连接信息", JOptionPane.CLOSED_OPTION,
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			if(!FtpFunction.isLogin()){
				JOptionPane.showConfirmDialog(null, "尚未登录！", " 登录信息", JOptionPane.CLOSED_OPTION,
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			FtpFunction.rmdir(jTextFieldDelFileName.getText().toString());// GEN-LAST:event_jButton8ActionPerformed
			jTextFieldDelFileName.setText("");
		} catch (IOException ex) {
			Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	// 显示当前远程目录
	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {

			public void run() {
				new MainFrame().setVisible(true);
			}
		});
	}

	private javax.swing.JButton jButtonLogin;
	private javax.swing.JButton jButtonChangeDir;
	private javax.swing.JButton jButtonConnectServer;
	private javax.swing.JButton jButtonUpload;
	private javax.swing.JButton jButtonMKD;
	private javax.swing.JButton jButtonFileList;
	private javax.swing.JButton jButtonQuit;
	private javax.swing.JButton jButtonCurDir;
	private javax.swing.JButton jButtonDelFile;
	private javax.swing.JButton jButtonDownloadFile;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabelDelFile;
	private javax.swing.JLabel jLabelDownloadFilePath;
	private javax.swing.JLabel jLabelFileSavePath;
	private javax.swing.JLabel jLabelChangeDir;
	private javax.swing.JLabel jLabelUserName;
	private javax.swing.JLabel jLabelPassword;
	private javax.swing.JLabel jLabelFileUploadPath;
	private javax.swing.JLabel jLabelMKD;
	private javax.swing.JLabel jLabelServerIP;
	private javax.swing.JLabel jLabelPort;
	private javax.swing.JLabel jLabelRedHint;
	private javax.swing.JLabel jLabelCurDir;
	private javax.swing.JPanel jPanelLogin;
	private javax.swing.JPanel jPanelUpload;
	private javax.swing.JPanel jPanelDownload;
	private javax.swing.JPanel jPanelDir;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JTabbedPane jTabbedPane1;
	private javax.swing.JTextArea jTextAreaFileList;
	private javax.swing.JTextField jTextFieldUserName;
	private javax.swing.JTextField jTextFieldLocalFile;
	private javax.swing.JTextField jTextFieldChangeDir;
	private javax.swing.JTextField jTextFieldPassword;
	private javax.swing.JTextField jTextField3;
	private javax.swing.JTextField jTextFieldDirName;
	private javax.swing.JTextField jTextFieldServerIP;
	private javax.swing.JTextField jTextFieldPort;
	private javax.swing.JTextField jTextFieldCurDir;
	private javax.swing.JTextField jTextFieldDelFileName;
	private javax.swing.JTextField jTextFieldRemoteFile;
}
