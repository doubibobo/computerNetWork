package demo;

import javafx.stage.FileChooser;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.text.LabelView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by zhuch on 2017/7/7.
 */
public class theLeastDemo extends JFrame implements ActionListener{
    JButton open = null;
    public static void main(String[] args) {
        new theLeastDemo();
    }

    public theLeastDemo() {
        open = new JButton("open");
        this.add(open);
        this.setBounds(400, 200, 100, 100);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        open.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        jFileChooser.showDialog(new JLabel(), "选择");
        File file = jFileChooser.getSelectedFile();
        if (file.isDirectory()) {
            System.out.println("文件夹：" + file.getAbsolutePath());
        } else if (file.isFile()) {
            System.out.println("文件：" + file.getAbsolutePath());
        }
        System.out.println(jFileChooser.getSelectedFile().getName());
    }
}
