package com.companyz.ems.dao;

import com.companyz.ems.model.Division;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DivisionDAO {

    public List<Division> getAllDivisions() {
        List<Division> list = new ArrayList<>();
        String sql = "SELECT * FROM division ORDER BY divName";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Division(rs.getInt("divID"), rs.getString("divName")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Division getByID(int divID) {
        String sql = "SELECT * FROM division WHERE divID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, divID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Division(rs.getInt("divID"), rs.getString("divName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getDivisionIDForEmployee(int empID) {
        String sql = "SELECT divID FROM employee_division WHERE empID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, empID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("divID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean setEmployeeDivision(int empID, int divID) {
        String deleteSql = "DELETE FROM employee_division WHERE empID = ?";
        String insertSql = "INSERT INTO employee_division (empID, divID) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(deleteSql)) {
                ps.setInt(1, empID);
                ps.executeUpdate();
            }
            try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
                ps.setInt(1, empID);
                ps.setInt(2, divID);
                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
