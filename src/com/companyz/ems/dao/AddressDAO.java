package com.companyz.ems.dao;

import com.companyz.ems.model.Address;
import com.companyz.ems.model.City;
import com.companyz.ems.model.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddressDAO {

    public Address getByID(int addressID) {
        String sql = "SELECT * FROM addresses WHERE addressID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, addressID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Address addr = new Address();
                addr.setAddressID(rs.getInt("addressID"));
                addr.setStreet(rs.getString("street"));
                addr.setCityID(rs.getInt("cityID"));
                addr.setStateID(rs.getInt("stateID"));
                addr.setZip(rs.getString("zip"));
                return addr;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int addAddress(Address addr) {
        String sql = "INSERT INTO addresses (street, cityID, stateID, zip) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, addr.getStreet());
            ps.setInt(2, addr.getCityID());
            ps.setInt(3, addr.getStateID());
            ps.setString(4, addr.getZip());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                ResultSet keys = ps.getGeneratedKeys();
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean updateAddress(Address addr) {
        String sql = "UPDATE addresses SET street=?, cityID=?, stateID=?, zip=? WHERE addressID=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, addr.getStreet());
            ps.setInt(2, addr.getCityID());
            ps.setInt(3, addr.getStateID());
            ps.setString(4, addr.getZip());
            ps.setInt(5, addr.getAddressID());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<City> getAllCities() {
        List<City> list = new ArrayList<>();
        String sql = "SELECT * FROM cities ORDER BY cityName";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new City(rs.getInt("cityID"), rs.getString("cityName")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<State> getAllStates() {
        List<State> list = new ArrayList<>();
        String sql = "SELECT * FROM states ORDER BY stateAbbr";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new State(rs.getInt("stateID"), rs.getString("stateAbbr")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
