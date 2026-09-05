package com.carparking;

import com.carparking.model.Booking;
import com.carparking.model.ParkingLocation;
import com.carparking.model.User;
import com.carparking.service.BookingService;
import com.carparking.service.LocationService;
import com.carparking.service.UserService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class App {

    private static final Scanner sc = new Scanner(System.in);
    private static final UserService userService = new UserService();
    private static final LocationService locationService = new LocationService();
    private static final BookingService bookingService = new BookingService();

    public static void main(String[] args) {
        while (true) {
            line();
            System.out.println("       CAR PARKING BOOKING SYSTEM");
            line();
            System.out.println("  1. Register");
            System.out.println("  2. Login");
            System.out.println("  3. Exit");
            line();
            System.out.print("  Enter Choice: ");
            switch (readInt()) {
                case 1 -> register();
                case 2 -> login();
                case 3 -> { System.out.println("\n  Goodbye!\n"); return; }
                default -> System.out.println("  Invalid choice!");
            }
        }
    }

    private static void register() {
        try {
            line();
            System.out.println("       REGISTER");
            line();
            System.out.print("  Name          : "); String name = sc.nextLine();
            System.out.print("  Email         : "); String email = sc.nextLine();
            System.out.print("  Password      : "); String pass = sc.nextLine();
            System.out.print("  Phone         : "); String phone = sc.nextLine();
            System.out.print("  Native Place  : "); String nativePlace = sc.nextLine();
            System.out.print("  Role (ADMIN/OWNER/USER): "); String role = sc.nextLine().toUpperCase().trim();

            if (!role.equals("ADMIN") && !role.equals("OWNER") && !role.equals("USER")) {
                System.out.println("  Invalid role!"); return;
            }
            User user = new User(name, email, pass, phone, nativePlace, role);
            int id = userService.register(user);
            System.out.println("\n  Registered! Your ID: " + id);
        } catch (SQLException e) { System.out.println("  Error: " + e.getMessage()); }
    }

    private static void login() {
        try {
            line();
            System.out.println("       LOGIN");
            line();
            System.out.print("  Email    : "); String email = sc.nextLine();
            System.out.print("  Password : "); String pass = sc.nextLine();
            User user = userService.login(email, pass);
            if (user == null) { System.out.println("  Invalid credentials!"); return; }
            System.out.println("\n  Welcome, " + user.getName() + "! (" + user.getRole() + ")");
            switch (user.getRole()) {
                case "ADMIN" -> adminMenu(user);
                case "OWNER" -> ownerMenu(user);
                case "USER" -> userMenu(user);
            }
        } catch (SQLException e) { System.out.println("  Error: " + e.getMessage()); }
    }

    // ====================== ADMIN ======================
    private static void adminMenu(User admin) {
        while (true) {
            line();
            System.out.println("       ADMIN MENU - " + admin.getName());
            line();
            System.out.println("  1. View All Users");
            System.out.println("  2. View Vehicle Owners");
            System.out.println("  3. View Parking Owners");
            System.out.println("  4. View All Locations");
            System.out.println("  5. View All Bookings");
            System.out.println("  6. Logout");
            line();
            System.out.print("  Enter Choice: ");

            switch (readInt()) {
                case 1 -> {
                    try {
                        showList("ALL USERS", userService.getAllUsers());
                    } catch (SQLException e) {
                        System.out.println("  Error: " + e.getMessage());
                    }
                }

                case 2 -> {
                    try {
                        showList("VEHICLE OWNERS",
                                userService.getUsersByRole("USER"));
                    } catch (SQLException e) {
                        System.out.println("  Error: " + e.getMessage());
                    }
                }

                case 3 -> {
                    try {
                        showList("PARKING OWNERS",
                                userService.getUsersByRole("OWNER"));
                    } catch (SQLException e) {
                        System.out.println("  Error: " + e.getMessage());
                    }
                }

                case 4 -> {
                    try {
                        showList("ALL LOCATIONS",
                                locationService.getAllLocations());
                    } catch (SQLException e) {
                        System.out.println("  Error: " + e.getMessage());
                    }
                }

                case 5 -> {
                    try {
                        showBookingList(bookingService.getAllBookings());
                    } catch (SQLException e) {
                        System.out.println("  Error: " + e.getMessage());
                    }
                }

                case 6 -> {
                    return;
                }

                default -> System.out.println("  Invalid choice!");
            }
        }
    }

    // ====================== OWNER ======================
    private static void ownerMenu(User owner) {
        while (true) {
            line();
            System.out.println("       PARKING OWNER MENU - " + owner.getName());
            line();
            System.out.println("  1. Add Parking Location");
            System.out.println("  2. View My Locations");
            System.out.println("  3. Delete Parking Location");
            System.out.println("  4. View Bookings on My Locations");
            System.out.println("  5. Logout");
            line();
            System.out.print("  Enter Choice: ");
            switch (readInt()) {
                case 1 -> addLocation(owner);
                case 2 -> {
                    try { showList("MY LOCATIONS", locationService.getLocationsByOwner(owner.getUserId())); }
                    catch (SQLException e) { System.out.println("  Error: " + e.getMessage()); }
                }
                case 3 -> deleteLocation(owner);
                case 4 -> viewOwnerBookings(owner);
                case 5 -> { return; }
                default -> System.out.println("  Invalid choice!");
            }
        }
    }

    private static void addLocation(User owner) {
        try {
            line();
            System.out.println("       ADD PARKING LOCATION");
            line();
            System.out.print("  Location Name     : "); String name = sc.nextLine();
            System.out.print("  Address           : "); String addr = sc.nextLine();
            System.out.print("  City              : "); String city = sc.nextLine();
            System.out.print("  Total Slots       : "); int slots = readInt();
            System.out.print("  Price per Hour(Rs): "); double price = readDouble();
            ParkingLocation loc = new ParkingLocation(owner.getUserId(), name, addr, city, slots, price);
            int id = locationService.addLocation(loc);
            System.out.println("\n  Location added! ID: " + id);
        } catch (SQLException e) { System.out.println("  Error: " + e.getMessage()); }
    }

    private static void deleteLocation(User owner) {
        try {
            showList("MY LOCATIONS", locationService.getLocationsByOwner(owner.getUserId()));
            System.out.print("  Enter Location ID to delete: ");
            int id = readInt();
            boolean ok = locationService.deleteLocation(id, owner.getUserId());
            System.out.println(ok ? "  Deleted!" : "  Not found or not yours!");
        } catch (SQLException e) { System.out.println("  Error: " + e.getMessage()); }
    }

    private static void viewOwnerBookings(User owner) {
        try {
            line();
            System.out.println("       BOOKINGS ON MY LOCATIONS");
            line();
            List<ParkingLocation> myLocs = locationService.getLocationsByOwner(owner.getUserId());
            if (myLocs.isEmpty()) { System.out.println("  No locations found."); return; }
            boolean found = false;
            for (ParkingLocation loc : myLocs) {
                for (Booking b : bookingService.getAllBookings()) {
                    if (b.getLocationId() == loc.getLocationId()) {
                        System.out.println("  " + b);
                        found = true;
                    }
                }
            }
            if (!found) System.out.println("  No bookings yet.");
        } catch (SQLException e) { System.out.println("  Error: " + e.getMessage()); }
    }

    // ====================== USER (VEHICLE OWNER) ======================
    private static void userMenu(User user) {
        while (true) {
            line();
            System.out.println("       VEHICLE OWNER MENU - " + user.getName());
            line();
            System.out.println("  1. Search Parking by City");
            System.out.println("  2. Book Parking Slot");
            System.out.println("  3. Release Parking (Checkout)");
            System.out.println("  4. View My Bookings");
            System.out.println("  5. Logout");
            line();
            System.out.print("  Enter Choice: ");
            switch (readInt()) {
                case 1 -> searchByCity();
                case 2 -> bookParking(user);
                case 3 -> releaseParking();
                case 4 -> {
                    try { showBookingList(bookingService.getBookingsByUser(user.getUserId())); }
                    catch (SQLException e) { System.out.println("  Error: " + e.getMessage()); }
                }
                case 5 -> { return; }
                default -> System.out.println("  Invalid choice!");
            }
        }
    }

    private static void searchByCity() {
        try {
            line();
            System.out.println("       SEARCH PARKING");
            line();
            System.out.print("  Enter City: ");
            String city = sc.nextLine();
            List<ParkingLocation> list = locationService.getAvailableByCity(city);
            if (list.isEmpty()) { System.out.println("  No parking found in " + city); return; }
            list.forEach(l -> System.out.println("  " + l));
        } catch (SQLException e) { System.out.println("  Error: " + e.getMessage()); }
    }

    private static void bookParking(User user) {
        try {
            searchByCity();
            line();
            System.out.println("       BOOK PARKING");
            line();
            System.out.print("  Your Name         : "); String name = sc.nextLine();
            System.out.print("  Your Phone        : "); String phone = sc.nextLine();
            System.out.print("  Your Native       : "); String nativePlace = sc.nextLine();
            System.out.print("  Vehicle Number    : "); String vehicle = sc.nextLine().toUpperCase();
            System.out.print("  Location ID       : "); int locId = readInt();

            ParkingLocation loc = locationService.getById(locId);
            if (loc == null || loc.getAvailableSlots() <= 0) {
                System.out.println("  Invalid location or no slots!"); return;
            }
            Booking booking = new Booking(user.getUserId(), name, phone, nativePlace, vehicle, locId);
            int id = bookingService.createBooking(booking);
            locationService.updateSlots(locId, -1);
            line();
            System.out.println("  Booking Successful!");
            System.out.println("  Booking ID  : " + id);
            System.out.println("  Location    : " + loc.getLocationName());
            System.out.println("  Vehicle     : " + vehicle);
            System.out.println("  Price       : Rs." + loc.getPricePerHour() + "/hr");
        } catch (SQLException e) { System.out.println("  Error: " + e.getMessage()); }
    }

    private static void releaseParking() {
        try {
            line();
            System.out.println("       RELEASE PARKING");
            line();
            System.out.print("  Enter Booking ID: ");
            int bookingId = readInt();
            Booking booking = null;
            for (Booking b : bookingService.getAllBookings()) {
                if (b.getBookingId() == bookingId && "ACTIVE".equals(b.getStatus())) {
                    booking = b; break;
                }
            }
            if (booking == null) { System.out.println("  No active booking found!"); return; }
            ParkingLocation loc = locationService.getById(booking.getLocationId());
            long diff = System.currentTimeMillis() - booking.getBookingTime().getTime();
            double hours = Math.max(1, Math.ceil(diff / (1000.0 * 60 * 60)));
            double cost = hours * loc.getPricePerHour();
            bookingService.releaseBooking(bookingId, cost);
            locationService.updateSlots(booking.getLocationId(), 1);
            line();
            System.out.println("  Released!");
            System.out.println("  Duration: " + (int) hours + " hour(s)");
            System.out.println("  Cost: Rs." + cost);
        } catch (SQLException e) { System.out.println("  Error: " + e.getMessage()); }
    }

    // ====================== HELPERS ======================
    private static void line() { System.out.println("=========================================="); }

    private static int readInt() {
        int val = 0;
        try { val = Integer.parseInt(sc.nextLine().trim()); } catch (Exception e) {}
        return val;
    }

    private static double readDouble() {
        double val = 0;
        try { val = Double.parseDouble(sc.nextLine().trim()); } catch (Exception e) {}
        return val;
    }

    private static void showList(String title, List<?> list) {
        line();
        System.out.println("       " + title);
        line();
        if (list.isEmpty()) { System.out.println("  No records found."); return; }
        list.forEach(item -> System.out.println("  " + item));
    }

    private static void showBookingList(List<Booking> list) {
        line();
        System.out.println("       BOOKINGS");
        line();
        if (list.isEmpty()) { System.out.println("  No bookings found."); return; }
        list.forEach(b -> System.out.println("  " + b));
    }
}
