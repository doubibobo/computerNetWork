package ftpClient;

import javax.swing.*;

/**
 * Created by zhuch on 2017/7/9.
 */
public class MyProgressBar extends JProgressBar implements Runnable {
    private int theValueOfTheProgressBar;

    /**
     * 构造方法
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
