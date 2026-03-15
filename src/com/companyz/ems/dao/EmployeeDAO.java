package com.companyz.ems.dao;

import com.companyz.ems.model.Employee;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    private Employee mapRow(ResultSet rs) throws SQLException {
        Employee emp = new Employee();
        emp.setEmpID(rs.getInt("empID"));
        emp.setFirstName(rs.getString("firstName"));
        emp.setLastName(rs.getString("lastName"));
        emp.setSSN(rs.getString("SSN"));
        emp.setDOB(rs.getDate("DOB"));
        emp.setEmail(rs.getString("email"));
        emp.setPhone(rs.getString("phone"));
        emp.setEmergencyContactName(rs.getString("emergencyContactName"));
        emp.setEmergencyContactPhone(rs.getString("emergencyContactPhone"));
        emp.setAddressID(rs.getInt("addressID"));
        emp.setHireDate(rs.getDate("hireDate"));
        emp.setActive(rs.getBoolean("isActive"));
        return emp;
    }

    public List<Employee> searchByName(String name) {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM employees WHERE firstName LIKE ? OR lastName LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + name + "%");
            ps.setString(2, "%" + name + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Employee searchByEmpID(int empID) {
        String sql = "SELECT * FROM employees WHERE empID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, empID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Employee searchBySSN(String ssn) {
        String sql = "SELECT * FROM employees WHERE SSN = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ssn);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Employee> searchByDOB(Date dob) {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM employees WHERE DOB = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, dob);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addEmployee(Employee emp) {
        String sql = "INSERT INTO employees (firstName, lastName, SSN, DOB, email, phone, " +
                     "emergencyContactName, emergencyContactPhone, addressID, hireDate, isActive) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, emp.getFirstName());
            ps.setString(2, emp.getLastName());
            ps.setString(3, emp.getSSN());
            ps.setDate(4, emp.getDOB());
            ps.setString(5, emp.getEmail());
            ps.setString(6, emp.getPhone());
            ps.setString(7, emp.getEmergencyContactName());
            ps.setString(8, emp.getEmergencyContactPhone());
            ps.setInt(9, emp.getAddressID());
            ps.setDate(10, emp.getHireDate());
            ps.setBoolean(11, emp.isActive());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                ResultSet keys = ps.getGeneratedKeys();
                if (keys.next()) {
                    emp.setEmpID(keys.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateEmployee(Employee emp) {
        String sql = "UPDATE employees SET firstName=?, lastName=?, SSN=?, DOB=?, email=?, phone=?, " +
                     "emergencyContactName=?, emergencyContactPhone=?, addressID=?, hireDate=?, isActive=? " +
                     "WHERE empID=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, emp.getFirstName());
            ps.setString(2, emp.getLastName());
            ps.setString(3, emp.getSSN());
            ps.setDate(4, emp.getDOB());
            ps.setString(5, emp.getEmail());
            ps.setString(6, emp.getPhone());
            ps.setString(7, emp.getEmergencyContactName());
            ps.setString(8, emp.getEmergencyContactPhone());
            ps.setInt(9, emp.getAddressID());
            ps.setDate(10, emp.getHireDate());
            ps.setBoolean(11, emp.isActive());
            ps.setInt(12, emp.getEmpID());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteEmployee(int empID) {
        String sql = "DELETE FROM employees WHERE empID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, empID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Employee> getAllEmployees() {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM employees";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Employee> getNewHires(Date startDate, Date endDate) {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM employees WHERE hireDate BETWEEN ? AND ? ORDER BY hireDate DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, startDate);
            ps.setDate(2, endDate);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
