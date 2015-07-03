package com.improteam.dancecomp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainLib {

    final static Logger LOG = LoggerFactory.getLogger(MainLib.class);

    public void libMethod() {
        System.out.println("Hallo Team!!!");
        LOG.info("Real logging!");
    }
}