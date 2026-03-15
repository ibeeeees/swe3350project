package com.companyz.ems.model;

public class Division {
    private int divID;
    private String divName;

    public Division() {}

    public Division(int divID, String divName) {
        this.divID = divID;
        this.divName = divName;
    }

    public int getDivID() { return divID; }
    public void setDivID(int divID) { this.divID = divID; }
    public String getDivName() { return divName; }
    public void setDivName(String divName) { this.divName = divName; }

    @Override
    public String toString() {
        return divName;
    }
}
