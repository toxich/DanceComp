package com.improteam.dancecomp.gui;

import com.improteam.dancecomp.model.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
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
        return null;
    }

    public JTable getTable() {
        if (table == null) {
            table = new JTable(this, columns = new ResultColumnModel(judges, participants));
            table.getColumnModel().setColumnSelectionAllowed(false);
            table.setRowHeight(30);
            table.setRowSelectionAllowed(true);
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            table.setDefaultRenderer(Object.class, cellRenderer = new ColoredTableRenderer());
        }
        return table;
    }
}
