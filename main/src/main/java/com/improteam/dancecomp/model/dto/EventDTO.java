package com.improteam.dancecomp.model.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jury
 */
public class EventDTO {

    private List<JudgeDTO> judges;
    private List<ParticipantDTO> participants;
    private List<ContestDTO> contests;

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

    public List<ContestDTO> getContests() {
        return contests != null ? contests : new ArrayList<ContestDTO>();
    }

    public void setContests(List<ContestDTO> contests) {
        this.contests = contests;
    }
}