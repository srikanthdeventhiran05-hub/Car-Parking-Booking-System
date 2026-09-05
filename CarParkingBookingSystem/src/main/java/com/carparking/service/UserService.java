package com.carparking.service;

import com.carparking.dao.UserDAO;
import com.carparking.model.User;

import java.sql.SQLException;
import java.util.List;

public class UserService {
    private final UserDAO userDAO = new UserDAO();

    public int register(User user) throws SQLException {
        return userDAO.register(user);
    }

    public User login(String email, String password) throws SQLException {
        return userDAO.login(email, password);
    }

    public List<User> getAllUsers() throws SQLException {
        return userDAO.getAllUsers();
    }

    public List<User> getUsersByRole(String role) throws SQLException {
        return userDAO.getUsersByRole(role);
    }
}
