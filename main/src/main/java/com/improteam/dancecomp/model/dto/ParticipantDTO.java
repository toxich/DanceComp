package com.improteam.dancecomp.model.dto;

import com.improteam.dancecomp.scoring.Participant;

/**
 * @author jury
 */
public class ParticipantDTO implements Participant {

    private String code;
    private String fullName;
    private int order;

    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
