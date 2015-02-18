package com.improteam.dancecomp.model.dto;

import com.improteam.dancecomp.model.ContestLevel;
import com.improteam.dancecomp.model.ContestType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jury
 */
public class ContestDTO {

    private ContestType contestType;
    private ContestLevel contestLevel;

    private List<JudgeDTO> judges;
    private List<ParticipantDTO> participants;
    private List<ScoringDTO> scoring;
    protected ContestResultDTO result;

    public ContestType getContestType() {
        return contestType;
    }

    public void setContestType(ContestType contestType) {
        this.contestType = contestType;
    }

    public ContestLevel getContestLevel() {
        return contestLevel;
    }

    public void setContestLevel(ContestLevel contestLevel) {
        this.contestLevel = contestLevel;
    }

    public List<JudgeDTO> getJudges() {
        return judges != null ? judges : new ArrayList<JudgeDTO>();
    }

    public void setJudges(List<JudgeDTO> judges) {
        this.judges = judges;
    }

    public List<ParticipantDTO> getParticipants() {
        return participants != null ? participants : new ArrayList<ParticipantDTO>();
    }

    public void setParticipants(List<ParticipantDTO> participants) {
        this.participants = participants;
    }

    public List<ScoringDTO> getScoring() {
        return scoring != null ? scoring : new ArrayList<ScoringDTO>();
    }

    public void setScoring(List<ScoringDTO> scoring) {
        this.scoring = scoring;
    }

    public ContestResultDTO getResult() {
        return result;
    }

    public void setResult(ContestResultDTO result) {
        this.result = result;
    }
}