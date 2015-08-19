package com.improteam.dancecomp.model.dto;

import com.improteam.dancecomp.scoring.Participant;

/**
 * @author jury
 */
public class ParticipantDTO implements Participant {

    private String code;
    private String title;
    private int order;

    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
