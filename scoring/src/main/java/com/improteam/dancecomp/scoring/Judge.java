package com.improteam.dancecomp.scoring;

/**
 * @author jury
 */
public interface Judge {

    //unique between judges for competition scoring
    public String getCode();

    public boolean isChief();
}