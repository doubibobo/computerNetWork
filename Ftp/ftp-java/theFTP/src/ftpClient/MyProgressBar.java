package ftpClient;

import javax.swing.*;

/**
 * Created by zhuch on 2017/7/9.
 */
public class MyProgressBar extends JProgressBar implements Runnable {
    private int theValueOfTheProgressBar;

    /**
     * ���췽��
     * @param value
     */
    MyProgressBar(int value) {
        super();
        theValueOfTheProgressBar = value;
    }

    @Override
    public void run() {
        this.setValue(this.theValueOfTheProgressBar);
    }
}
