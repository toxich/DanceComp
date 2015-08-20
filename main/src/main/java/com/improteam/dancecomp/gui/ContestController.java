package com.improteam.dancecomp.gui;

import com.improteam.dancecomp.model.ContestModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jury
 */
public class ContestController {

    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(ContestController.class);

    private ContestMainFrame mainFrame;
    private ContestModel contestModel;

    public void start() {
        mainFrame = new ContestMainFrame();
        mainFrame.setContestModel(contestModel = new ContestModel());
        mainFrame.createFrame();
    }

    public void exit() {
//        if (mainFrame != null) mainFrame.
    }

}

