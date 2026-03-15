package com.companyz.ems.service;

import com.companyz.ems.dao.PayrollDAO;
import com.companyz.ems.model.Payroll;
import java.util.List;

public class PayrollService {
    private PayrollDAO payrollDAO;

    public PayrollService() {
        this.payrollDAO = new PayrollDAO();
    }

    public List<Payroll> getPayHistory(int empID) {
        return payrollDAO.getByEmpID(empID);
    }

    public double getTotalPayByJobTitle(String jobTitle, int month, int year) {
        return payrollDAO.getTotalPayByJobTitle(jobTitle, month, year);
    }

    public double getTotalPayByDivision(String divName, int month, int year) {
        return payrollDAO.getTotalPayByDivision(divName, month, year);
    }
}
