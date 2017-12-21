package demo;

import java.io.File;
import java.nio.file.Files;

/**
 * Created by zhuch on 2017/7/7.
 */
public class theDemoTwo {
    public static void main(String[] args) {
        File selDisk = new File("D:");
//        String[] files = selDisk.list();
//        for (String fileName : files) {
//            System.out.println(fileName);
//        }
        String[] theFileInformation;
        File[] files = selDisk.listFiles();
        if (files != null) {
            for (File fileInformation : files) {
//                theFileInformation = fileInformation.getName();
            }
        }
    }
}
