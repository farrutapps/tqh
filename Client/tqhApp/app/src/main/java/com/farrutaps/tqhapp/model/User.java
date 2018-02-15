package com.farrutaps.tqhapp.model;

public class User {

    private String username;
    private Status status;
    private int hour;

    public User(String username) {
        this.username = username;
        this.status = new Status();
        this.hour = 0;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Options op, boolean on) {
        status.setStatus(op, on);
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour){
        this.hour = hour;
    }
}
