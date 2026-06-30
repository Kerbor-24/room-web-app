package com.roomallocation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@RestController
public class RoomWebController {

    // Database configurations
    private static final String URL = "jdbc:mysql://localhost:3306/room_db"; 
    private static final String USER = "root";
    private static final String PASSWORD = "Basukbha@24"; // Use your working password here

    private Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    @GetMapping("/api/rooms")
    public List<Room> getLiveRoomsFromDatabase() {
        List<Room> realRoomsList = new ArrayList<>();
        String sql = "SELECT room_number, capacity, status FROM rooms";
        
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                // Build a strong Java object for each database row entry
                Room room = new Room(
                    rs.getString("room_number"),
                    rs.getInt("capacity"),
                    rs.getString("status")
                );
                realRoomsList.add(room);
            }
        } catch (Exception ex) {
            System.out.println("Web Server Data Error: " + ex.getMessage());
        }
        return realRoomsList;
    }
    @org.springframework.web.bind.annotation.PostMapping("/api/rooms/add")
    public void addNewRoomFromWeb(@org.springframework.web.bind.annotation.RequestParam String roomNumber) {
        String sql = "INSERT INTO rooms (room_number, capacity, status) VALUES (?, 45, 'Available')";
        
        try (Connection conn = getConnection(); java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, roomNumber);
            stmt.executeUpdate();
            System.out.println("Successfully registered room over HTTP: " + roomNumber);
        } catch (Exception ex) {
            System.out.println("Database Write Failure: " + ex.getMessage());
        }
    }
    @org.springframework.web.bind.annotation.PostMapping("/api/rooms/toggle")
    public void toggleRoomStatusFromWeb(@org.springframework.web.bind.annotation.RequestParam String roomNumber) {
        String sql = "UPDATE rooms SET status = CASE WHEN status = 'Available' THEN 'Occupied' ELSE 'Available' END WHERE room_number = ?";
        try (Connection conn = getConnection(); java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, roomNumber);
            stmt.executeUpdate();
            System.out.println("Toggled room status via Web API: " + roomNumber);
        } catch (Exception ex) {
            System.out.println("Status Toggle Failure: " + ex.getMessage());
        }
    }

    @org.springframework.web.bind.annotation.PostMapping("/api/rooms/delete")
    public void deleteRoomFromWeb(@org.springframework.web.bind.annotation.RequestParam String roomNumber) {
        String sql = "DELETE FROM rooms WHERE room_number = ?";
        try (Connection conn = getConnection(); java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, roomNumber);
            stmt.executeUpdate();
            System.out.println("Purged room record via Web API: " + roomNumber);
        } catch (Exception ex) {
            System.out.println("Record Delete Failure: " + ex.getMessage());
        }
    }
}