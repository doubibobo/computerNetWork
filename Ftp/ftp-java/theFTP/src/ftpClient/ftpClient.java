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
     * ���췽��
     */
    public ftpClient(FunctionOfFtp functionOfFtp) {
        this.functionOfFtp = functionOfFtp;
        initComponents();
    }

    /**
     * �������ܣ�����ҳ��ĳ�ʼ������
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

        jLabel2.setText("������ַ");

        serverTextField.setText("127.0.0.1");
        serverTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                serverTextFieldActionPerformed(evt);
            }
        });

        jLabel1.setText("�˿�");

        portTextField.setText("21");
        portTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                portTextFieldActionPerformed(evt);
            }
        });

        jLabel3.setText("�û���");

        userTextField.setText("");
        userTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userTextFieldActionPerformed(evt);
            }
        });

        jLabel4.setText("����");

        PassField.setText("");
        PassField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PassFieldActionPerformed(evt);
            }
        });

        linkButton.setText("����");
        linkButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                linkButtonActionPerformed(evt);
            }
        });

        LocalPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jToolBar1.setRollover(true);

        deleteLocalButton.setText("ɾ��");
        deleteLocalButton.setFocusable(false);
        deleteLocalButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        deleteLocalButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        deleteLocalButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteLocalButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(deleteLocalButton);

        createLocalButton.setText("�½��ļ���");
        createLocalButton.setFocusable(false);
        createLocalButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        createLocalButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        createLocalButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createLocalButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(createLocalButton);

        uploadLocalButton.setText("�ϴ�");
        uploadLocalButton.setFocusable(false);
        uploadLocalButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        uploadLocalButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        uploadLocalButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uploadLocalButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(uploadLocalButton);

        refreshLocalButton.setText("ˢ��");
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

        jLabel5.setText("����");

        localSelFilePathLabel.setText("c:\\");

        localDiskTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {
                },
                new String [] {
                        "�ļ���", "��С", "����"
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

        jLabel6.setText("Զ��");

        jToolBar2.setRollover(true);

        delButton.setText("ɾ��");
        delButton.setFocusable(false);
        delButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        delButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        delButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delButtonActionPerformed(evt);
            }
        });
        jToolBar2.add(delButton);

        createButton.setText("�½��ļ���");
        createButton.setFocusable(false);
        createButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        createButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        createButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createButtonActionPerformed(evt);
            }
        });
        jToolBar2.add(createButton);

        downloadButton.setText("����");
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

        refreshButton.setText("ˢ��");
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
                        "�ļ���", "��С", "����"
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

        theQuenue.setText("����");
        theQuenue.setFocusable(false);
        theQuenue.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        theQuenue.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        theQuenue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                theQuenueActionPerformed(evt);
            }
        });
        jToolBar3.add(theQuenue);

        uploadQue.setText("�ϴ�����");
        uploadQue.setFocusable(false);
        uploadQue.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        uploadQue.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        uploadQue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uploadQueActionPerformed(evt);
            }
        });
        jToolBar3.add(uploadQue);

        downloadQuenue.setText("���ض���");
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
                        "�ļ���", "���ؽ���"
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

        stopTheButton.setText("��ͣ");
        stopTheButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopTheButtonActionPerformed(evt);
            }
        });

        goOn.setText("����");
//        goOn.setVisible(false);
        goOn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                goOnActionPerformed(evt);
            }
        });

        goOn.setEnabled(false);
        deleteTheButton.setText("�Ͽ�");
        deleteTheButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteTheButtonActionPerformed(evt);
            }
        });

        jLabel7.setText("��ǰ�ļ����ؽ���");
//        currentProgressBar .addChangeListener();

        jLabel8.setText("�ļ����ƣ�");

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

        //������ҳ���ʼ�������ļ��б�
        File file = new File("d:");
        localFileList(file);
        this.setIconImage(new ImageIcon("theIcon2.jpg").getImage());
        this.setTitle("yj������");
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
     * ������ӷ�������ť���������¼�
     * @param evt
     */
    private void linkButtonActionPerformed(java.awt.event.ActionEvent evt) {
//        if (this.linkButton.getText().equals("����")) {
            // �ͻ��˳��Ժͷ������˽�������
            this.functionOfFtp.setRemoteHost(serverTextField.getText());
            this.functionOfFtp.setRemotePort(Integer.parseInt(portTextField.getText()));
            this.functionOfFtp.connect();
            // ����������ӳɹ�����Ϣ����ʾ���ӳɹ��Ի���
            // �����������ʧ����Ϣ������ʾ���ӳɹ��Ի���

            this.functionOfFtp.setUser(userTextField.getText());
            this.functionOfFtp.setPW(PassField.getText());
            this.functionOfFtp.login();
            // TODO ������Ӧ�����򲻿ɱ༭
            this.serverTextField.setEditable(false);
            this.portTextField.setEditable(false);
            this.userTextField.setEditable(false);
            this.PassField.setEditable(false);
            // TODO ��¼��ʼˢ���ļ�
            theRefreshButtonOfTheServer();
//            this.linkButton.setText("�Ͽ�");
//        }
//        if (this.linkButton.getText().equals("�Ͽ�")) {
//            try {
//                this.functionOfFtp.close();
//            } catch (IOException E) {
//                E.printStackTrace();
//            }
//            this.linkButton.setText("����");
//        }
        // ���ȿͻ��˺ͷ������˷������ӵ����󣬿ͻ��������Ƿ����ӳɹ�����Ϣ
//        JOptionPane.showMessageDialog(this.jScrollPane1,"���ӳɹ�");
//        JOptionPane.showMessageDialog(this.jScrollPane1,"����ʧ��");
    }

    /**
     * �������ܣ�ɾ��Զ�̵�һ���ļ��в���
     * @param evt
     */
    private void delButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // ���Ȼ����ѡ����ļ���Ϣ����String�����б��棬���ѡ���������һ�����������û��ѡ������ʾ������Ϣ
        String theFileName = this.thePathOfTheServer;
        if (!theFileName.equals("")) {
            int confirmInformation = JOptionPane.showConfirmDialog(this,"ȷ��Ҫɾ��Զ�̵��ļ���");
            if (confirmInformation == JOptionPane.YES_OPTION) {
                // ���ÿͻ��˵ķ�����򣬷��ͷ���������ɾ��Զ���ļ��У�
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
            JOptionPane.showMessageDialog(this, "û��Ҫɾ�����ļ�");
        }
    }

    /**
     * �������ܣ���Զ�̴���һ���ļ���Ŀ¼
     * @param evt
     */
    private void createButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        // ʹ������Ի�������û�������ļ�������
        String folderName = JOptionPane.showInputDialog("�������ļ������ƣ�");
        if (folderName == null) {
            JOptionPane.showConfirmDialog(this, "�ļ��в���Ϊ��");
            return;
        }
        try {
            this.functionOfFtp.mkdir(folderName);
            theRefreshButtonOfTheServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        File curFolder = null;
//        // ��ȡԶ����Դ���к�
//        int selRow = this.serverDiskTable.getSelectedRow();
//        if (selRow < 0) {
//            // ������ǰ�ļ��ж���
//            curFolder = new File(this.localSelFilePathLabel.getText());
//        } else {
//
//        }
//        // ������ǰ�ļ����µ����ļ��ж���
//        File tempFile = new File(curFolder, folderName);
//        if (tempFile.exists()) {
//            // �����������ͬ���ļ��л����ļ�
//            JOptionPane.showMessageDialog(jScrollPane1, folderName+"����ʧ�ܣ����ļ��Ѿ�����","�����ļ���",JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//        if (tempFile.mkdir()) {
//            JOptionPane.showMessageDialog(jScrollPane1, folderName + "�ļ��У������ɹ���",
//                    "�����ļ���", JOptionPane.INFORMATION_MESSAGE);
//        }
//        else {
//            JOptionPane.showMessageDialog(jScrollPane1, folderName + "�ļ����޷���������",
//                    "�����ļ���", JOptionPane.ERROR_MESSAGE);
//        }
//        // TODO ˢ�±����ļ��еĲ���
//        localFileList(getCurrrent());
    }

    /**
     * ������صİ�ť������Ӧ
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
            JOptionPane.showMessageDialog(this.jScrollPane1, "��ѡ��Ҫ���ص��ļ�");
            return;
        } else {
            // ����Ƕ��ļ����أ��Ŷӵȴ�
            boolean ifOrNot = true;
            try {
                ifOrNot = this.functionOfFtp.download1(this.thePathOfTheServer = (String) this.serverDiskTable.getValueAt(selRows[0], 1), this.currentProgressBar , this.stopTheButton, this.goOn);
                this.jLabel8.setText("�ļ����ƣ�" + (String) this.serverDiskTable.getValueAt(selRows[0], 0));
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.goOn.setEnabled(true);
            this.stopTheButton.setEnabled(false);

            for (int i = 1; i < selRows.length && ifOrNot; ) {
                try {
                    ifOrNot = false;
                    ifOrNot = this.functionOfFtp.download1(this.thePathOfTheServer = (String) this.serverDiskTable.getValueAt(selRows[i], 1), this.currentProgressBar , this.stopTheButton, this.goOn);
                    this.jLabel8.setText("�ļ����ƣ�" + (String) this.serverDiskTable.getValueAt(selRows[i], 0));
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
     * ���Զ�̷�����ˢ�°�ť���ķ�Ӧ
     * @param evt
     */
    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        // TODO ���ѡ�еı������û��ѡ�У���Ĭ��Ϊ��
        // TODO ���ѡ�б������ȡ���ĵ�һ�У����ļ���·�������б�Ҫ����������в���
        theRefreshButtonOfTheServer();
    }

    /**
     * �������ܣ�ִ��Զ���ļ���ˢ�²���
     */
    private void theRefreshButtonOfTheServer() {
        try {
            ArrayList<String> allTheNameOfTheFile = this.functionOfFtp.list("");
            // ������е�Զ���ļ��б�
            serverFileList(allTheNameOfTheFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ���ˢ�±��ط����������ķ�Ӧ
     * @param evt
     */
    private void refreshLocalButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        // ˢ�±��ص��ļ���
        localFileList(getCurrrent());
    }

    /**
     * �����ϴ��ļ��Ķ���������
     * @param evt
     */
    private void uploadLocalButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // ��ȡ�û�ѡ��Ķ���ļ������ļ���
        int[] selRows = this.localDiskTable.getSelectedRows();
        if (selRows.length < 1) {
            JOptionPane.showMessageDialog(this.jScrollPane1, "��ѡ���ϴ����ļ����ļ���");
            return;
        } else {
            // ��ȡѡ��ı������
            DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
            // ����������ر�������
            model.setRowCount(0);
            for (int i = 0; i < selRows.length; i++) {
                model.addRow(new Object[] {this.localDiskTable.getValueAt(selRows[i], 0)});
            }
            // TODO ����Ҫ�ĳɶ˵������Ĺ��ܣ�������ӽ��Ȳ���
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
     * �������ܣ���ȡҪ�ϴ����ļ�����
     * @return
     */
    private File getCurrrent() {
        // ʹ�ñ�ǩ·��������ǰ�ļ�����
        File file = new File(localSelFilePathLabel.getText());
        if (file != null) {
            return file;
        }
        return file;
    }

    /**
     * �ڱ��ش���һ���ļ���
     * @param evt
     */
    private void createLocalButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        // ʹ������Ի�������û�������ļ�������
        String folderName = JOptionPane.showInputDialog("�������ļ������ƣ�");
        if (folderName == null) {
            return;
        }
        File curFolder = null;
        // ��ȡ������Դ���к�
        int selRow = this.localDiskTable.getSelectedRow();
        if (selRow < 0) {
            // ������ǰ�ļ��ж���
            curFolder = new File(this.localSelFilePathLabel.getText());
        } else {

        }
        // ������ǰ�ļ����µ����ļ��ж���
        File tempFile = new File(curFolder, folderName);
        if (tempFile.exists()) {
            // �����������ͬ���ļ��л����ļ�
            JOptionPane.showMessageDialog(jScrollPane1, folderName+"����ʧ�ܣ����ļ��Ѿ�����","�����ļ���",JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (tempFile.mkdir()) {
            JOptionPane.showMessageDialog(jScrollPane1, folderName + "�ļ��У������ɹ���",
                    "�����ļ���", JOptionPane.INFORMATION_MESSAGE);
        }
        else {
            JOptionPane.showMessageDialog(jScrollPane1, folderName + "�ļ����޷���������",
                    "�����ļ���", JOptionPane.ERROR_MESSAGE);
        }
        // TODO ˢ�±����ļ��еĲ���
        localFileList(getCurrrent());
    }

//    private void deleteLocalButtonActionPerformed(java.awt.event.ActionEvent evt) {
//        // TODO add your handling code here:
//        // ��ȡ���ѡ���������
//        final int[] selRows = this.localDiskTable.getSelectedRows();
//        if (selRows.length < 1) // ���û��ѡ��������
//            return; // �����÷���
//        // TODO �����Ƿ�Ҫ����ɾ������
//        int confirmDialog = JOptionPane.showConfirmDialog(this.jScrollPane1, "ȷ��ɾ����");
//        // TODO �û�ȷ��Ҫ����ɾ������
//        if (confirmDialog == JOptionPane.YES_OPTION) {
//            // ������ɾ�����ص��ļ�
//
//        }
//    }

    /**
     * �ڱ���ɾ��һ���ļ���
     * @param evt
     */
    public void deleteLocalButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // ��ȡ���ѡ���������
        final int[] selRows = this.localDiskTable.getSelectedRows();
        if (selRows.length < 1) {
            // ���û��ѡ��������
            JOptionPane.showMessageDialog(this.jScrollPane2, "û��ѡ�е��ļ�");
            return; // �����÷���
        }
        int confirmDialog = JOptionPane.showConfirmDialog(this.jScrollPane2,
                "ȷ��Ҫִ��ɾ����"); // �û�ȷ���Ƿ�ɾ��
        if (confirmDialog == JOptionPane.YES_OPTION) { // �������ͬ��ɾ��
            Runnable runnable = new Runnable() { // �����߳�
                /**
                 * ɾ���ļ��ĵݹ鷽��
                 *
                 * @param file
                 *            Ҫɾ�����ļ�����
                 */
                private void delFile(File file) {
                    try {
                        if (file.isFile()) { // ���ɾ�������ļ�
                            boolean delete = file.delete(); // ����ɾ���ļ��ķ���
                            if (!delete) {
                                JOptionPane.showMessageDialog(jScrollPane2, file
                                                .getAbsoluteFile()
                                                + "�ļ��޷�ɾ����", "ɾ���ļ�",
                                        JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                        } else if (file.isDirectory()) { // ���ɾ�������ļ���
                            File[] listFiles = file.listFiles();// ��ȡ���ļ��е��ļ��б�
                            if (listFiles.length > 0) {
                                for (File subFile : listFiles) {
                                    delFile(subFile); // ���õݹ鷽��ɾ�����б�������ļ����ļ���
                                }
                            }
                            boolean delete = file.delete();// ���ɾ�����ļ���
                            if (!delete) { // ����ɹ�ɾ��
                                JOptionPane.showMessageDialog(jScrollPane2, file
                                                .getAbsoluteFile()
                                                + "�ļ����޷�ɾ����", "ɾ���ļ�",
                                        JOptionPane.ERROR_MESSAGE);
                                return; // ���ط����ĵ��ô�
                            }
                        }
                    } catch (Exception ex) {

                    }
                }

                /**
                 * �̵߳����巽��
                 *
                 * @see java.lang.Runnable#run()
                 */
                public void run() {
                    File parent = null;
                    // ��������ѡ������
                    for (int i = 0; i < selRows.length; i++) {
                        // ��ȡÿ��ѡ���еĵ�һ�е�Ԫ����
                        Object value = localDiskTable.getValueAt(selRows[i], 0);
                        // ��������ݲ���DiskFile���ʵ������
                        if (!(value instanceof File))
                            continue; // ��������ѭ��
                        File file = (File)value;
                        if (parent == null)
                            parent = file.getParentFile(); // ��ȡѡ���ļ����ϼ��ļ���
                        if (file != null) {
                            delFile(file); // ���õݹ鷽��ɾ��ѡ������
                        }
                    }
                    // ����refreshFolder����ˢ�µ�ǰ�ļ���
                    localFileList(getCurrrent());
                    JOptionPane.showMessageDialog(jScrollPane2, "ɾ���ɹ���");
                }
            };
            new Thread(runnable).start(); // ��������������߳�
        }
    }

    /**
     * �����ļ��ı�Ĳ���
     * @param evt
     */
    private void localDiskComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {
        // �����б����ݵ������
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            // ��ȡѡ��������б��
            Object item = evt.getItem();
            if(item instanceof File) {
                File selDisk = (File)item;
                // todo ������ʾ�ļ��б�ķ�������ʾ��ָ���ļ��Ĵ����б�
                localFileList(selDisk);
            }
        }

    }

    /**
     * ������ر���ȡ���ر���ĳһ��
     * @param event
     */
    private void localDiskTableMouseClicked(MouseEvent event) {
        // ��ȡ�Ѿ�ѡ����к�,ʵ�ֶ�ѡ
        int[] selectRows = this.localDiskTable.getSelectedRows();
        if (selectRows.length < 0) {
            return;
        }
        for (int i = 0; i < selectRows.length; i++) {
            //        // ��ȡÿһ�еĵ�һ����Ϣ
            Object value = this.localDiskTable.getValueAt(selectRows[i],0);
            if (value instanceof File) {
                // ���ñ����ļ��е�·��
    //            this.localSelFilePathLabel.getText() = value;
                this.localSelFilePathLabel.setText(((File) value).getAbsolutePath());
                // ������ļ��в��ҵ���������
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
//        // ��ȡÿһ�еĵ�һ����Ϣ
//        Object value = this.localDiskTable.getValueAt(selectRow,0);
//        if (value instanceof File) {
//            // ���ñ����ļ��е�·��
////            this.localSelFilePathLabel.getText() = value;
//            this.localSelFilePathLabel.setText(((File) value).getAbsolutePath());
//            // ������ļ��в��ҵ���������
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
     * �������ܣ���ȡ�����ļ������ķ���
     * @param selDisk
     */
    private void localFileList(File selDisk) {
        if (selDisk == null || selDisk.isFile()) {
            return;
        }
        localSelFilePathLabel.setText(selDisk.getAbsolutePath());
        // ��ȡ�����ļ��б�
        File[] listFiles = selDisk.listFiles();
        // ��ȡ��������ģ��
        DefaultTableModel model = (DefaultTableModel)localDiskTable.getModel();
        // ���������������
        model.setRowCount(0);
        model.addRow(new Object[]{".", "�ļ���", ""});
        model.addRow(new Object[]{"..", "�ļ���", ""});
        if (listFiles == null) {
            // ���û�ж������ļ���
            JOptionPane.showMessageDialog(this, "�ô����޷�����");
            return;
        }
        // ���������ļ��е����ݣ���ӵ����֮��
        for (File file:listFiles) {
            // �����ļ�����
            File diskFile = new File(file.toURI());
            String length = file.length() + "B";
            // ��ȡ�ļ����޸���Ϣ
//            file.lastModified()����1970-1-1-8��00֮��ĺ��������б�ʾ
            String theLastModify = new Date(file.lastModified()).toLocaleString();
            // �ж��ļ��Ƿ�ɶ�
            if (!file.canRead()) {
                length = "δ֪";
                theLastModify = "δ֪";
            }
            if (file.isDirectory()) {
                length = "�ļ���";
            }
            model.addRow(new Object[]{diskFile, length, theLastModify});
        }
    }

    /**
     * �������ܣ��鿴���е����ض���
     * @param theFileName
     */
    private void getDownloadQuenue(String theFileName) {
        //  ͬ���Ļ�ȡ��������ģ��
        DefaultTableModel model = (DefaultTableModel) downloadQuenue.getModel();
        // ��ձ���д��ڵ�������
        model.setRowCount(0);
        // �����ļ���
//        model.addRow(new Object[] {theFileName, ""});
        model.addRow(new Object[] {"hahaha", "gehee"});
    }

    /**
     * �������ܣ����Զ�̷����������е�ĳһ������ȡ�ļ���·����Ϣ
     * @param event
     */
    private void serverDiskTableMouseClicked(MouseEvent event) {
        // ��ȡ�Ѿ��ڷ�����������ѡ����к�
        int selectRow = this.serverDiskTable.getSelectedRow();
        if (selectRow < 0) {
//            JOptionPane.showMessageDialog(this, "");
            return ;
        }
        // ��ȡÿһ�еĵ�һ�е���Ϣ
        Object value = this.serverDiskTable.getValueAt(selectRow, 1);
        if (value instanceof String) {
//            this.thePathOfTheServer = value;
            // ����Զ����Ҫ���ص�·����Ϣ
            this.thePathOfTheServer = (String)value;
            // ����Զ����Ҫ���ص��ļ�����Ϣ
            this.theFileName = (String) this.serverDiskTable.getValueAt(selectRow, 0);
            // ���ѡ�е����ļ�����˫�����Σ���ˢ�µ�ǰ��Ŀ¼
            if ((boolean)this.serverDiskTable.getValueAt(selectRow, 2) && event.getClickCount() >= 2) {
                theRefreshButtonOfTheServer();
            }
            // ���ѡ�е����ļ�����˫�����Σ���ֱ�ӽ�������
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
     * �������ܣ���ȡ���������ļ�������
     * @param allTheItems
     */
    private void serverFileList(ArrayList<String> allTheItems) {
        // ���Ȼ�ȡ��������ģ��
        DefaultTableModel mode = (DefaultTableModel) serverDiskTable.getModel();
        // �����������е�����
        mode.setRowCount(0);
        mode.addRow(new Object[] {".", "�ļ���"});
        mode.addRow(new Object[] {"..", "�ļ���"});
        if (allTheItems == null) {
           // ��ʾ��Ϣ�����ļ�������û����Ŀ
            JOptionPane.showMessageDialog(this, "û�����ļ�");
            return;
        } else {
            // �����ַ������������ļ������ƽ�����ʾ
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
     * �������ܣ��������֮���������ķ�Ӧ��ˢ�µ�ǰ�������ػ����ϴ��ļ�����Ϣ
     * @param evt
     */
    private void theQuenueActionPerformed(java.awt.event.ActionEvent evt) {
        // ��ȡ�ļ������ض���
        getDownloadQuenue(this.theFileName);
    }

    private void uploadQueActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void downloadQuenueActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    /**
     * �������ܣ�ɾ����ť����Ӧ�Ĳ���
     * @param evt
     */
    private void stopTheButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        // TODO �����ͣ�İ�ť��������Ӧ
        // TODO ��������ֹͣ�ϴ��ļ��Ĳ��������ҽ���ť��Ϊ����
        // ���ȶԽ����������
        this.stopTheButton.setEnabled(false);
        this.goOn.setEnabled(true);
        // ��ֹ�ļ�����
//        this.ftpClient1.stop();
    }

    /**
     * �������ܣ�������ť����Ӧ�Ĳ���
     * @param evt
     */
    private void goOnActionPerformed(java.awt.event.ActionEvent evt) {
            // ���߼���ʵ��
//            System.out.println(currentProgressBar.getValue() + "��������ֵ�ı仯");
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
     * ɾ���������صİ�ť����Ӧ�Ĳ���
     * @param evt
     */
    private void deleteTheButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        // TODO �����Ƿ�Ҫ����ɾ������
        int confirmDialog = JOptionPane.showConfirmDialog(this.jScrollPane1, "ȷ���˳���¼��");
        // TODO �û�ȷ��Ҫ����ɾ������
        if (confirmDialog == JOptionPane.YES_OPTION) {
            // ����ftp�ͻ��˽�����Ӧ�����Ӳ���
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
         * ��ʾ�ͻ���ҳ��
         */
//        ftpClient theFrameClient = new ftpClient(new FTPClient1());
//        theFrameClient.setVisible(true);
//        try {
////            theFrameClient.ftpClient1.Get("D:\\ff", theFrameClient.currentProgressBar);
////            theFrameClinet.currentProgressBar.setValue(100);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        // ��ʾ�ͻ���ҳ��
        ftpClient theFrameClient = new ftpClient(new FunctionOfFtp());
        theFrameClient.setVisible(true);
    }

    /**
     * �������ܣ��õ�Ŀǰ�Ľ�����
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
