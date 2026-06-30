package com.roomallocation;

public class Room {
    private String roomNumber;
    private int capacity;
    private String status;

    // Constructor
    public Room(String roomNumber, int capacity, String status) {
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.status = status;
    }

    // Getters and Setters
    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}