package ftpClient;

import com.sun.javaws.progress.Progress;

import javax.swing.*;

/**
 * Created by zhuch on 2017/7/9.
 */
public class ProgressThread extends Thread {
    public ProgressThread(JProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    private JProgressBar progressBar;

    @Override
    public void run() {
        super.run();
    }
}
