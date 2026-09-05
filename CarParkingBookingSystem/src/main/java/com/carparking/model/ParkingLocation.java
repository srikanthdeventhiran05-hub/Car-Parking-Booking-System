package com.carparking.model;

public class ParkingLocation {
    private int locationId;
    private int ownerId;
    private String locationName;
    private String address;
    private String city;
    private int totalSlots;
    private int availableSlots;
    private double pricePerHour;

    public ParkingLocation() {}

    public ParkingLocation(int ownerId, String locationName, String address, String city, int totalSlots, double pricePerHour) {
        this.ownerId = ownerId;
        this.locationName = locationName;
        this.address = address;
        this.city = city;
        this.totalSlots = totalSlots;
        this.availableSlots = totalSlots;
        this.pricePerHour = pricePerHour;
    }

    public int getLocationId() { return locationId; }
    public void setLocationId(int locationId) { this.locationId = locationId; }
    public int getOwnerId() { return ownerId; }
    public void setOwnerId(int ownerId) { this.ownerId = ownerId; }
    public String getLocationName() { return locationName; }
    public void setLocationName(String locationName) { this.locationName = locationName; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public int getTotalSlots() { return totalSlots; }
    public void setTotalSlots(int totalSlots) { this.totalSlots = totalSlots; }
    public int getAvailableSlots() { return availableSlots; }
    public void setAvailableSlots(int availableSlots) { this.availableSlots = availableSlots; }
    public double getPricePerHour() { return pricePerHour; }
    public void setPricePerHour(double pricePerHour) { this.pricePerHour = pricePerHour; }

    @Override
    public String toString() {
        return "ID:" + locationId + " | " + locationName + " | " + city +
               " | Slots:" + availableSlots + "/" + totalSlots + " | Rs." + pricePerHour + "/hr";
    }
}
