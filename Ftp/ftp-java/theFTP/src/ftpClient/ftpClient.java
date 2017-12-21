/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ftpClient;

import LogicClient.FunctionOfFtp;
import demo.FTPClient1;
import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author zhuch
 */
public class ftpClient extends javax.swing.JFrame {

    /**
     * 构造方法
     */
    public ftpClient(FunctionOfFtp functionOfFtp) {
        this.functionOfFtp = functionOfFtp;
        initComponents();
    }

    /**
     * 方法功能：进行页面的初始化操作
     */
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        serverTextField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        portTextField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        userTextField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        PassField = new javax.swing.JTextField();
        linkButton = new javax.swing.JButton();
        LocalPanel = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        deleteLocalButton = new javax.swing.JButton();
        createLocalButton = new javax.swing.JButton();
        uploadLocalButton = new javax.swing.JButton();
        refreshLocalButton = new javax.swing.JButton();
        localDiskComboBox = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        localSelFilePathLabel = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        localDiskTable = new javax.swing.JTable();
        ServerPanel = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jToolBar2 = new javax.swing.JToolBar();
        delButton = new javax.swing.JButton();
        createButton = new javax.swing.JButton();
        downloadButton = new javax.swing.JButton();
        refreshButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        serverDiskTable = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jToolBar3 = new javax.swing.JToolBar();
        theQuenue = new javax.swing.JButton();
        uploadQue = new javax.swing.JButton();
        downloadQuenue = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        stopTheButton = new javax.swing.JButton();
        goOn = new javax.swing.JButton();
        deleteTheButton = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        currentProgressBar = new javax.swing.JProgressBar();

//        currentProgressBar = new MyProgressBar(0);
//        new Thread(currentProgressBar).start();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        }).start();

        jLabel8 = new javax.swing.JLabel();
        theFileDowning = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setText("主机地址");

