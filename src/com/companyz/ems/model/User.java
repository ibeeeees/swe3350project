package com.companyz.ems.model;

public class User {
    private int userID;
    private int empID;
    private String username;
    private String passwordHash;
    private String role;

    public User() {}

    public User(int userID, int empID, String username, String passwordHash, String role) {
        this.userID = userID;
        this.empID = empID;
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    public int getUserID() { return userID; }
    public void setUserID(int userID) { this.userID = userID; }
    public int getEmpID() { return empID; }
    public void setEmpID(int empID) { this.empID = empID; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    @Override
    public String toString() {
        return "User{userID=" + userID + ", username=" + username + ", role=" + role + "}";
    }
}
