package com.improteam.dancecomp.model.dto;

import com.improteam.dancecomp.scoring.Judge;

/**
 * @author jury
 */
public class JudgeDTO extends PersonDTO implements Judge {

    private boolean chief;
    private boolean trial;
    private boolean judgeLeaders;
    private boolean judgeFollowers;
    private boolean judgeAll;
    private String nick;

    @Override
    public boolean isChief() {
        return chief;
    }

    @Override
    public String getCode() {
        return getNick();
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

    public boolean isJudgeLeaders() {
        return judgeLeaders;
    }

    public void setJudgeLeaders(boolean judgeLeaders) {
        this.judgeLeaders = judgeLeaders;
    }

    public boolean isJudgeFollowers() {
        return judgeFollowers;
    }

    public void setJudgeFollowers(boolean judgeFollowers) {
        this.judgeFollowers = judgeFollowers;
    }

    public boolean isJudgeAll() {
        return judgeAll;
    }

    public void setJudgeAll(boolean judgeAll) {
        this.judgeAll = judgeAll;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
}