package com.improteam.dancecomp.gui;

import com.improteam.dancecomp.model.dto.JudgeDTO;
import com.improteam.dancecomp.model.dto.ParticipantDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author jury
 */
public class ResultColumnModel extends DefaultTableColumnModel {

    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(ResultColumnModel.class);

    private List<JudgeDTO> judges;
    private List<ParticipantDTO> participants;

    private List<TableColumn> columns;

    public static final String[] PARTICIPANT_INFO = {
            "N",
            "Participants"
    };

    public ResultColumnModel(List<JudgeDTO> judges, List<ParticipantDTO> participants) {
        this.judges = judges;
        this.participants = participants;
        columns = new ArrayList<>();
        initColumns();
    }

    @Override
    public int getColumnCount() {
        return columns.size();
    }

    public void initColumns() {
        for (TableColumn column : new ArrayList<>(columns)) removeColumn(column);
        for (int i = 0; i < PARTICIPANT_INFO.length; i++) addColumn(new ParticipantColumn(i), i);
        setJudgeColumns();
        setScoreColumns();
        addColumn(new PlaceColumn(), columns.size());
    }

    private void addColumn(TableColumn column, int index) {
        columns.add(index, column);
        addColumn(column);
        setColumnsOrder();
    }

    @Override
    public void removeColumn(TableColumn column) {
        columns.remove(column);
        super.removeColumn(column);
    }

    private void setJudgeColumns() {
        for (TableColumn column : new ArrayList<>(columns)) {
            if (column instanceof JudgeColumn) removeColumn(column);
        }
        List<JudgeDTO> reversed = new ArrayList<>(judges);
        Collections.reverse(reversed);
        for (JudgeDTO judge : reversed) {
            JudgeColumn column = new JudgeColumn(judge);
            addColumn(column, PARTICIPANT_INFO.length);
        }
        setColumnsOrder();
    }

    private void setScoreColumns() {
        for (TableColumn column : new ArrayList<>(columns)) {
            if (column instanceof ScoreColumn) removeColumn(column);
        }
        for (int i = 0; i < participants.size(); i++) {
            addColumn(new ScoreColumn(i), PARTICIPANT_INFO.length + judges.size() + i);
        }
        setColumnsOrder();
    }

    private void setColumnsOrder() {
        for (int target = 0; target < columns.size(); target++) {
            TableColumn column = columns.get(target);
            int real = getColumnIndex(column.getIdentifier());
            if (target != real) moveColumn(real, target);
        }
    }

    public static class ParticipantColumn extends TableColumn {

        private int index;

        public ParticipantColumn(int index) {
            super();
            this.index = index;
            setIdentifier(PARTICIPANT_INFO[index]);
            if (index == 0) setMaxWidth(30);
            else setMinWidth(200);
        }

        @Override
        public Object getHeaderValue() {
            return PARTICIPANT_INFO[index];
        }
    }

    public static class JudgeColumn extends TableColumn {

        private JudgeDTO judge;

        public JudgeColumn(JudgeDTO judge) {
            super();
            this.judge = judge;
            setIdentifier(judge);
            setMaxWidth(30);
        }

        @Override
        public Object getHeaderValue() {
            return judge.getCode();
        }

        public JudgeDTO getJudge() {
            return judge;
        }
    }

    public static class ScoreColumn extends TableColumn {

        public ScoreColumn(int index) {
            super();
            setIdentifier(index + 1);
            setHeaderValue("1-" + (index + 1));
        }

        public int getLowPlace() {
            return (Integer) getIdentifier();
        }
    }

    public static class PlaceColumn extends TableColumn {

        public PlaceColumn() {
            super();
            setIdentifier("Result Place");
            setHeaderValue("R");
            setMaxWidth(30);
        }
    }
}
