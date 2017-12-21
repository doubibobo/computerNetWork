package ftpClient;

import javax.swing.*;
import java.awt.event.*;

/**
 * Created by zhuch on 2017/7/7.
 */
public class HaHa {
    private JTextField textField1;
    private JPanel panel1;
    private JButton button1;
    private JButton button2;
    private JButton button3;

    public HaHa() {
        textField1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        button2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
        button3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
    }

    public static void main(String[] args) {
//        HaHa HAHA = new HaHa();
//        HAHA.
    }
}
