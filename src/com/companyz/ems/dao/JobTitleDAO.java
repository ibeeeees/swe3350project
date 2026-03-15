package com.companyz.ems.dao;

import com.companyz.ems.model.JobTitle;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JobTitleDAO {

    public List<JobTitle> getAllJobTitles() {
        List<JobTitle> list = new ArrayList<>();
        String sql = "SELECT * FROM job_titles ORDER BY jobTitle";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new JobTitle(rs.getInt("job_titleID"), rs.getString("jobTitle")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public JobTitle getByID(int jobTitleID) {
        String sql = "SELECT * FROM job_titles WHERE job_titleID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, jobTitleID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new JobTitle(rs.getInt("job_titleID"), rs.getString("jobTitle"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getJobTitleIDForEmployee(int empID) {
        String sql = "SELECT job_titleID FROM employee_job_titles WHERE empID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, empID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("job_titleID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean setEmployeeJobTitle(int empID, int jobTitleID) {
        String deleteSql = "DELETE FROM employee_job_titles WHERE empID = ?";
        String insertSql = "INSERT INTO employee_job_titles (empID, job_titleID) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(deleteSql)) {
                ps.setInt(1, empID);
                ps.executeUpdate();
            }
            try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
                ps.setInt(1, empID);
                ps.setInt(2, jobTitleID);
                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
