# 🚗 Park Smart – Car Parking Booking System

> **Java • JDBC • MySQL • Maven • Layered Architecture**

A Java-based **Car Parking Booking System** that helps vehicle owners find available parking spaces, book parking slots, and release them after use. Parking owners can manage their parking locations, while administrators can monitor users, locations, and bookings.

---

## 📖 Project Synopsis

The **Car Parking Booking System** is designed to simplify and automate the process of finding and reserving parking spaces. Manually searching for parking can be time-consuming and may lead to booking conflicts or difficulties in maintaining reservation records.

This system provides a structured solution where users can **search parking locations by city, check available slots, book parking spaces, and release them after use**. Parking owners can add and manage their parking locations and specify the price per hour.

The system supports three different roles:

- 🔴 **Admin** – Monitors and manages the complete system
- 🟠 **Parking Owner** – Manages parking locations and prices
- 🟢 **Vehicle Owner/User** – Searches, books, and releases parking slots

The application is developed using **Java, JDBC, MySQL, and Maven** and follows a **Layered Architecture** consisting of Model, DAO, Service, and Presentation/Application layers.

---

## 🎯 Problem Statement

Manually locating and reserving available parking spaces can be time-consuming and inconvenient. It may also result in:

- Booking conflicts
- Difficulty finding available parking
- Manual maintenance of reservation records
- Difficulty managing parking locations
- Incorrect manual fee calculations
- Lack of centralized parking information

The proposed system provides an organized and database-driven solution for parking management.

---

# ✨ Features

## 🔐 1. User Registration & Login

- New users can register
- Users can login using email and password
- Role-based access is provided
- Supports:
  - ADMIN
  - OWNER
  - USER

---

## 👨‍💼 2. Admin Features

The administrator has complete monitoring access.

### Admin can:

- View all users
- View vehicle owners
- View parking owners
- View all parking locations
- View all bookings
- Monitor system activities
- Logout from the system

---

## 🅿️ 3. Parking Owner Features

Parking owners can manage their parking facilities.

### Owner can:

- Add parking location
- View own parking locations
- Delete parking location
- Set parking price per hour
- View bookings made at their locations
- Manage total and available parking slots

---

## 🚗 4. Vehicle Owner Features

Vehicle owners can find and reserve parking spaces.

### User can:

- Search parking by city
- View available parking locations
- Check available slots
- Enter vehicle details
- Book a parking slot
- View personal bookings
- Release parking
- Automatically calculate parking cost

---

## 💰 5. Automatic Cost Calculation

The system calculates the parking cost based on parking duration.

```text
Parking Cost = Parking Hours × Price Per Hour
```

### Example

```text
Price = ₹25/hour
Duration = 3 hours

Total Cost = 25 × 3
           = ₹75
```

---

## 📊 6. Parking Slot Management

When a user books a parking slot:

```text
Available Slots = Available Slots - 1
```

When the user releases the parking:

```text
Available Slots = Available Slots + 1
```

This helps maintain the current availability of parking locations.

---

# 🏗️ System Architecture

The project follows a **Layered Architecture**.

```text
                 ┌──────────────────────┐
                 │       App.java       │
                 │ Presentation Layer  │
                 └──────────┬───────────┘
                            │
                 ┌──────────▼───────────┐
                 │    Service Layer     │
                 │                      │
                 │ UserService          │
                 │ BookingService       │
                 │ LocationService      │
                 └──────────┬───────────┘
                            │
                 ┌──────────▼───────────┐
                 │      DAO Layer       │
                 │                      │
                 │ UserDAO              │
                 │ BookingDAO           │
                 │ LocationDAO          │
                 └──────────┬───────────┘
                            │
                 ┌──────────▼───────────┐
                 │    MySQL Database    │
                 └──────────────────────┘
```

---

# 🛠️ Technologies Used

| Technology | Purpose |
|---|---|
| ☕ Java | Application development |
| 🔌 JDBC | Java–MySQL connectivity |
| 🐬 MySQL | Database management |
| 📦 Maven | Dependency management |
| 🏗️ Layered Architecture | Code organization |
| 💻 IntelliJ IDEA | Development environment |

