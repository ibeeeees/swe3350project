package com.companyz.ems.model;

public class JobTitle {
    private int jobTitleID;
    private String jobTitle;

    public JobTitle() {}

    public JobTitle(int jobTitleID, String jobTitle) {
        this.jobTitleID = jobTitleID;
        this.jobTitle = jobTitle;
    }

    public int getJobTitleID() { return jobTitleID; }
    public void setJobTitleID(int jobTitleID) { this.jobTitleID = jobTitleID; }
    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }

    @Override
    public String toString() {
        return jobTitle;
    }
}
