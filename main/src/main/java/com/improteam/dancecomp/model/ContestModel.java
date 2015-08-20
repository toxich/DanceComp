package com.improteam.dancecomp.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.improteam.dancecomp.model.dto.JudgeDTO;
import com.improteam.dancecomp.model.dto.ParticipantDTO;
import com.improteam.dancecomp.model.dto.ScoreDTO;
import com.improteam.dancecomp.scoring.Place;
import com.improteam.dancecomp.scoring.Score;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

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

    public String serializeModel() {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setPrettyPrinting().create();
        String result = gson.toJson(this);
        return result;
    }

    public void deserializeModel(String json) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setPrettyPrinting().create();
        ContestModel otherModel = gson.fromJson(json, ContestModel.class);
        StringBuilder message = new StringBuilder();

        judges.clear();
        participants.clear();
        scores.clear();
        places.clear();

        Map<String, JudgeDTO> jMap = otherModel.getJudgeMap(message);
        judges.addAll(jMap.values());
        Map<Integer, ParticipantDTO> pMap = otherModel.getParticipantMap(message);
        participants.addAll(pMap.values());
        for (ScoreDTO score : otherModel.getScores()) setScore(jMap, pMap, score);
    }

    private Map<String, JudgeDTO> getJudgeMap(StringBuilder message) {
        Map<String, JudgeDTO> result = new LinkedHashMap<>();
        for (JudgeDTO judge : getJudges()) {
            if (judge.getNick() == null) judge.setNick("");
            if (result.containsKey(judge.getNick())) {
                message.append("Duplicate judge code '").append(judge.getNick()).append("'\n");
                continue;
            }
            result.put(judge.getNick(), judge);
        }
        return result;
    }

    private Map<Integer, ParticipantDTO> getParticipantMap(StringBuilder message) {
        Map<Integer, ParticipantDTO> result = new LinkedHashMap<>();
        for (ParticipantDTO part : getParticipants()) {
            if (part.getOrder() == null) {
                message.append("Get participant without order '").append(part.getTitle()).append("'\n");
                continue;
            }
            if (result.containsKey(part.getOrder())) {
                message.append("Duplicate participant '").append(part.getOrder()).append("'\n");
                continue;
            }
            result.put(part.getOrder(), part);
        }
        return result;
    }

    private void setScore(
            Map<String, JudgeDTO> jMap,
            Map<Integer, ParticipantDTO> pMap,
            ScoreDTO newScore
    ) {
        if (
                newScore == null ||
                newScore.getJudge() == null ||
                newScore.getParticipant() == null ||
                newScore.getRate() == null
        ) {
            return;
        }

        if (newScore.getJudge().getNick() == null) newScore.getJudge().setNick("");
        JudgeDTO judge = jMap.get(newScore.getJudge().getNick());
        ParticipantDTO part = pMap.get(newScore.getParticipant().getOrder());
        if (judge == null || part == null) return;

        ScoreDTO score = null;
        for (ScoreDTO cur : getScores()) {
            if (cur.getJudge().equals(judge) && cur.getParticipant().equals(part)) {
                score = cur;
                break;
            }
        }
        if (score == null) {
            score = new ScoreDTO();
            score.setJudge(judge);
            score.setParticipant(part);
            getScores().add(score);
        }
        score.setRate(newScore.getRate().intValue());
    }
}
