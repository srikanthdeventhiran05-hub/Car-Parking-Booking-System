CREATE DATABASE IF NOT EXISTS carparking_db;
USE carparking_db;

CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    phone VARCHAR(15),
    native_place VARCHAR(50),
    role VARCHAR(10) NOT NULL DEFAULT 'USER'
);

CREATE TABLE parking_locations (
    location_id INT AUTO_INCREMENT PRIMARY KEY,
    owner_id INT NOT NULL,
    location_name VARCHAR(100) NOT NULL,
    address VARCHAR(200) NOT NULL,
    city VARCHAR(50) NOT NULL,
    total_slots INT NOT NULL,
    available_slots INT NOT NULL,
    price_per_hour DECIMAL(7,2) NOT NULL,
    FOREIGN KEY (owner_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE bookings (
    booking_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    owner_name VARCHAR(50) NOT NULL,
    owner_phone VARCHAR(15) NOT NULL,
    owner_native VARCHAR(50) NOT NULL,
    vehicle_number VARCHAR(20) NOT NULL,
    location_id INT NOT NULL,
    booking_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_cost DECIMAL(10,2) DEFAULT 0.00,
    status VARCHAR(15) DEFAULT 'ACTIVE',
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (location_id) REFERENCES parking_locations(location_id) ON DELETE CASCADE
);

INSERT INTO users (name, email, password, phone, native_place, role) VALUES
('Admin', 'admin@park.com', 'admin123', '9999999999', 'Chennai', 'ADMIN'),
('Ravi Kumar', 'ravi@park.com', 'ravi123', '8888888888', 'Mumbai', 'OWNER'),
('Priya Singh', 'priya@park.com', 'priya123', '7777777777', 'Delhi', 'USER');

INSERT INTO parking_locations (owner_id, location_name, address, city, total_slots, available_slots, price_per_hour) VALUES
(2, 'City Center Parking', '123 Main Road', 'Chennai', 50, 45, 25.00),
(2, 'Mall Parking Zone', '45 Park Street', 'Chennai', 100, 85, 30.00),
(2, 'Airport Parking', 'Airport Road', 'Mumbai', 200, 190, 50.00),
(2, 'Beach Road Parking', 'Marine Drive', 'Mumbai', 80, 70, 20.00);