        serverTextField.setText("127.0.0.1");
        serverTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                serverTextFieldActionPerformed(evt);
            }
        });

        jLabel1.setText("端口");

        portTextField.setText("21");
        portTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                portTextFieldActionPerformed(evt);
            }
        });

        jLabel3.setText("用户名");

        userTextField.setText("");
        userTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userTextFieldActionPerformed(evt);
            }
        });

        jLabel4.setText("密码");

        PassField.setText("");
        PassField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PassFieldActionPerformed(evt);
            }
        });

        linkButton.setText("连接");
        linkButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                linkButtonActionPerformed(evt);
            }
        });

        LocalPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jToolBar1.setRollover(true);

        deleteLocalButton.setText("删除");
        deleteLocalButton.setFocusable(false);
        deleteLocalButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        deleteLocalButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        deleteLocalButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteLocalButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(deleteLocalButton);

        createLocalButton.setText("新建文件夹");
        createLocalButton.setFocusable(false);
        createLocalButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        createLocalButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        createLocalButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createLocalButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(createLocalButton);

        uploadLocalButton.setText("上传");
        uploadLocalButton.setFocusable(false);
        uploadLocalButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        uploadLocalButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        uploadLocalButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uploadLocalButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(uploadLocalButton);

        refreshLocalButton.setText("刷新");
        refreshLocalButton.setFocusable(false);
        refreshLocalButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        refreshLocalButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        refreshLocalButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshLocalButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(refreshLocalButton);

        localDiskComboBox.setModel(new javax.swing.DefaultComboBoxModel(File.listRoots()));
        localDiskComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                localDiskComboBoxItemStateChanged(evt);
            }
        });
        jToolBar1.add(localDiskComboBox);

        jLabel5.setText("本地");

        localSelFilePathLabel.setText("c:\\");

        localDiskTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {
                },
                new String [] {
                        "文件名", "大小", "日期"
                }
        ) {
            Class[] types = new Class [] {
                    java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                    false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        localDiskTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                localDiskTableMouseClicked(e);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        jScrollPane3.setViewportView(localDiskTable);
        if (localDiskTable.getColumnModel().getColumnCount() > 0) {
            localDiskTable.getColumnModel().getColumn(0).setResizable(false);
            localDiskTable.getColumnModel().getColumn(1).setResizable(false);
            localDiskTable.getColumnModel().getColumn(2).setResizable(false);
        }

        javax.swing.GroupLayout LocalPanelLayout = new javax.swing.GroupLayout(LocalPanel);
        LocalPanel.setLayout(LocalPanelLayout);
        LocalPanelLayout.setHorizontalGroup(
                LocalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(LocalPanelLayout.createSequentialGroup()
                                .addGroup(LocalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(LocalPanelLayout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(localSelFilePathLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(LocalPanelLayout.createSequentialGroup()
                                                .addGroup(LocalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(LocalPanelLayout.createSequentialGroup()
                                                                .addGap(127, 127, 127)
                                                                .addComponent(jLabel5))
                                                        .addGroup(LocalPanelLayout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
                        .addGroup(LocalPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        LocalPanelLayout.setVerticalGroup(
                LocalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(LocalPanelLayout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(localSelFilePathLabel))
        );

        ServerPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel6.setText("远程");

        jToolBar2.setRollover(true);

        delButton.setText("删除");
        delButton.setFocusable(false);
        delButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        delButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        delButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delButtonActionPerformed(evt);
            }
        });
        jToolBar2.add(delButton);

        createButton.setText("新建文件夹");
        createButton.setFocusable(false);
        createButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        createButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        createButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createButtonActionPerformed(evt);
            }
        });
        jToolBar2.add(createButton);

        downloadButton.setText("下载");
        downloadButton.setFocusable(false);
        downloadButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        downloadButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
//        downloadButton.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                downloadButtonActionPerformed(evt);
//            }
//        });
        downloadButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                downloadButtonActionPerformed(e);
            }
        });
        jToolBar2.add(downloadButton);

        refreshButton.setText("刷新");
        refreshButton.setFocusable(false);
        refreshButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        refreshButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });
        jToolBar2.add(refreshButton);

        serverDiskTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {
                },
                new String [] {
                        "文件名", "大小", "日期"
                }
        ) {
            Class[] types = new Class [] {
                    java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                    false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        serverDiskTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                serverDiskTableMouseClicked(e);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        jScrollPane1.setViewportView(serverDiskTable);
        if (serverDiskTable.getColumnModel().getColumnCount() > 0) {
            serverDiskTable.getColumnModel().getColumn(0).setResizable(false);
            serverDiskTable.getColumnModel().getColumn(1).setResizable(false);
            serverDiskTable.getColumnModel().getColumn(2).setResizable(false);
        }

        javax.swing.GroupLayout ServerPanelLayout = new javax.swing.GroupLayout(ServerPanel);
        ServerPanel.setLayout(ServerPanelLayout);
        ServerPanelLayout.setHorizontalGroup(
                ServerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(ServerPanelLayout.createSequentialGroup()
                                .addGroup(ServerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(ServerPanelLayout.createSequentialGroup()
                                                .addGap(150, 150, 150)
                                                .addComponent(jLabel6)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addGroup(ServerPanelLayout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(jToolBar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addContainerGap())
                        .addGroup(ServerPanelLayout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(45, Short.MAX_VALUE))
        );
        ServerPanelLayout.setVerticalGroup(
                ServerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(ServerPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(20, Short.MAX_VALUE))
        );

        jToolBar3.setRollover(true);

        theQuenue.setText("队列");
        theQuenue.setFocusable(false);
        theQuenue.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        theQuenue.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        theQuenue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                theQuenueActionPerformed(evt);
            }
        });
        jToolBar3.add(theQuenue);

        uploadQue.setText("上传队列");
        uploadQue.setFocusable(false);
        uploadQue.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        uploadQue.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        uploadQue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uploadQueActionPerformed(evt);
            }
        });
        jToolBar3.add(uploadQue);

        downloadQuenue.setText("下载队列");
        downloadQuenue.setFocusable(false);
        downloadQuenue.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        downloadQuenue.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        downloadQuenue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downloadQuenueActionPerformed(evt);
            }
        });
        jToolBar3.add(downloadQuenue);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {
                        {null, null},
                        {null, null},
                        {null, null},
                        {null, null}
                },
                new String [] {
                        "文件名", "下载进度"
                }
        ) {
            boolean[] canEdit = new boolean [] {
                    false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
        }

        stopTheButton.setText("暂停");
        stopTheButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopTheButtonActionPerformed(evt);
            }
        });

        goOn.setText("继续");
//        goOn.setVisible(false);
        goOn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                goOnActionPerformed(evt);
            }
        });

        goOn.setEnabled(false);
        deleteTheButton.setText("断开");
        deleteTheButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteTheButtonActionPerformed(evt);
            }
        });

        jLabel7.setText("当前文件下载进度");
