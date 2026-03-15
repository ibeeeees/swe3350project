package com.companyz.ems.model;

public class State {
    private int stateID;
    private String stateAbbr;

    public State() {}

    public State(int stateID, String stateAbbr) {
        this.stateID = stateID;
        this.stateAbbr = stateAbbr;
    }

    public int getStateID() { return stateID; }
    public void setStateID(int stateID) { this.stateID = stateID; }
    public String getStateAbbr() { return stateAbbr; }
    public void setStateAbbr(String stateAbbr) { this.stateAbbr = stateAbbr; }

    @Override
    public String toString() {
        return stateAbbr;
    }
}
