package com.carparking.service;

import com.carparking.dao.BookingDAO;
import com.carparking.model.Booking;

import java.sql.SQLException;
import java.util.List;

public class BookingService {
    private final BookingDAO bookingDAO = new BookingDAO();

    public int createBooking(Booking booking) throws SQLException {
        return bookingDAO.createBooking(booking);
    }

    public boolean releaseBooking(int bookingId, double cost) throws SQLException {
        return bookingDAO.releaseBooking(bookingId, cost);
    }

    public List<Booking> getAllBookings() throws SQLException {
        return bookingDAO.getAllBookings();
    }

    public List<Booking> getBookingsByUser(int userId) throws SQLException {
        return bookingDAO.getBookingsByUser(userId);
    }
}
