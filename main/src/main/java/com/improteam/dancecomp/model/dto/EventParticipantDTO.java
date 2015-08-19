package com.improteam.dancecomp.model.dto;

import com.improteam.dancecomp.model.ParticipantType;

/**
 * @author jury
 */
public class EventParticipantDTO extends PersonDTO {

    private ParticipantType participantType;
    private Integer contestNumber;
    private Integer wsdcNumber;

    public ParticipantType getParticipantType() {
        return participantType;
    }

    public void setParticipantType(ParticipantType participantType) {
        this.participantType = participantType;
    }

    public Integer getContestNumber() {
        return contestNumber;
    }

    public void setContestNumber(Integer contestNumber) {
        this.contestNumber = contestNumber;
    }

    public Integer getWsdcNumber() {
        return wsdcNumber;
    }

    public void setWsdcNumber(Integer wsdcNumber) {
        this.wsdcNumber = wsdcNumber;
    }
}