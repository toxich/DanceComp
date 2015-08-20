package com.improteam.dancecomp.scoring;

import java.util.List;

/**
 * @author jury
 */
public interface Place {

    Participant getParticipant();
    Integer getResultPlace();
    List<PlaceDetail> getDetails();
}
