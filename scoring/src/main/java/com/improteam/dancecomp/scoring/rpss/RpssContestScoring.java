package com.improteam.dancecomp.scoring.rpss;

import com.improteam.dancecomp.scoring.Judge;
import com.improteam.dancecomp.scoring.Participant;
import com.improteam.dancecomp.scoring.Score;

import java.util.List;

/**
 * @author jury
 */
public interface RpssContestScoring {

    public List<Participant> getParticipants();
    public List<Judge> getJudges();
    public List<Score> getScores();
}