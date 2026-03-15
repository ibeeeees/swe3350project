package com.companyz.ems.dao;

import com.companyz.ems.model.Payroll;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PayrollDAO {

    private Payroll mapRow(ResultSet rs) throws SQLException {
        Payroll p = new Payroll();
        p.setPayrollID(rs.getInt("payrollID"));
        p.setEmpID(rs.getInt("empID"));
        p.setPayDate(rs.getDate("payDate"));
        p.setGrossPay(rs.getDouble("grossPay"));
        p.setFederalTax(rs.getDouble("federalTax"));
        p.setStateTax(rs.getDouble("stateTax"));
        p.setSocialSecurity(rs.getDouble("socialSecurity"));
        p.setMedicare(rs.getDouble("medicare"));
        p.setRetirement401k(rs.getDouble("retirement401k"));
        p.setHealthInsurance(rs.getDouble("healthInsurance"));
        p.setNetPay(rs.getDouble("netPay"));
        return p;
    }

    public List<Payroll> getByEmpID(int empID) {
        List<Payroll> list = new ArrayList<>();
        String sql = "SELECT * FROM payroll WHERE empID = ? ORDER BY payDate DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, empID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addPayroll(Payroll p) {
        String sql = "INSERT INTO payroll (empID, payDate, grossPay, federalTax, stateTax, " +
                     "socialSecurity, medicare, retirement401k, healthInsurance, netPay) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, p.getEmpID());
            ps.setDate(2, p.getPayDate());
            ps.setDouble(3, p.getGrossPay());
            ps.setDouble(4, p.getFederalTax());
            ps.setDouble(5, p.getStateTax());
            ps.setDouble(6, p.getSocialSecurity());
            ps.setDouble(7, p.getMedicare());
            ps.setDouble(8, p.getRetirement401k());
            ps.setDouble(9, p.getHealthInsurance());
            ps.setDouble(10, p.getNetPay());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public double getTotalPayByJobTitle(String jobTitle, int month, int year) {
        String sql = "SELECT SUM(p.grossPay) AS total FROM payroll p " +
                     "JOIN employee_job_titles ejt ON p.empID = ejt.empID " +
                     "JOIN job_titles jt ON ejt.job_titleID = jt.job_titleID " +
                     "WHERE jt.jobTitle = ? AND MONTH(p.payDate) = ? AND YEAR(p.payDate) = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, jobTitle);
            ps.setInt(2, month);
            ps.setInt(3, year);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public double getTotalPayByDivision(String divName, int month, int year) {
        String sql = "SELECT SUM(p.grossPay) AS total FROM payroll p " +
                     "JOIN employee_division ed ON p.empID = ed.empID " +
                     "JOIN division d ON ed.divID = d.divID " +
                     "WHERE d.divName = ? AND MONTH(p.payDate) = ? AND YEAR(p.payDate) = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, divName);
            ps.setInt(2, month);
            ps.setInt(3, year);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}
