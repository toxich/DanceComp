package com.improteam.dancecomp.gui;

import com.improteam.dancecomp.model.ContestModel;
import com.improteam.dancecomp.model.dto.*;
import com.improteam.dancecomp.scoring.Place;
import com.improteam.dancecomp.scoring.PlaceDetail;
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

    private ContestModel contestModel;
    private List<ParticipantDTO> participants;
    private List<ScoreDTO> scores;

    private JTable table;
    private ResultColumnModel columns;
    private ColoredTableRenderer cellRenderer;
    private DataChangeController dataChange;

    public ResultTable(ContestModel contestModel, DataChangeController dataChange) {
        this.contestModel = contestModel;
        this.participants = contestModel.getParticipants();
        this.scores = contestModel.getScores();
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
        else if (column instanceof ResultColumnModel.JudgeColumn) {
            JudgeDTO judge = ((ResultColumnModel.JudgeColumn) column).getJudge();
            Number score = getScore(judge, participant);
            return score != null ? score : "";
        }
        else if (column instanceof ResultColumnModel.ScoreColumn) {
            int lowPlace = ((ResultColumnModel.ScoreColumn) column).getLowPlace();
            for (Place place : contestModel.getPlaces()) {
                if (place.getParticipant().equals(participant)) {
                    for (PlaceDetail detail : place.getDetails()) {
                        if (detail.isRelated(lowPlace)) return detail.printDetail();
                    }
                    return place.getResultPlace();
                }
            }
        }
        else if (column instanceof ResultColumnModel.PlaceColumn) {
            for (Place place : contestModel.getPlaces()) {
                if (place.getParticipant().equals(participant)) return place.getResultPlace();
            }
        }
        return "";
    }

    private Number getScore(JudgeDTO judge, ParticipantDTO participant) {
        for (ScoreDTO score : scores) {
            if (score.getJudge().equals(judge) && score.getParticipant().equals(participant)) {
                return score.getRate();
            }
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
            dataChange.fireResultDataChanged();
        }
        else if (column instanceof ResultColumnModel.JudgeColumn) {
            JudgeDTO judge = ((ResultColumnModel.JudgeColumn) column).getJudge();
            setScore(judge, participant, value);
            contestModel.getPlaces().clear();
            dataChange.fireResultDataChanged();
        }
    }

    private void setScore(JudgeDTO judge, ParticipantDTO participant, String value) {
        ScoreDTO score = null;
        for (ScoreDTO cur : scores) {
            if (cur.getJudge().equals(judge) && cur.getParticipant().equals(participant)) {
                score = cur;
                break;
            }
        }
        if (score == null) {
            score = new ScoreDTO();
            score.setParticipant(participant);
            score.setJudge(judge);
            scores.add(score);
        }
        Integer rate = null;
        try {
            rate = Integer.parseInt(value);
        } catch (Exception e) {}
        score.setRate(rate);
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
        if (curRow < 0) return;
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
            table = new JTable(this, columns = new ResultColumnModel(contestModel));
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
