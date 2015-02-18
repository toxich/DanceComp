package com.improteam.dancecomp.model.dto;

import com.improteam.dancecomp.model.ParticipantType;

/**
 * @author jury
 */
public class ParticipantDTO extends PersonDTO {

    private ParticipantType participantType;

    public ParticipantType getParticipantType() {
        return participantType;
    }

    public void setParticipantType(ParticipantType participantType) {
        this.participantType = participantType;
    }
}