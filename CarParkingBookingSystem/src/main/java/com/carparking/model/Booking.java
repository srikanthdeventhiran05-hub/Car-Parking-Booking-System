package com.carparking.model;

import java.sql.Timestamp;

public class Booking {
    private int bookingId;
    private int userId;
    private String ownerName;
    private String ownerPhone;
    private String ownerNative;
    private String vehicleNumber;
    private int locationId;
    private Timestamp bookingTime;
    private double totalCost;
    private String status;

    public Booking() {}

    public Booking(int userId, String ownerName, String ownerPhone, String ownerNative, String vehicleNumber, int locationId) {
        this.userId = userId;
        this.ownerName = ownerName;
        this.ownerPhone = ownerPhone;
        this.ownerNative = ownerNative;
        this.vehicleNumber = vehicleNumber;
        this.locationId = locationId;
        this.status = "ACTIVE";
    }

    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }
    public String getOwnerPhone() { return ownerPhone; }
    public void setOwnerPhone(String ownerPhone) { this.ownerPhone = ownerPhone; }
    public String getOwnerNative() { return ownerNative; }
    public void setOwnerNative(String ownerNative) { this.ownerNative = ownerNative; }
    public String getVehicleNumber() { return vehicleNumber; }
    public void setVehicleNumber(String vehicleNumber) { this.vehicleNumber = vehicleNumber; }
    public int getLocationId() { return locationId; }
    public void setLocationId(int locationId) { this.locationId = locationId; }
    public Timestamp getBookingTime() { return bookingTime; }
    public void setBookingTime(Timestamp bookingTime) { this.bookingTime = bookingTime; }
    public double getTotalCost() { return totalCost; }
    public void setTotalCost(double totalCost) { this.totalCost = totalCost; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "BookingID:" + bookingId + " | Owner:" + ownerName + " | Phone:" + ownerPhone +
               " | Native:" + ownerNative + " | Vehicle:" + vehicleNumber +
               " | LocationID:" + locationId + " | Rs." + totalCost + " | " + status;
    }
}