//        currentProgressBar .addChangeListener();

        jLabel8.setText("文件名称：");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jToolBar3, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 578, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                .addComponent(goOn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(stopTheButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                        .addComponent(deleteTheButton)))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(8, 8, 8)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel7)
                                                        .addComponent(jLabel8))
                                                .addGap(29, 29, 29)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(currentProgressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(theFileDowning, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(0, 0, Short.MAX_VALUE)))))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jToolBar3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(stopTheButton)
                                                .addGap(18, 18, 18)
                                                .addComponent(goOn)
                                                .addGap(18, 18, 18)
                                                .addComponent(deleteTheButton))
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel8)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(theFileDowning)
                                                .addGap(18, 18, 18)))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(currentProgressBar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(26, 26, 26))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addComponent(jLabel2)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(serverTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(jLabel1)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(portTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(jLabel3)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(userTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(jLabel4)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(PassField, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(linkButton))
                                                .addGroup(layout.createSequentialGroup()
                                                        .addComponent(LocalPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(32, 32, 32)
                                                        .addComponent(ServerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(serverTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel1)
                                        .addComponent(portTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel3)
                                        .addComponent(userTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel4)
                                        .addComponent(PassField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(linkButton))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(LocalPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(ServerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        //　进入页面初始化本地文件列表
        File file = new File("d:");
        localFileList(file);
        this.setIconImage(new ImageIcon("theIcon2.jpg").getImage());
        this.setTitle("yj下载器");
        pack();
    }

    private void serverTextFieldActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void portTextFieldActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void userTextFieldActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void PassFieldActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    /**
     * 点击连接服务器按钮所触发的事件
     * @param evt
     */
    private void linkButtonActionPerformed(java.awt.event.ActionEvent evt) {
//        if (this.linkButton.getText().equals("连接")) {
            // 客户端尝试和服务器端进行连接
            this.functionOfFtp.setRemoteHost(serverTextField.getText());
            this.functionOfFtp.setRemotePort(Integer.parseInt(portTextField.getText()));
            this.functionOfFtp.connect();
            // 如果返回连接成功的信息，显示连接成功对话框
            // 如果返回连接失败信息，则显示连接成功对话框

            this.functionOfFtp.setUser(userTextField.getText());
            this.functionOfFtp.setPW(PassField.getText());
            this.functionOfFtp.login();
            // TODO 设置相应的区域不可编辑
            this.serverTextField.setEditable(false);
            this.portTextField.setEditable(false);
            this.userTextField.setEditable(false);
            this.PassField.setEditable(false);
            // TODO 登录开始刷新文件
            theRefreshButtonOfTheServer();
//            this.linkButton.setText("断开");
//        }
//        if (this.linkButton.getText().equals("断开")) {
//            try {
//                this.functionOfFtp.close();
//            } catch (IOException E) {
//                E.printStackTrace();
//            }
//            this.linkButton.setText("连接");
//        }
        // 首先客户端和服务器端发送连接的请求，客户端增加是否连接成功的信息
//        JOptionPane.showMessageDialog(this.jScrollPane1,"连接成功");
//        JOptionPane.showMessageDialog(this.jScrollPane1,"连接失败");
    }

    /**
     * 方法功能：删除远程的一个文件夹操作
     * @param evt
     */
    private void delButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // 首先获得所选择的文件信息，用String来进行保存，如果选择，则进行下一步操作，如果没有选择，则提示错误信息
        String theFileName = this.thePathOfTheServer;
        if (!theFileName.equals("")) {
            int confirmInformation = JOptionPane.showConfirmDialog(this,"确认要删除远程的文件吗？");
            if (confirmInformation == JOptionPane.YES_OPTION) {
                // 调用客户端的服务程序，发送服务程序命令（删除远程文件夹）
                try {
                    this.functionOfFtp.delete(theFileName);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    theRefreshButtonOfTheServer();
                }
            } else {
                return;
            }
        } else {
            JOptionPane.showMessageDialog(this, "没有要删除的文件");
        }
    }

    /**
     * 方法功能：在远程创建一个文件夹目录
     * @param evt
     */
    private void createButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        // 使用输入对话框接收用户输入的文件夹名称
        String folderName = JOptionPane.showInputDialog("请输入文件夹名称：");
        if (folderName == null) {
            JOptionPane.showConfirmDialog(this, "文件夹不能为空");
            return;
        }
        try {
            this.functionOfFtp.mkdir(folderName);
            theRefreshButtonOfTheServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        File curFolder = null;
//        // 获取远程资源的行号
//        int selRow = this.serverDiskTable.getSelectedRow();
//        if (selRow < 0) {
//            // 创建当前文件夹对象
//            curFolder = new File(this.localSelFilePathLabel.getText());
//        } else {
//
//        }
//        // 创建当前文件夹下的新文件夹对象
//        File tempFile = new File(curFolder, folderName);
//        if (tempFile.exists()) {
//            // 如果存在着相同的文件夹或者文件
//            JOptionPane.showMessageDialog(jScrollPane1, folderName+"创建失败，该文件已经存在","创建文件夹",JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//        if (tempFile.mkdir()) {
//            JOptionPane.showMessageDialog(jScrollPane1, folderName + "文件夹，创建成功。",
//                    "创建文件夹", JOptionPane.INFORMATION_MESSAGE);
//        }
//        else {
//            JOptionPane.showMessageDialog(jScrollPane1, folderName + "文件夹无法被创建。",
//                    "创建文件夹", JOptionPane.ERROR_MESSAGE);
//        }
//        // TODO 刷新本地文件夹的操作
//        localFileList(getCurrrent());
    }

    /**
     * 点击下载的按钮触发响应
     * @param evt
     */
    private void downloadButtonActionPerformed(MouseEvent evt) {
        try {
//            this.ftpClient1.Get("D:\\ff", currentProgressBar);
//            this.functionOfFtp.download();
        } catch (Exception E) {
            E.printStackTrace();
        }
        int[] selRows = this.serverDiskTable.getSelectedRows();
        if (selRows.length < 1) {
            JOptionPane.showMessageDialog(this.jScrollPane1, "请选择要下载的文件");
            return;
        } else {
            // 如果是多文件下载，排队等待
            boolean ifOrNot = true;
            try {
                ifOrNot = this.functionOfFtp.download1(this.thePathOfTheServer = (String) this.serverDiskTable.getValueAt(selRows[0], 1), this.currentProgressBar , this.stopTheButton, this.goOn);
                this.jLabel8.setText("文件名称：" + (String) this.serverDiskTable.getValueAt(selRows[0], 0));
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.goOn.setEnabled(true);
            this.stopTheButton.setEnabled(false);

            for (int i = 1; i < selRows.length && ifOrNot; ) {
                try {
                    ifOrNot = false;
                    ifOrNot = this.functionOfFtp.download1(this.thePathOfTheServer = (String) this.serverDiskTable.getValueAt(selRows[i], 1), this.currentProgressBar , this.stopTheButton, this.goOn);
                    this.jLabel8.setText("文件名称：" + (String) this.serverDiskTable.getValueAt(selRows[i], 0));
                    if (ifOrNot) {
                        this.goOn.setEnabled(true);
                        this.stopTheButton.setEnabled(false);
                        i++;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 点击远程服务器刷新按钮做的反应
     * @param evt
     */
    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        // TODO 获得选中的表格项，如果没有选中，则默认为空
        // TODO 如果选中表格项，则获取表格的第一列，将文件的路径当作列表要给出的项进行操作
        theRefreshButtonOfTheServer();
    }

    /**
     * 方法功能：执行远程文件的刷新操作
     */
    private void theRefreshButtonOfTheServer() {
        try {
            ArrayList<String> allTheNameOfTheFile = this.functionOfFtp.list("");
            // 获得所有的远程文件列表
            serverFileList(allTheNameOfTheFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 点击刷新本地服务器所做的反应
     * @param evt
     */
    private void refreshLocalButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        // 刷新本地的文件夹
        localFileList(getCurrrent());
    }

    /**
     * 本地上传文件的动作处理器
     * @param evt
     */
    private void uploadLocalButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // 获取用户选择的多个文件或者文件夹
        int[] selRows = this.localDiskTable.getSelectedRows();
        if (selRows.length < 1) {
            JOptionPane.showMessageDialog(this.jScrollPane1, "请选择上传的文件或文件夹");
            return;
        } else {
            // 获取选择的表格数据
            DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
            // 首先清除下载表格的内容
            model.setRowCount(0);
            for (int i = 0; i < selRows.length; i++) {
                model.addRow(new Object[] {this.localDiskTable.getValueAt(selRows[i], 0)});
            }
            // TODO 这里要改成端点续传的功能，并且添加进度操作
            for (int i = 0; i < selRows.length; i++) {
//                System.out.println((String) this.localDiskTable.getValueAt(selRows[i], 0));
                System.out.println(this.localDiskTable.getValueAt(selRows[i], 0).getClass());
                File theUploadFile = (File) this.localDiskTable.getValueAt(selRows[i], 0);
                try {
//                    this.functionOfFtp.list(localSelFilePathLabel.getText());
//                    this.functionOfFtp.upload(getCurrrent());
                    this.functionOfFtp.upload(theUploadFile);
                    localFileList(getCurrrent());
                    theRefreshButtonOfTheServer();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 方法功能：获取要上传的文件对象
     * @return
     */
    private File getCurrrent() {
        // 使用标签路径创建当前文件对象
        File file = new File(localSelFilePathLabel.getText());
        if (file != null) {
            return file;
        }
        return file;
    }

    /**
     * 在本地创建一个文件夹
     * @param evt
     */
    private void createLocalButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        // 使用输入对话框接收用户输入的文件夹名称
        String folderName = JOptionPane.showInputDialog("请输入文件夹名称：");
        if (folderName == null) {
            return;
        }
        File curFolder = null;
        // 获取本地资源的行号
        int selRow = this.localDiskTable.getSelectedRow();
        if (selRow < 0) {
            // 创建当前文件夹对象
            curFolder = new File(this.localSelFilePathLabel.getText());
        } else {

        }
        // 创建当前文件夹下的新文件夹对象
        File tempFile = new File(curFolder, folderName);
        if (tempFile.exists()) {
            // 如果存在着相同的文件夹或者文件
            JOptionPane.showMessageDialog(jScrollPane1, folderName+"创建失败，该文件已经存在","创建文件夹",JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (tempFile.mkdir()) {
            JOptionPane.showMessageDialog(jScrollPane1, folderName + "文件夹，创建成功。",
                    "创建文件夹", JOptionPane.INFORMATION_MESSAGE);
        }
        else {
            JOptionPane.showMessageDialog(jScrollPane1, folderName + "文件夹无法被创建。",
                    "创建文件夹", JOptionPane.ERROR_MESSAGE);
        }
        // TODO 刷新本地文件夹的操作
        localFileList(getCurrrent());
    }

//    private void deleteLocalButtonActionPerformed(java.awt.event.ActionEvent evt) {
//        // TODO add your handling code here:
//        // 获取表格选择的所有行
//        final int[] selRows = this.localDiskTable.getSelectedRows();
//        if (selRows.length < 1) // 如果没有选择表格内容
//            return; // 结束该方法
//        // TODO 提醒是否要进行删除窗口
//        int confirmDialog = JOptionPane.showConfirmDialog(this.jScrollPane1, "确认删除？");
//        // TODO 用户确认要进行删除操作
//        if (confirmDialog == JOptionPane.YES_OPTION) {
//            // 这里是删除本地的文件
//
//        }
//    }

    /**
     * 在本地删除一个文件夹
     * @param evt
     */
    public void deleteLocalButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // 获取表格选择的所有行
        final int[] selRows = this.localDiskTable.getSelectedRows();
        if (selRows.length < 1) {
            // 如果没有选择表格内容
            JOptionPane.showMessageDialog(this.jScrollPane2, "没有选中的文件");
            return; // 结束该方法
        }
        int confirmDialog = JOptionPane.showConfirmDialog(this.jScrollPane2,
                "确定要执行删除吗？"); // 用户确认是否删除
        if (confirmDialog == JOptionPane.YES_OPTION) { // 如果用于同意删除
            Runnable runnable = new Runnable() { // 创建线程
                /**
                 * 删除文件的递归方法
                 *
                 * @param file
                 *            要删除的文件对象
                 */
                private void delFile(File file) {
                    try {
                        if (file.isFile()) { // 如果删除的是文件
                            boolean delete = file.delete(); // 调用删该文件的方法
                            if (!delete) {
                                JOptionPane.showMessageDialog(jScrollPane2, file
                                                .getAbsoluteFile()
                                                + "文件无法删除。", "删除文件",
                                        JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                        } else if (file.isDirectory()) { // 如果删除的是文件夹
                            File[] listFiles = file.listFiles();// 获取该文件夹的文件列表
                            if (listFiles.length > 0) {
                                for (File subFile : listFiles) {
                                    delFile(subFile); // 调用递归方法删除该列表的所有文件或文件夹
                                }
                            }
                            boolean delete = file.delete();// 最后删除该文件夹
                            if (!delete) { // 如果成功删除
                                JOptionPane.showMessageDialog(jScrollPane2, file
                                                .getAbsoluteFile()
                                                + "文件夹无法删除。", "删除文件",
                                        JOptionPane.ERROR_MESSAGE);
                                return; // 返回方法的调用处
                            }
                        }
                    } catch (Exception ex) {

                    }
                }

                /**
                 * 线程的主体方法
                 *
                 * @see java.lang.Runnable#run()
                 */
                public void run() {
                    File parent = null;
                    // 遍历表格的选择内容
                    for (int i = 0; i < selRows.length; i++) {
                        // 获取每个选择行的第一列单元内容
                        Object value = localDiskTable.getValueAt(selRows[i], 0);
                        // 如果该内容不是DiskFile类的实例对象
                        if (!(value instanceof File))
                            continue; // 结束本次循环
                        File file = (File)value;
                        if (parent == null)
                            parent = file.getParentFile(); // 获取选择文件的上级文件夹
                        if (file != null) {
                            delFile(file); // 调用递归方法删除选择内容
                        }
                    }
                    // 调用refreshFolder方法刷新当前文件夹
                    localFileList(getCurrrent());
                    JOptionPane.showMessageDialog(jScrollPane2, "删除成功。");
                }
            };
            new Thread(runnable).start(); // 创建并启动这个线程
        }
    }

    /**
     * 磁盘文件改变的操作
     * @param evt
     */
    private void localDiskComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {
        // 下拉列表内容点击操作
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            // 获取选择的下拉列表框
            Object item = evt.getItem();
            if(item instanceof File) {
                File selDisk = (File)item;
                // todo 调用显示文件列表的方法，显示该指定文件的磁盘列表
                localFileList(selDisk);
            }
        }

    }

    /**
     * 点击本地表格获取本地表格的某一行
     * @param event
     */
    private void localDiskTableMouseClicked(MouseEvent event) {
        // 获取已经选择的行号,实现多选
        int[] selectRows = this.localDiskTable.getSelectedRows();
        if (selectRows.length < 0) {
            return;
        }
        for (int i = 0; i < selectRows.length; i++) {
            //        // 获取每一行的第一列信息
            Object value = this.localDiskTable.getValueAt(selectRows[i],0);
            if (value instanceof File) {
                // 设置本地文件夹的路径
    //            this.localSelFilePathLabel.getText() = value;
                this.localSelFilePathLabel.setText(((File) value).getAbsolutePath());
                // 如果是文件夹并且点击鼠标两次
                if (((File) value).isDirectory() && event.getClickCount() >= 2) {
                    localFileList((File) value);
                }
            } else {
                if (event.getClickCount() >= 2 && value.equals("..")) {
                    localFileList(getCurrrent().getParentFile());
                }
            }
        }
//        int selectRow = this.localDiskTable.getSelectedRow();
//        if(selectRow < 0) {
//            return;
//        }
//        // 获取每一行的第一列信息
//        Object value = this.localDiskTable.getValueAt(selectRow,0);
//        if (value instanceof File) {
//            // 设置本地文件夹的路径
////            this.localSelFilePathLabel.getText() = value;
//            this.localSelFilePathLabel.setText(((File) value).getAbsolutePath());
//            // 如果是文件夹并且点击鼠标两次
//            if (((File) value).isDirectory() && event.getClickCount() >= 2) {
//                localFileList((File) value);
//            }
//        } else {
//            if (event.getClickCount() >= 2 && value.equals("..")) {
//                localFileList(getCurrrent().getParentFile());
//            }
//        }
    }

    /**
     * 方法功能：读取本地文件到表格的方法
     * @param selDisk
     */
    private void localFileList(File selDisk) {
        if (selDisk == null || selDisk.isFile()) {
            return;
        }
        localSelFilePathLabel.setText(selDisk.getAbsolutePath());
        // 读取磁盘文件列表
        File[] listFiles = selDisk.listFiles();
        // 获取表格的数据模型
        DefaultTableModel model = (DefaultTableModel)localDiskTable.getModel();
        // 首先清除表格的内容
        model.setRowCount(0);
        model.addRow(new Object[]{".", "文件夹", ""});
        model.addRow(new Object[]{"..", "文件夹", ""});
        if (listFiles == null) {
            // 如果没有读出子文件夹
            JOptionPane.showMessageDialog(this, "该磁盘无法访问");
            return;
        }
        // 遍历本地文件夹的内容，添加到表格之中
        for (File file:listFiles) {
            // 创建文件对象
            File diskFile = new File(file.toURI());
            String length = file.length() + "B";
            // 获取文件的修改信息
//            file.lastModified()用与1970-1-1-8：00之间的毫秒数进行表示
            String theLastModify = new Date(file.lastModified()).toLocaleString();
            // 判断文件是否可读
            if (!file.canRead()) {
                length = "未知";
                theLastModify = "未知";
            }
            if (file.isDirectory()) {
                length = "文件夹";
            }
            model.addRow(new Object[]{diskFile, length, theLastModify});
        }
    }

    /**
     * 方法功能：查看所有的下载队列
     * @param theFileName
     */
    private void getDownloadQuenue(String theFileName) {
        //  同样的获取表格的数据模型
        DefaultTableModel model = (DefaultTableModel) downloadQuenue.getModel();
        // 清空表格中存在的所有行
        model.setRowCount(0);
        // 设置文件名
//        model.addRow(new Object[] {theFileName, ""});
        model.addRow(new Object[] {"hahaha", "gehee"});
    }

    /**
     * 方法功能：点击远程服务器窗格中的某一行来获取文件的路径信息
     * @param event
     */
    private void serverDiskTableMouseClicked(MouseEvent event) {
        // 获取已经在服务器窗格中选择的行号
        int selectRow = this.serverDiskTable.getSelectedRow();
        if (selectRow < 0) {
//            JOptionPane.showMessageDialog(this, "");
            return ;
        }
        // 获取每一行的第一列的信息
        Object value = this.serverDiskTable.getValueAt(selectRow, 1);
        if (value instanceof String) {
//            this.thePathOfTheServer = value;
            // 设置远程上要下载的路径信息
            this.thePathOfTheServer = (String)value;
            // 设置远程上要下载的文件名信息
            this.theFileName = (String) this.serverDiskTable.getValueAt(selectRow, 0);
            // 如果选中的是文件并且双击两次，则刷新当前的目录
            if ((boolean)this.serverDiskTable.getValueAt(selectRow, 2) && event.getClickCount() >= 2) {
                theRefreshButtonOfTheServer();
            }
            // 如果选中的是文件并且双击两次，则直接进行下载
            if (!((boolean)this.serverDiskTable.getValueAt(selectRow, 2)) && event.getClickCount() >= 2) {
                try {
                    this.functionOfFtp.download1(this.thePathOfTheServer = (String) this.serverDiskTable.getValueAt(selectRow, 1), this.currentProgressBar , this.stopTheButton, this.goOn);
//                    System.out.println(this.theFileName);
                    getDownloadQuenue(this.theFileName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 方法功能：读取服务器端文件到窗格
     * @param allTheItems
     */
    private void serverFileList(ArrayList<String> allTheItems) {
        // 首先获取表格的数据模型
        DefaultTableModel mode = (DefaultTableModel) serverDiskTable.getModel();
        // 清除表格所有行的内容
        mode.setRowCount(0);
        mode.addRow(new Object[] {".", "文件夹"});
        mode.addRow(new Object[] {"..", "文件夹"});
        if (allTheItems == null) {
           // 提示信息，该文件夹下面没有项目
            JOptionPane.showMessageDialog(this, "没有子文件");
            return;
        } else {
            // 遍历字符串容器，将文件的名称进行显示
            for (int i = 0; i < allTheItems.size(); ) {
                String fileName = allTheItems.get(i);
                i++;
                String thePath = allTheItems.get(i);
                i++;
                boolean isOrNotDirectory;
                if (allTheItems.get(i).equals("true")) {
                    isOrNotDirectory = true;
                } else {
                    isOrNotDirectory = false;
                }
                i++;
                mode.addRow(new Object[] {fileName, thePath, isOrNotDirectory});
            }
        }
    }

    /**
     * 方法功能：点击队列之后所作出的反应，刷新当前正在下载或者上传文件的信息
     * @param evt
     */
    private void theQuenueActionPerformed(java.awt.event.ActionEvent evt) {
        // 获取文件的下载队列
        getDownloadQuenue(this.theFileName);
    }

    private void uploadQueActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void downloadQuenueActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    /**
     * 方法功能：删除按钮所对应的操作
     * @param evt
     */
    private void stopTheButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        // TODO 点击暂停的按钮，触发响应
        // TODO 接着启动停止上传文件的操作，并且将按钮改为继续
        // 首先对界面进行设置
        this.stopTheButton.setEnabled(false);
        this.goOn.setEnabled(true);
        // 终止文件传输
//        this.ftpClient1.stop();
    }

    /**
     * 方法功能：继续按钮所对应的操作
     * @param evt
     */
    private void goOnActionPerformed(java.awt.event.ActionEvent evt) {
            // 在逻辑上实现
//            System.out.println(currentProgressBar.getValue() + "进度条的值的变化");
//            this.stopTheButton.setEnabled(true);
//            this.goOn.setEnabled(false);
//            try {
//                Thread.sleep(1000);
//                this.ftpClient1.Get("D:\\ff", currentProgressBar);
//            } catch (Exception E) {
//                E.printStackTrace();
//            } finally {
//
//            }
    }

    /**
     * 删除正在下载的按钮所对应的操作
     * @param evt
     */
    private void deleteTheButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        // TODO 提醒是否要进行删除窗口
        int confirmDialog = JOptionPane.showConfirmDialog(this.jScrollPane1, "确认退出登录？");
        // TODO 用户确认要进行删除操作
        if (confirmDialog == JOptionPane.YES_OPTION) {
            // 调用ftp客户端进行相应的连接操作
            try {
                this.functionOfFtp.close();
            } catch (IOException E) {
                E.printStackTrace();
            }
        }
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws Exception {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ftpClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ftpClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ftpClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ftpClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new ftpClient().setVisible(true);
//            }
//        });
//        FTPClient1 client = new FTPClient1();
        /**
         * 显示客户端页面
         */
//        ftpClient theFrameClient = new ftpClient(new FTPClient1());
//        theFrameClient.setVisible(true);
//        try {
////            theFrameClient.ftpClient1.Get("D:\\ff", theFrameClient.currentProgressBar);
////            theFrameClinet.currentProgressBar.setValue(100);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        // 显示客户端页面
        ftpClient theFrameClient = new ftpClient(new FunctionOfFtp());
        theFrameClient.setVisible(true);
    }

    /**
     * 方法功能：得到目前的进度条
     * @return
     */
//    public JProgressBar getCurrentProgressBar() {
//        return this.currentProgressBar;
//    }

    private String theFileName;
    private String thePathOfTheServer;
    private String theServerName;
    public FTPClient1 ftpClient1;
    private FunctionOfFtp functionOfFtp;
    private javax.swing.JPanel LocalPanel;
    private javax.swing.JTextField PassField;
    private javax.swing.JPanel ServerPanel;
    private javax.swing.JButton createButton;
    private javax.swing.JButton createLocalButton;
    private javax.swing.JProgressBar currentProgressBar;

//    private MyProgressBar currentProgressBar;

    private javax.swing.JButton delButton;
    private javax.swing.JButton deleteLocalButton;
    private javax.swing.JButton deleteTheButton;
    private javax.swing.JButton downloadButton;
    private javax.swing.JButton downloadQuenue;
    private javax.swing.JButton goOn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JToolBar jToolBar3;
    private javax.swing.JButton linkButton;
    private javax.swing.JComboBox<String> localDiskComboBox;
    private javax.swing.JTable localDiskTable;
    private javax.swing.JLabel localSelFilePathLabel;
    private javax.swing.JTextField portTextField;
    private javax.swing.JButton refreshButton;
    private javax.swing.JButton refreshLocalButton;
    private javax.swing.JTable serverDiskTable;
    private javax.swing.JTextField serverTextField;
    private javax.swing.JButton stopTheButton;
    private javax.swing.JLabel theFileDowning;
    private javax.swing.JButton theQuenue;
    private javax.swing.JButton uploadLocalButton;
    private javax.swing.JButton uploadQue;
    private javax.swing.JTextField userTextField;
    // End of variables declaration
//    private demo.FTPClient;
}
