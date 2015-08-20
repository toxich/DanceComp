package com.improteam.dancecomp.gui;

import com.improteam.dancecomp.model.ContestModel;
import com.improteam.dancecomp.model.dto.JudgeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;

/**
 * @author jury
 */
public class JudgeTable extends AbstractTableModel {

    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(JudgeTable.class);

    public static final String[] COLUMNS = {
            "N",
            "Code",
            "Chief",
            "Full Name"
    };

    private List<JudgeDTO> judges;

    private JTable table;
    private DataChangeController dataChange;

    public JudgeTable(ContestModel contestModel, DataChangeController dataChange) {
        this.judges = contestModel.getJudges();
        this.dataChange = dataChange;
    }

    @Override
    public int getRowCount() {
        return judges.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMNS.length;
    }

    @Override
    public String getColumnName(int column) {
        return COLUMNS[column];
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return col != 0;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        JudgeDTO judge = judges.get(rowIndex);
        switch (columnIndex) {
            case 0: return rowIndex + 1;
            case 1: return judge.getCode();
            case 2: return judge.isChief() ? "x" : "";
            case 3: return judge.getFullName();
        }
        return null;
    }

    @Override
    public void setValueAt(Object valueObj, int row, int col) {
        String value = valueObj != null ? valueObj.toString() : "";
        JudgeDTO judge = judges.get(row);
        switch (col) {
            case 1:
                setCode(judge, value);
                break;
            case 2:
                setChief(judge, value.length() > 0);
                break;
            case 3:
                judge.setFullName(value);
                break;
        }
        dataChange.fireJudgeDataChanged();
    }

    public JTable getTable() {
        if (table == null) {
            table = new JTable(this);
            for (int cIndex = 0; cIndex < COLUMNS.length; cIndex++) {
                TableColumn column = table.getColumnModel().getColumn(cIndex);
                column.setHeaderValue(COLUMNS[cIndex]);
                if (cIndex == 0) column.setMaxWidth(30);
                else if (cIndex == 1) column.setMaxWidth(45);
                else if (cIndex == 2) column.setMaxWidth(45);
                else column.setMinWidth(300);
            }
            table.getColumnModel().setColumnSelectionAllowed(false);
            table.setRowHeight(30);
            table.setRowSelectionAllowed(true);
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            table.setDefaultRenderer(Object.class, new ColoredTableRenderer());
            table.setPreferredScrollableViewportSize(new Dimension(600, 250));
        }
        return table;
    }

    public void addNewJudge() {
        JudgeDTO judge = new JudgeDTO();
        judge.setNick(newJudgeCode());
        judge.setChief(true);
        for (JudgeDTO cur : judges) {
            if (cur.isChief()) {
                judge.setChief(false);
                break;
            }
        }
        judges.add(judge);
    }

    public void removeSelectedJudge() {
        judges.remove(table.getSelectedRow());
        checkChief();
    }

    public void moveSelectedJudge(boolean up) {
        int curRow = table.getSelectedRow();
        if (curRow < 0) return;
        int newRow = up ? curRow -1 : curRow + 1;
        if (judges.size() <= 1 || newRow < 0 || newRow >= judges.size()) return;
        JudgeDTO curJudge = judges.get(curRow);
        JudgeDTO otherJudge = judges.get(newRow);
        judges.set(curRow, otherJudge);
        judges.set(newRow, curJudge);
    }

    private String newJudgeCode() {
        char code = (char) ('A' + judges.size());
        FREE_CODE:
        while (true) {
            for (JudgeDTO cur : judges) {
                if (cur.getCode().length() > 0 && cur.getCode().charAt(0) == code) {
                    code++;
                    continue FREE_CODE;
                }
            }
            break;
        }
        return String.valueOf(code);
    }

    private void setCode(JudgeDTO judge, String value) {
        String message = null;
        if (value.length() == 0) {
            message = "Judge code should not be empty";
        } else {
            for (JudgeDTO cur : judges) {
                if (cur != judge && value.equals(cur.getNick())) {
                    message = "Code '" + value + "' is in use already";
                    break;
                }
            }
        }
        if (message != null) {
            JOptionPane.showMessageDialog(null, message);
        } else {
            judge.setNick(value.length() > 0 ? value : newJudgeCode());
        }
    }

    private void setChief(JudgeDTO judge, boolean chief) {
        if (chief) {
            for (JudgeDTO cur : judges) {
                cur.setChief(cur == judge);
            }
        } else {
            judge.setChief(false);
            checkChief();
        }
    }

    private void checkChief() {
        boolean hasChief = false;
        for (JudgeDTO cur : judges) {
            if (hasChief) cur.setChief(false);
            else hasChief = cur.isChief();
        }
        if (judges.size() > 0) judges.get(0).setChief(true);
    }
}
