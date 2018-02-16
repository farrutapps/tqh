package com.farrutaps.tqhapp.model;

public class User {

    private String username;
    private Status status;
    private int backHome;

    public User(String username) {
        this.username = username;
        this.status = new Status();
        this.backHome = 0;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Status getStatus() {
        return status;
    }

    public int getBackHome() {
        return backHome;
    }

    public void setBackHome(int backHome) {
        this.backHome = backHome;
    }
}
