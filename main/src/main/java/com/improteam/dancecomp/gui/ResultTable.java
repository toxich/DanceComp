package com.improteam.dancecomp.gui;

import com.improteam.dancecomp.model.dto.*;
import com.improteam.dancecomp.scoring.Participant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.List;

/**
 * @author jury
 */
public class ResultTable extends AbstractTableModel {

    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(ResultTable.class);

    private List<JudgeDTO> judges;
    private List<ParticipantDTO> participants;
    private List<ScoreDTO> scores;

    private JTable table;
    private ResultColumnModel columns;
    private ColoredTableRenderer cellRenderer;
    private DataChangeController dataChange;

    public ResultTable(
            List<JudgeDTO> judges,
            List<ParticipantDTO> participants,
            List<ScoreDTO> scores,
            DataChangeController dataChange
    ) {
        this.judges = judges;
        this.participants = participants;
        this.scores = scores;
        this.dataChange = dataChange;
        addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                columns.initColumns();
            }
        });
    }

    @Override
    public int getRowCount() {
        return participants.size();
    }

    @Override
    public int getColumnCount() {
        return columns.getColumnCount();
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return col == 1 || cellRenderer.getSpecialCols().get(col) != null;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ParticipantDTO participant = participants.get(rowIndex);
        TableColumn column = columns.getColumn(columnIndex);
        if (column instanceof ResultColumnModel.ParticipantColumn) {
            return ((ResultColumnModel.ParticipantColumn) column).getInfo(participant);
        }

        return null;
    }

    @Override
    public void setValueAt(Object valueObj, int row, int col) {
        String value = valueObj != null ? valueObj.toString() : "";
        ParticipantDTO participant = participants.get(row);
        TableColumn column = columns.getColumn(col);
        if (column instanceof ResultColumnModel.ParticipantColumn) {
            ((ResultColumnModel.ParticipantColumn) column).setInfo(participant, value);
        }

    }

    public void addParticipant() {
        participants.add(new ParticipantDTO());
        setOrder();
    }

    public void removeSelectedParticipant() {
        participants.remove(table.getSelectedRow());
        setOrder();
    }

    public void moveSelectedParticipant(boolean up) {
        int curRow = table.getSelectedRow();
        int newRow = up ? curRow -1 : curRow + 1;
        if (participants.size() <= 1 || newRow < 0 || newRow >= participants.size()) return;
        ParticipantDTO curPart = participants.get(curRow);
        ParticipantDTO otherPart = participants.get(newRow);
        participants.set(curRow, otherPart);
        participants.set(newRow, curPart);
        setOrder();
    }

    private void setOrder() {
        for (int i = 0; i < participants.size(); i++) participants.get(i).setOrder(i + 1);
    }

    public JTable getTable() {
        if (table == null) {
            table = new JTable(this, columns = new ResultColumnModel(judges, participants));
            table.getColumnModel().setColumnSelectionAllowed(false);
            table.setRowHeight(30);
            table.setRowSelectionAllowed(true);
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            table.setDefaultRenderer(Object.class, cellRenderer = new ColoredTableRenderer());
            cellRenderer.setSpecialCols(columns.getRowMark());
            table.setPreferredScrollableViewportSize(new Dimension(1200, 500));
        }
        return table;
    }
}
