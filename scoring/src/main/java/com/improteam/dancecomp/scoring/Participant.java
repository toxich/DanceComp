package com.improteam.dancecomp.scoring;

/**
 * @author jury
 */
public interface Participant {

    //unique between judges for competition scoring
    public String getCode();

    // Composed name for both leader and follower
    public String getTitle();

    // Order of the dance
    public Integer getOrder();
}