---

# 📁 Project Structure

```text
CarParkingBookingSystem/
│
├── pom.xml
├── README.md
│
└── src/
    └── main/
        └── java/
            └── com/
                └── carparking/
                    │
                    ├── App.java
                    │
                    ├── model/
                    │   ├── User.java
                    │   ├── Booking.java
                    │   └── ParkingLocation.java
                    │
                    ├── dao/
                    │   ├── UserDAO.java
                    │   ├── BookingDAO.java
                    │   └── LocationDAO.java
                    │
                    ├── service/
                    │   ├── UserService.java
                    │   ├── BookingService.java
                    │   └── LocationService.java
                    │
                    └── util/
                        └── DBConnection.java
```

---

# 🗄️ Database

Database name:

```sql
carparking_db
```

The system contains three main tables:

```text
users
    ↓
parking_locations
    ↓
bookings
```

### Database Relationships

```text
users
  │
  ├───────────────┐
  │               │
  ▼               ▼
parking_locations  bookings
       │             │
       └─────────────┘
```

---

# 💾 MySQL Database Setup

Copy and execute the following SQL in **MySQL**.

```sql
CREATE DATABASE carparking_db;

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

    FOREIGN KEY (owner_id)
    REFERENCES users(user_id)
    ON DELETE CASCADE
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

    FOREIGN KEY (user_id)
    REFERENCES users(user_id)
    ON DELETE CASCADE,

    FOREIGN KEY (location_id)
    REFERENCES parking_locations(location_id)
    ON DELETE CASCADE
);
```

---

# 👤 Sample Users

```sql
INSERT INTO users
(name, email, password, phone, native_place, role)
VALUES
('Admin', 'admin@park.com', 'admin123',
 '9999999999', 'Chennai', 'ADMIN'),

('srikanth', 'sri@park.com', 'sri123',
 '8888888888', 'Mumbai', 'OWNER'),

('mohan', 'mohan@park.com', 'mohan123',
 '7777777777', 'Delhi', 'USER');
```

---

# 🅿️ Sample Parking Locations

```sql
INSERT INTO parking_locations
(owner_id, location_name, address, city,
 total_slots, available_slots, price_per_hour)
VALUES

(2, 'City Center Parking',
 '123 Main Road', 'Chennai',
 50, 45, 25.00),

(2, 'Mall Parking Zone',
 '45 Park Street', 'Chennai',
 100, 85, 30.00),

(2, 'Airport Parking',
 'Airport Road', 'Mumbai',
 200, 190, 50.00),

(2, 'Beach Road Parking',
 'Marine Drive', 'Mumbai',
 80, 70, 20.00);
```

---

# 🔍 View Database Records

### View Users

```sql
SELECT * FROM users;
```

### View Parking Locations

```sql
SELECT * FROM parking_locations;
```

### View Bookings

```sql
SELECT * FROM bookings;
```

---

# 🔄 Booking Workflow

```text
       ┌───────────────┐
       │     Login     │
       └───────┬───────┘
               ↓
       ┌───────────────┐
       │ Search City   │
       └───────┬───────┘
               ↓
       ┌───────────────┐
       │Pick Location  │
       └───────┬───────┘
               ↓
       ┌───────────────┐
       │ Enter Details │
       └───────┬───────┘
               ↓
       ┌───────────────┐
       │  Book Slot    │
       │ Available - 1 │
       └───────┬───────┘
               ↓
       ┌───────────────┐
       │ Release Slot  │
       │ Available + 1 │
       └───────┬───────┘
               ↓
       ┌───────────────┐
       │ Calculate Cost│
       └───────────────┘
```

---

# 🔌 JDBC Workflow

The Java application communicates with MySQL using JDBC.

```text
Java Application
       ↓
MySQL JDBC Driver
       ↓
DriverManager
       ↓
Database Connection
       ↓
PreparedStatement
       ↓
executeQuery / executeUpdate
       ↓
MySQL Database
```

