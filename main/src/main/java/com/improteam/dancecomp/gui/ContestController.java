package com.improteam.dancecomp.gui;

import com.improteam.dancecomp.model.dto.JudgeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    public void start() {
        mainFrame = new ContestMainFrame();
        mainFrame.setJudges(judges = new ArrayList<JudgeDTO>());
        mainFrame.createFrame();
    }

    public void exit() {
//        if (mainFrame != null) mainFrame.
    }

}

