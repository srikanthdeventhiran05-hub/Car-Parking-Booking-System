package com.carparking.model;

public class User {
    private int userId;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String nativePlace;
    private String role;

    public User() {}

    public User(String name, String email, String password, String phone, String nativePlace, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.nativePlace = nativePlace;
        this.role = role;
    }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getNativePlace() { return nativePlace; }
    public void setNativePlace(String nativePlace) { this.nativePlace = nativePlace; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    @Override
    public String toString() {
        return "ID:" + userId + " | Name:" + name + " | Email:" + email +
               " | Phone:" + phone + " | Native:" + nativePlace + " | Role:" + role;
    }
}
