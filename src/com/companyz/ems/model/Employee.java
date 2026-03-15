package com.companyz.ems.model;

import java.sql.Date;

public class Employee {
    private int empID;
    private String firstName;
    private String lastName;
    private String SSN;
    private Date DOB;
    private String email;
    private String phone;
    private String emergencyContactName;
    private String emergencyContactPhone;
    private int addressID;
    private Date hireDate;
    private boolean isActive;

    public Employee() {}

    public Employee(int empID, String firstName, String lastName, String SSN, Date DOB,
                    String email, String phone, String emergencyContactName,
                    String emergencyContactPhone, int addressID, Date hireDate, boolean isActive) {
        this.empID = empID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.SSN = SSN;
        this.DOB = DOB;
        this.email = email;
        this.phone = phone;
        this.emergencyContactName = emergencyContactName;
        this.emergencyContactPhone = emergencyContactPhone;
        this.addressID = addressID;
        this.hireDate = hireDate;
        this.isActive = isActive;
    }

    public int getEmpID() { return empID; }
    public void setEmpID(int empID) { this.empID = empID; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getSSN() { return SSN; }
    public void setSSN(String SSN) { this.SSN = SSN; }
    public Date getDOB() { return DOB; }
    public void setDOB(Date DOB) { this.DOB = DOB; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmergencyContactName() { return emergencyContactName; }
    public void setEmergencyContactName(String emergencyContactName) { this.emergencyContactName = emergencyContactName; }
    public String getEmergencyContactPhone() { return emergencyContactPhone; }
    public void setEmergencyContactPhone(String emergencyContactPhone) { this.emergencyContactPhone = emergencyContactPhone; }
    public int getAddressID() { return addressID; }
    public void setAddressID(int addressID) { this.addressID = addressID; }
    public Date getHireDate() { return hireDate; }
    public void setHireDate(Date hireDate) { this.hireDate = hireDate; }
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    @Override
    public String toString() {
        return "Employee{empID=" + empID + ", name=" + firstName + " " + lastName +
               ", SSN=" + SSN + ", DOB=" + DOB + ", hireDate=" + hireDate + "}";
    }
}
