package com.improteam.dancecomp.model.dto;

/**
 * @author jury
 */
public class ParticipantsPairDTO {

    private Long id;
    private ContestDTO contest;
    private Integer contestNumber;
    private ParticipantDTO leader;
    private ParticipantDTO follower;

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

    public ParticipantDTO getLeader() {
        return leader;
    }

    public void setLeader(ParticipantDTO leader) {
        this.leader = leader;
    }

    public ParticipantDTO getFollower() {
        return follower;
    }

    public void setFollower(ParticipantDTO follower) {
        this.follower = follower;
    }
}