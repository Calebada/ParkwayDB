package com.parkway.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "notifications")
public class Notification {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    @JsonProperty("notification_id")
    private Long notificationId;
    
    @Column(name = "admin_id")
    @JsonProperty("admin_id")
    private Long adminId;
    
    @Column(name = "user_id")
    @JsonProperty("user_id")
    private Long userId;
    
    @Column(name = "parking_lot_id")
    @JsonProperty("parking_lot_id")
    private Long parkingLotId;
    
    @Column(name = "booking_id")
    @JsonProperty("booking_id")
    private Long bookingId;
    
    @Column(name = "title")
    @JsonProperty("title")
    private String title;
    
    @Column(name = "message", columnDefinition = "TEXT")
    @JsonProperty("message")
    private String message;
    
    @Column(name = "type", length = 50)
    @JsonProperty("type")
    private String type;
    
    @Column(name = "user_name")
    @JsonProperty("user_name")
    private String userName;
    
    @Column(name = "parking_lot_name")
    @JsonProperty("parking_lot_name")
    private String parkingLotName;
    
    @Column(name = "date_reserved")
    @JsonProperty("date_reserved")
    private LocalDate dateReserved;
    
    @Column(name = "time_in")
    @JsonProperty("time_in")
    private LocalTime timeIn;
    
    @Column(name = "time_out")
    @JsonProperty("time_out")
    private LocalTime timeOut;
    
    @Column(name = "vehicle_type", length = 50)
    @JsonProperty("vehicle_type")
    private String vehicleType;
    
    @Column(name = "read", nullable = false)
    @JsonProperty("read")
    private Boolean read = false;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    
    // Default constructor
    public Notification() {
    }
    
    // Getters and Setters
    public Long getNotificationId() {
        return notificationId;
    }
    
    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }
    
    public Long getAdminId() {
        return adminId;
    }
    
    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public Long getParkingLotId() {
        return parkingLotId;
    }
    
    public void setParkingLotId(Long parkingLotId) {
        this.parkingLotId = parkingLotId;
    }
    
    public Long getBookingId() {
        return bookingId;
    }
    
    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
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
    
    public String getVehicleType() {
        return vehicleType;
    }
    
    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
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
