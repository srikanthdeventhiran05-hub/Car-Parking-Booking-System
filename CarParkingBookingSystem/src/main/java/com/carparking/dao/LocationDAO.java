package com.carparking.dao;

import com.carparking.model.ParkingLocation;
import com.carparking.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocationDAO {

    public int addLocation(ParkingLocation loc) throws SQLException {
        String sql = "INSERT INTO parking_locations (owner_id, location_name, address, city, total_slots, available_slots, price_per_hour) VALUES (?,?,?,?,?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, loc.getOwnerId());
            ps.setString(2, loc.getLocationName());
            ps.setString(3, loc.getAddress());
            ps.setString(4, loc.getCity());
            ps.setInt(5, loc.getTotalSlots());
            ps.setInt(6, loc.getAvailableSlots());
            ps.setDouble(7, loc.getPricePerHour());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        }
        return -1;
    }

    public boolean deleteLocation(int locationId, int ownerId) throws SQLException {
        String sql = "DELETE FROM parking_locations WHERE location_id=? AND owner_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, locationId);
            ps.setInt(2, ownerId);
            return ps.executeUpdate() > 0;
        }
    }

    public List<ParkingLocation> getAllLocations() throws SQLException {
        List<ParkingLocation> list = new ArrayList<>();
        String sql = "SELECT * FROM parking_locations";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(mapLocation(rs));
        }
        return list;
    }

    public List<ParkingLocation> getLocationsByOwner(int ownerId) throws SQLException {
        List<ParkingLocation> list = new ArrayList<>();
        String sql = "SELECT * FROM parking_locations WHERE owner_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ownerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapLocation(rs));
        }
        return list;
    }

    public List<ParkingLocation> getAvailableByCity(String city) throws SQLException {
        List<ParkingLocation> list = new ArrayList<>();
        String sql = "SELECT * FROM parking_locations WHERE city=? AND available_slots > 0";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, city);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapLocation(rs));
        }
        return list;
    }

    public boolean updateSlots(int locationId, int change) throws SQLException {
        String sql = "UPDATE parking_locations SET available_slots = available_slots + ? WHERE location_id=? AND available_slots + ? >= 0";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, change);
            ps.setInt(2, locationId);
            ps.setInt(3, change);
            return ps.executeUpdate() > 0;
        }
    }

    public ParkingLocation getById(int locationId) throws SQLException {
        String sql = "SELECT * FROM parking_locations WHERE location_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, locationId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapLocation(rs);
        }
        return null;
    }

    private ParkingLocation mapLocation(ResultSet rs) throws SQLException {
        ParkingLocation loc = new ParkingLocation();
        loc.setLocationId(rs.getInt("location_id"));
        loc.setOwnerId(rs.getInt("owner_id"));
        loc.setLocationName(rs.getString("location_name"));
        loc.setAddress(rs.getString("address"));
        loc.setCity(rs.getString("city"));
        loc.setTotalSlots(rs.getInt("total_slots"));
        loc.setAvailableSlots(rs.getInt("available_slots"));
        loc.setPricePerHour(rs.getDouble("price_per_hour"));
        return loc;
    }
}
