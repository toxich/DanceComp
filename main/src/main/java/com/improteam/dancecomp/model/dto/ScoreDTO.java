package com.improteam.dancecomp.model.dto;

import com.improteam.dancecomp.scoring.Score;

/**
 * @author jury
 */
public class ScoreDTO implements Score {

    private JudgeDTO judge;
    private ParticipantDTO participant;
    private Number rate;

    public JudgeDTO getJudge() {
        return judge;
    }

    public void setJudge(JudgeDTO judge) {
        this.judge = judge;
    }

    @Override
    public ParticipantDTO getParticipant() {
        return participant;
    }

    public void setParticipant(ParticipantDTO participant) {
        this.participant = participant;
    }

    public Number getRate() {
        return rate;
    }

    public void setRate(Number rate) {
        this.rate = rate;
    }
}
