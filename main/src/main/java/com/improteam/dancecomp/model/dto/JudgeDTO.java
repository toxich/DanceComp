package com.improteam.dancecomp.model.dto;

/**
 * @author jury
 */
public class JudgeDTO extends PersonDTO {

    private boolean chief;
    private boolean trial;
    private boolean onlyLeaders;
    private boolean onlyFollowers;

    public boolean isChief() {
        return chief;
    }

    public void setChief(boolean chief) {
        this.chief = chief;
    }

    public boolean isTrial() {
        return trial;
    }

    public void setTrial(boolean trial) {
        this.trial = trial;
    }

    public boolean isOnlyLeaders() {
        return onlyLeaders;
    }

    public void setOnlyLeaders(boolean onlyLeaders) {
        this.onlyLeaders = onlyLeaders;
    }

    public boolean isOnlyFollowers() {
        return onlyFollowers;
    }

    public void setOnlyFollowers(boolean onlyFollowers) {
        this.onlyFollowers = onlyFollowers;
    }
}