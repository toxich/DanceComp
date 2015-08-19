package com.improteam.dancecomp.gui;

import com.improteam.dancecomp.model.dto.JudgeDTO;
import com.improteam.dancecomp.model.dto.ParticipantDTO;
import com.improteam.dancecomp.model.dto.ScoreDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * @author jury
 */
public class ContestMainFrame implements DataChangeController {

    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(ContestMainFrame.class);

    private List<JudgeDTO> judges;
    private List<ParticipantDTO> participants;
    private List<ScoreDTO> scores;

    private JudgeTable judgeTable;
    private ResultTable resultTable;

    private JFrame mainFrame;
    private JButton addJudgeButton;
    private JButton delJudgeButton;
    private JButton upJudgeButton;
    private JButton downJudgeButton;
    private JButton saveButton;
    private JButton loadButton;
    private JButton calcButton;
    private JButton printButton;

    public void setJudges(List<JudgeDTO> judges) {
        this.judges = judges;
    }

    public void setParticipants(List<ParticipantDTO> participants) {
        this.participants = participants;
    }

    public void setScores(List<ScoreDTO> scores) {
        this.scores = scores;
    }

    public void createFrame() {
        if (mainFrame != null) {
            logger.error("Application is started already");
        }
        mainFrame = new JFrame("RPSS contest calculation");
        mainFrame .setMinimumSize(new Dimension(600, 360));
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        createControls();
        mainFrame.add(createMailPanel());
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    private void createControls() {
        judgeTable = new JudgeTable(judges, this);
        judgeTable.addNewJudge();
        resultTable = new ResultTable(judges, participants, scores, this);

        addJudgeButton = newButton("Add Judge");
        addJudgeButton.addActionListener(new AddJudgeListener());
        delJudgeButton = newButton("Delete Judge");
        delJudgeButton.addActionListener(new DeleteJudgeListener());
        upJudgeButton = newButton("Move Up");
        upJudgeButton.addActionListener(new MoveUpJudgeListener());
        downJudgeButton = newButton("Move Down");
        downJudgeButton.addActionListener(new MoveDownJudgeListener());

        saveButton = newButton("Save");
        loadButton = newButton("Load");
        calcButton = newButton("Calculate");
        printButton = newButton("Print");
    }

    private JButton newButton(String name) {
        JButton result = new JButton(name);
        result.setMinimumSize(new Dimension(130, 25));
        result.setMaximumSize(new Dimension(130, 25));
        return result;
    }

    private JPanel createMailPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(createControlPanel(), BorderLayout.NORTH);
        mainPanel.add(createResultsPanel(), BorderLayout.SOUTH);
        return mainPanel;
    }

    private JPanel createControlPanel() {
        JPanel controlPanel = new JPanel();
        controlPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.X_AXIS));
        controlPanel.add(new JScrollPane(judgeTable.getTable()));
        controlPanel.add(Box.createHorizontalStrut(10));
        controlPanel.add(createJudgeButtonsPanel());
        controlPanel.add(Box.createHorizontalStrut(200));
        controlPanel.add(createDataButtonsPanel());
        return controlPanel;
    }

    private JPanel createResultsPanel() {
        JPanel resultPanel = new JPanel();
        resultPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        resultPanel.setLayout(new BorderLayout());
        resultPanel.add(new JScrollPane(resultTable.getTable()), BorderLayout.CENTER);
        return resultPanel;
    }

    private JPanel createJudgeButtonsPanel() {
        JPanel jButtonPanel = new JPanel();
        jButtonPanel.setLayout(new BoxLayout(jButtonPanel, BoxLayout.Y_AXIS));
        jButtonPanel.add(addJudgeButton);
        jButtonPanel.add(Box.createVerticalStrut(5));
        jButtonPanel.add(delJudgeButton);
        jButtonPanel.add(Box.createVerticalStrut(5));
        jButtonPanel.add(upJudgeButton);
        jButtonPanel.add(Box.createVerticalStrut(5));
        jButtonPanel.add(downJudgeButton);
        return jButtonPanel;
    }

    private JPanel createDataButtonsPanel() {
        JPanel jButtonPanel = new JPanel();
        jButtonPanel.setLayout(new BoxLayout(jButtonPanel, BoxLayout.Y_AXIS));
        jButtonPanel.add(saveButton);
        jButtonPanel.add(Box.createVerticalStrut(5));
        jButtonPanel.add(loadButton);
        jButtonPanel.add(Box.createVerticalStrut(5));
        jButtonPanel.add(calcButton);
        jButtonPanel.add(Box.createVerticalStrut(5));
        jButtonPanel.add(printButton);
        return jButtonPanel;
    }

    private class AddJudgeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            judgeTable.addNewJudge();
            fireJudgeDataChanged();
        }
    }

    private class DeleteJudgeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            judgeTable.removeSelectedJudge();
            fireJudgeDataChanged();
        }
    }

    private class MoveUpJudgeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            judgeTable.moveSelectedJudge(true);
            fireJudgeDataChanged();
        }
    }

    private class MoveDownJudgeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            judgeTable.moveSelectedJudge(false);
            fireJudgeDataChanged();
        }
    }

    public void fireJudgeDataChanged() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                judgeTable.fireTableDataChanged();
                fireResultTableChanged();
            }
        });
    }

    public void fireResultDataChanged() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                resultTable.fireTableDataChanged();
            }
        });
    }

    public void fireResultTableChanged() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                resultTable.fireTableStructureChanged();
            }
        });
    }
}
