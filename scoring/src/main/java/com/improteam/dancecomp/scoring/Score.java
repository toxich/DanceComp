package com.improteam.dancecomp.scoring;

/**
 * @author jury
 */
public interface Score {

    public Participant getParticipant();
    public Judge getJudge();
    public Number getRate();
}