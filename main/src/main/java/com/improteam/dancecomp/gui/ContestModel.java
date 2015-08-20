package com.improteam.dancecomp.gui;

import com.improteam.dancecomp.model.dto.JudgeDTO;
import com.improteam.dancecomp.model.dto.ParticipantDTO;
import com.improteam.dancecomp.model.dto.ScoreDTO;
import com.improteam.dancecomp.scoring.Place;
import com.improteam.dancecomp.scoring.PlaceDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jury
 */
public class ContestModel {

    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(ContestModel.class);

    private List<JudgeDTO> judges = new ArrayList<>();
    private List<ParticipantDTO> participants = new ArrayList<>();
    private List<ScoreDTO> scores = new ArrayList<>();
    private List<Place> places = new ArrayList<>();

    public List<JudgeDTO> getJudges() {
        return judges;
    }

    public List<ParticipantDTO> getParticipants() {
        return participants;
    }

    public List<ScoreDTO> getScores() {
        return scores;
    }

    public List<Place> getPlaces() {
        return places;
    }
}
