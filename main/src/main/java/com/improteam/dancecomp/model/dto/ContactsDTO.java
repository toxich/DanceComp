package com.improteam.dancecomp.model.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jury
 */
public class ContactsDTO {

    private List<String> emails;
    private List<String> phones;
    private List<String> addresses;

    public List<String> getEmails() {
        return emails != null ? emails : new ArrayList<String>();
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public List<String> getPhones() {
        return phones != null ? phones : new ArrayList<String>();
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }

    public List<String> getAddresses() {
        return addresses != null ? addresses : new ArrayList<String>();
    }

    public void setAddresses(List<String> addresses) {
        this.addresses = addresses;
    }
}