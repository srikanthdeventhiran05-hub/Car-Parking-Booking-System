package com.carparking.dao;

import com.carparking.model.Booking;
import com.carparking.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    public int createBooking(Booking booking) throws SQLException {
        String sql = "INSERT INTO bookings (user_id, owner_name, owner_phone, owner_native, vehicle_number, location_id) VALUES (?,?,?,?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, booking.getUserId());
            ps.setString(2, booking.getOwnerName());
            ps.setString(3, booking.getOwnerPhone());
            ps.setString(4, booking.getOwnerNative());
            ps.setString(5, booking.getVehicleNumber());
            ps.setInt(6, booking.getLocationId());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        }
        return -1;
    }

    public boolean releaseBooking(int bookingId, double cost) throws SQLException {
        String sql = "UPDATE bookings SET total_cost=?, status='RELEASED' WHERE booking_id=? AND status='ACTIVE'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, cost);
            ps.setInt(2, bookingId);
            return ps.executeUpdate() > 0;
        }
    }

    public List<Booking> getAllBookings() throws SQLException {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM bookings";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(mapBooking(rs));
        }
        return list;
    }

    public List<Booking> getBookingsByUser(int userId) throws SQLException {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE user_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapBooking(rs));
        }
        return list;
    }

    private Booking mapBooking(ResultSet rs) throws SQLException {
        Booking b = new Booking();
        b.setBookingId(rs.getInt("booking_id"));
        b.setUserId(rs.getInt("user_id"));
        b.setOwnerName(rs.getString("owner_name"));
        b.setOwnerPhone(rs.getString("owner_phone"));
        b.setOwnerNative(rs.getString("owner_native"));
        b.setVehicleNumber(rs.getString("vehicle_number"));
        b.setLocationId(rs.getInt("location_id"));
        b.setBookingTime(rs.getTimestamp("booking_time"));
        b.setTotalCost(rs.getDouble("total_cost"));
        b.setStatus(rs.getString("status"));
        return b;
    }
}
