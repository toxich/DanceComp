package com.improteam.dancecomp.model.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jury
 */
public class EventDTO {

    private Long id;
    private List<ContestDTO> contests;
    private List<JudgeDTO> judges;
    private List<ParticipantDTO> leaders;
    private List<ParticipantDTO> followers;
    private List<ParticipantsPairDTO> pairs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ContestDTO> getContests() {
        return contests != null ? contests : new ArrayList<ContestDTO>();
    }

    public void setContests(List<ContestDTO> contests) {
        this.contests = contests;
    }

    public List<JudgeDTO> getJudges() {
        return judges != null ? judges : new ArrayList<JudgeDTO>();
    }

    public void setJudges(List<JudgeDTO> judges) {
        this.judges = judges;
    }

    public List<ParticipantDTO> getLeaders() {
        return leaders != null ? leaders : new ArrayList<ParticipantDTO>();
    }

    public void setLeaders(List<ParticipantDTO> leaders) {
        this.leaders = leaders;
    }

    public List<ParticipantDTO> getFollowers() {
        return followers != null ? followers : new ArrayList<ParticipantDTO>();
    }

    public void setFollowers(List<ParticipantDTO> followers) {
        this.followers = followers;
    }

    public List<ParticipantsPairDTO> getPairs() {
        return pairs != null ? pairs : new ArrayList<ParticipantsPairDTO>();
    }

    public void setPairs(List<ParticipantsPairDTO> pairs) {
        this.pairs = pairs;
    }
}