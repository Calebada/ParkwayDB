package com.parkway.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AdminNotificationDTO {
    
    @JsonProperty("notification_id")
    private Long notificationId;
    
    @JsonProperty("booking_id")
    private Long bookingId;
    
    @JsonProperty("user_name")
    private String userName;
    
    @JsonProperty("parking_lot_name")
    private String parkingLotName;
    
    @JsonProperty("date_reserved")
    private LocalDate dateReserved;
    
    @JsonProperty("time_in")
    private LocalTime timeIn;
    
    @JsonProperty("time_out")
    private LocalTime timeOut;
    
    @JsonProperty("read")
    private Boolean read;
    
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    
    // Default constructor
    public AdminNotificationDTO() {
    }
    
    // Constructor
    public AdminNotificationDTO(Long notificationId, Long bookingId, String userName,
                               String parkingLotName, LocalDate dateReserved, LocalTime timeIn,
                               LocalTime timeOut, Boolean read, LocalDateTime createdAt) {
        this.notificationId = notificationId;
        this.bookingId = bookingId;
        this.userName = userName;
        this.parkingLotName = parkingLotName;
        this.dateReserved = dateReserved;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        this.read = read;
        this.createdAt = createdAt;
    }
    
    // Getters and Setters
    public Long getNotificationId() {
        return notificationId;
    }
    
    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }
    
    public Long getBookingId() {
        return bookingId;
    }
    
    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getParkingLotName() {
        return parkingLotName;
    }
    
    public void setParkingLotName(String parkingLotName) {
        this.parkingLotName = parkingLotName;
    }
    
    public LocalDate getDateReserved() {
        return dateReserved;
    }
    
    public void setDateReserved(LocalDate dateReserved) {
        this.dateReserved = dateReserved;
    }
    
    public LocalTime getTimeIn() {
        return timeIn;
    }
    
    public void setTimeIn(LocalTime timeIn) {
        this.timeIn = timeIn;
    }
    
    public LocalTime getTimeOut() {
        return timeOut;
    }
    
    public void setTimeOut(LocalTime timeOut) {
        this.timeOut = timeOut;
    }
    
    public Boolean getRead() {
        return read;
    }
    
    public void setRead(Boolean read) {
        this.read = read;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
