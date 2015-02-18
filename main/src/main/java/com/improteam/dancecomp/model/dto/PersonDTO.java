package com.improteam.dancecomp.model.dto;

import com.improteam.dancecomp.model.Gender;

/**
 * @author jury
 */
public class PersonDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private Gender gender;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}