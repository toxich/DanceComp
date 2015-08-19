package com.improteam.dancecomp.model.dto;

/**
 * @author jury
 */
public class ParticipantsPairDTO {

    private Long id;
    private ContestDTO contest;
    private Integer contestNumber;
    private EventParticipantDTO leader;
    private EventParticipantDTO follower;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ContestDTO getContest() {
        return contest;
    }

    public void setContest(ContestDTO contest) {
        this.contest = contest;
    }

    public Integer getContestNumber() {
        return contestNumber;
    }

    public void setContestNumber(Integer contestNumber) {
        this.contestNumber = contestNumber;
    }

    public EventParticipantDTO getLeader() {
        return leader;
    }

    public void setLeader(EventParticipantDTO leader) {
        this.leader = leader;
    }

    public EventParticipantDTO getFollower() {
        return follower;
    }

    public void setFollower(EventParticipantDTO follower) {
        this.follower = follower;
    }
}