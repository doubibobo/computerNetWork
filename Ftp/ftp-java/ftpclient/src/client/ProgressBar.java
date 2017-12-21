package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;


public class ProgressBar extends JFrame implements Runnable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JProgressBar bar;
	JPanel panel;
	InputStream in;
//	JButton button;

	public ProgressBar(String s)
	{
		setTitle(s);
		bar = new JProgressBar(0, 100);
		bar.setValue(5);// 设置进度条当前值
		this.setSize(300, 80);

		panel = new JPanel();
//		button = new JButton("完成");
//		button.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				ProgressBar.this.dispose();
//			}
//		});
		panel.setLayout(new FlowLayout());
		bar.setPreferredSize(new Dimension(300, 37));
		bar.setStringPainted(true);
		bar.setString(s+"进度");
		bar.setBackground(Color.ORANGE);
		panel.add(bar);
//		panel.add(button);
		this.add(panel, BorderLayout.CENTER);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setAlwaysOnTop(true);
		
		//显示在屏幕中央
		Dimension scr = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension fra = getSize();
		if (fra.width > scr.width) {
			fra.width = scr.width;
		}
		if (fra.height > scr.height) {
			fra.height = scr.height;
		}
		setLocation((scr.width - fra.width) / 2, (scr.height - fra.height) / 2);
				
		setVisible(true);
	}
	
	public ProgressBar(String s, BufferedInputStream in){
		this(s);
		this.in = in;
	}
	
	public void closeBar(){
		bar.setValue(100);
//		dispose();
	}

	@Override
//	public void run() {
//		while(bar.getValue() != 100){
//			try {
//				Thread.sleep(200);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			int value = (int)(Math.random()*10)+bar.getValue();
//			if(value >= 100)
//				value = 100;
//			bar.setValue(value);
//		}
//		dispose();
//	}
	public void run() {
		try {
			long len = in.available();
			while(bar.getValue() != 100){
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				int value = (int)((len - in.available())*1.0/len*100);
				bar.setValue(value);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		dispose();
	}
	
	public static void main(String[] args){
		ProgressBar pBar = new ProgressBar("下载");
		Thread thread = new Thread(pBar);
		thread.start();
		
//		pBar.closeBar();
	}
}
