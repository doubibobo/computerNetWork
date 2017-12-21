package ftpClient;

import javax.swing.table.DefaultTableModel;

/**
 * Created by zhuch on 2017/7/6.
 */
public class LocalTabelModel extends DefaultTableModel {
    Class[] types = new Class[] { java.lang.Object.class,
            java.lang.String.class, java.lang.String.class };
    boolean[] canEdit = new boolean[] { false, false, false };

    LocalTabelModel() {
        super(new Object[][] {}, new String[] { "文件名", "大小", "日期" });
    }

    public Class getColumnClass(int columnIndex) {
        return types[columnIndex];
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit[columnIndex];
    }
}
