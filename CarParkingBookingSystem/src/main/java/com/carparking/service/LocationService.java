package com.carparking.service;

import com.carparking.dao.LocationDAO;
import com.carparking.model.ParkingLocation;

import java.sql.SQLException;
import java.util.List;

public class LocationService {
    private final LocationDAO locationDAO = new LocationDAO();

    public int addLocation(ParkingLocation loc) throws SQLException {
        return locationDAO.addLocation(loc);
    }

    public boolean deleteLocation(int locationId, int ownerId) throws SQLException {
        return locationDAO.deleteLocation(locationId, ownerId);
    }

    public List<ParkingLocation> getAllLocations() throws SQLException {
        return locationDAO.getAllLocations();
    }

    public List<ParkingLocation> getLocationsByOwner(int ownerId) throws SQLException {
        return locationDAO.getLocationsByOwner(ownerId);
    }

    public List<ParkingLocation> getAvailableByCity(String city) throws SQLException {
        return locationDAO.getAvailableByCity(city);
    }

    public boolean updateSlots(int locationId, int change) throws SQLException {
        return locationDAO.updateSlots(locationId, change);
    }

    public ParkingLocation getById(int locationId) throws SQLException {
        return locationDAO.getById(locationId);
    }
}
