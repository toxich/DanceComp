package com.improteam.dancecomp.gui;

import com.improteam.dancecomp.model.dto.JudgeDTO;
import com.improteam.dancecomp.model.dto.ParticipantDTO;
import com.improteam.dancecomp.model.dto.ScoreDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jury
 */
public class ContestController {

    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(ContestController.class);

    private ContestMainFrame mainFrame;
    private List<JudgeDTO> judges;
    private List<ParticipantDTO> participants;
    private List<ScoreDTO> scores;

    public void start() {
        mainFrame = new ContestMainFrame();
        mainFrame.setJudges(judges = new ArrayList<JudgeDTO>());
        mainFrame.setParticipants(participants = new ArrayList<ParticipantDTO>());
        mainFrame.setScores(scores = new ArrayList<ScoreDTO>());
        mainFrame.createFrame();
    }

    public void exit() {
//        if (mainFrame != null) mainFrame.
    }

}

