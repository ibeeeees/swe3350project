package com.companyz.ems.service;

import com.companyz.ems.dao.EmployeeDAO;
import com.companyz.ems.dao.PayrollDAO;
import com.companyz.ems.model.Employee;
import com.companyz.ems.model.Payroll;
import java.sql.Date;
import java.util.List;

public class SalaryService {
    private EmployeeDAO employeeDAO;
    private PayrollDAO payrollDAO;

    public SalaryService() {
        this.employeeDAO = new EmployeeDAO();
        this.payrollDAO = new PayrollDAO();
    }

    public int updateSalariesByPercentage(double percentage, double minSalary, double maxSalary) {
        List<Employee> employees = employeeDAO.getAllEmployees();
        int count = 0;

        for (Employee emp : employees) {
            List<Payroll> payHistory = payrollDAO.getByEmpID(emp.getEmpID());
            if (payHistory.isEmpty()) continue;

            Payroll latest = payHistory.get(0);
            double annualSalary = latest.getGrossPay() * 12;

            if (annualSalary >= minSalary && annualSalary <= maxSalary) {
                double newGrossPay = latest.getGrossPay() * (1 + percentage / 100.0);
                double federalTax = newGrossPay * 0.22;
                double stateTax = newGrossPay * 0.05;
                double socialSecurity = newGrossPay * 0.062;
                double medicare = newGrossPay * 0.0145;
                double retirement401k = newGrossPay * 0.06;
                double healthInsurance = 200.00;
                double netPay = newGrossPay - federalTax - stateTax - socialSecurity
                              - medicare - retirement401k - healthInsurance;

                Payroll newPayroll = new Payroll();
                newPayroll.setEmpID(emp.getEmpID());
                newPayroll.setPayDate(new Date(System.currentTimeMillis()));
                newPayroll.setGrossPay(Math.round(newGrossPay * 100.0) / 100.0);
                newPayroll.setFederalTax(Math.round(federalTax * 100.0) / 100.0);
                newPayroll.setStateTax(Math.round(stateTax * 100.0) / 100.0);
                newPayroll.setSocialSecurity(Math.round(socialSecurity * 100.0) / 100.0);
                newPayroll.setMedicare(Math.round(medicare * 100.0) / 100.0);
                newPayroll.setRetirement401k(Math.round(retirement401k * 100.0) / 100.0);
                newPayroll.setHealthInsurance(healthInsurance);
                newPayroll.setNetPay(Math.round(netPay * 100.0) / 100.0);

                if (payrollDAO.addPayroll(newPayroll)) {
                    count++;
                }
            }
        }
        return count;
    }
}