### Main JDBC Steps

1. Load JDBC Driver
2. Create database connection
3. Create `PreparedStatement`
4. Execute SQL query/update
5. Process result
6. Close resources

---

# 🔒 Security

The project uses `PreparedStatement` for database operations.

Benefits:

- Helps prevent SQL injection
- Separates SQL from user input
- Provides safer database operations

> **Note:** This is an educational project. In a production system, passwords should be securely hashed rather than stored as plain text.

---

# ⚙️ Installation & Setup

## 1. Clone Repository

```bash
git clone https://github.com/YOUR-USERNAME/CarParkingBookingSystem.git
```

```bash
cd CarParkingBookingSystem
```

## 2. Create Database

Open MySQL and execute the SQL provided in the **Database Setup** section.

## 3. Configure Database Connection

Open:

```text
src/main/java/com/carparking/util/DBConnection.java
```

Configure:

```java
private static final String URL =
        "jdbc:mysql://localhost:3306/carparking_db";

private static final String USER = "root";

private static final String PASSWORD =
        "YOUR_MYSQL_PASSWORD";
```

Replace `YOUR_MYSQL_PASSWORD` with your MySQL password.

## 4. Install Maven Dependencies

```bash
mvn clean install
```

## 5. Run Application

Run:

```text
App.java
```

from IntelliJ IDEA.

---

# 🖥️ Main Menu

```text
==========================================
       CAR PARKING BOOKING SYSTEM
==========================================
  1. Register
  2. Login
  3. Exit
==========================================
```

---

# 👨‍💼 Admin Menu

```text
1. View All Users
2. View Vehicle Owners
3. View Parking Owners
4. View All Locations
5. View All Bookings
6. Logout
```

---

# 🅿️ Parking Owner Menu

```text
1. Add Parking Location
2. View My Locations
3. Delete Parking Location
4. View Bookings on My Locations
5. Logout
```

---

# 🚗 Vehicle Owner Menu

```text
1. Search Parking by City
2. Book Parking Slot
3. Release Parking
4. View My Bookings
5. Logout
```

---

# 📸 Screenshots

Add your project screenshots here:

```text
docs/
│
├── login.png
├── admin-menu.png
├── owner-menu.png
├── user-menu.png
├── parking-search.png
└── booking-success.png
```

Then add them to the README:

```markdown
![Login Screen](docs/login.png)

![Admin Menu](docs/admin-menu.png)

![Booking Screen](docs/booking-success.png)
```

---

# 🚀 Future Enhancements

The following features can be added in future versions:

- 💳 Online payment integration
- 📱 Dedicated mobile application
- 📍 Google Maps integration
- 📷 QR-code-based parking entry
- ⚡ Real-time parking slot availability
- 📧 Email notifications
- 📱 SMS reservation notifications
- 📊 Administrative dashboard
- 📈 Reports and analytics

---

# 🎯 Advantages

- ✅ Easy parking search
- ✅ Simple booking process
- ✅ Role-based access
- ✅ Centralized database
- ✅ Automatic slot management
- ✅ Automatic cost calculation
- ✅ Reduced manual work
- ✅ Organized booking records
- ✅ Clean layered architecture
- ✅ Reusable service and DAO layers

---

# 📚 Learning Outcomes

This project helped us understand:

- Java
- Object-Oriented Programming
- JDBC
- MySQL
- SQL
- CRUD operations
- Maven
- Layered Architecture
- DAO Pattern
- Service Layer
- Exception Handling
- Database Relationships
- Git & GitHub

---

# 👨‍💻 Developers

### Srikanth D.

**Project:** Park Smart – Car Parking Booking System

**Technology:** Java + JDBC + MySQL

---

# 📄 License

This project was developed for **educational and academic purposes**.

---

## ⭐ If you like this project

Please give this repository a ⭐ on GitHub!

**Park Smart 🚗 | Book Smart 🅿️ | Park Easy**
