package com.epam.training.ticketservice.database.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class UserEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String pw;
    private boolean privileged;

    public UserEntity(String name, String pw, boolean privileged) {
        this.name = name;
        this.pw = pw;
        this.privileged = privileged;
    }

    public UserEntity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public boolean isPrivileged() {
        return privileged;
    }

    public void setPrivileged(boolean privileged) {
        this.privileged = privileged;
    }
}
