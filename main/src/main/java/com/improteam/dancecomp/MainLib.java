package com.improteam.dancecomp;

import com.improteam.dancecomp.gui.ContestController;
import com.sun.java.swing.SwingUtilities3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

public class MainLib {

    final static Logger logger = LoggerFactory.getLogger(MainLib.class);

    public void libMethod() {
        System.out.println("Hallo Team!!!");
        logger.info("Real logging!");

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            logger.warn("No Nimbus Look&Feel installed, use default");
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ContestController().start();
            }
        });
    }
}