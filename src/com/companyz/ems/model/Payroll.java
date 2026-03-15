package com.companyz.ems.model;

import java.sql.Date;

public class Payroll {
    private int payrollID;
    private int empID;
    private Date payDate;
    private double grossPay;
    private double federalTax;
    private double stateTax;
    private double socialSecurity;
    private double medicare;
    private double retirement401k;
    private double healthInsurance;
    private double netPay;

    public Payroll() {}

    public Payroll(int payrollID, int empID, Date payDate, double grossPay,
                   double federalTax, double stateTax, double socialSecurity,
                   double medicare, double retirement401k, double healthInsurance, double netPay) {
        this.payrollID = payrollID;
        this.empID = empID;
        this.payDate = payDate;
        this.grossPay = grossPay;
        this.federalTax = federalTax;
        this.stateTax = stateTax;
        this.socialSecurity = socialSecurity;
        this.medicare = medicare;
        this.retirement401k = retirement401k;
        this.healthInsurance = healthInsurance;
        this.netPay = netPay;
    }

    public int getPayrollID() { return payrollID; }
    public void setPayrollID(int payrollID) { this.payrollID = payrollID; }
    public int getEmpID() { return empID; }
    public void setEmpID(int empID) { this.empID = empID; }
    public Date getPayDate() { return payDate; }
    public void setPayDate(Date payDate) { this.payDate = payDate; }
    public double getGrossPay() { return grossPay; }
    public void setGrossPay(double grossPay) { this.grossPay = grossPay; }
    public double getFederalTax() { return federalTax; }
    public void setFederalTax(double federalTax) { this.federalTax = federalTax; }
    public double getStateTax() { return stateTax; }
    public void setStateTax(double stateTax) { this.stateTax = stateTax; }
    public double getSocialSecurity() { return socialSecurity; }
    public void setSocialSecurity(double socialSecurity) { this.socialSecurity = socialSecurity; }
    public double getMedicare() { return medicare; }
    public void setMedicare(double medicare) { this.medicare = medicare; }
    public double getRetirement401k() { return retirement401k; }
    public void setRetirement401k(double retirement401k) { this.retirement401k = retirement401k; }
    public double getHealthInsurance() { return healthInsurance; }
    public void setHealthInsurance(double healthInsurance) { this.healthInsurance = healthInsurance; }
    public double getNetPay() { return netPay; }
    public void setNetPay(double netPay) { this.netPay = netPay; }

    @Override
    public String toString() {
        return "Payroll{payrollID=" + payrollID + ", empID=" + empID +
               ", payDate=" + payDate + ", grossPay=" + grossPay + ", netPay=" + netPay + "}";
    }
}
