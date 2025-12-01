package com.parkway.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalTime;

public class AdminNotificationRequest {
    
    @JsonProperty("parking_lot_id")
    private Long parkingLotId;
    
    @JsonProperty("booking_id")
    private Long bookingId;
    
    @JsonProperty("user_id")
    private Long userId;
    
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
    
    @JsonProperty("vehicle_type")
    private String vehicleType;
    
    // Default constructor
    public AdminNotificationRequest() {
    }
    
    // Getters and Setters
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
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
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
}
