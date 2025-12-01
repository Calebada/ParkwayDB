package com.parkway.demo.controller;

import com.parkway.demo.model.Notification;
import com.parkway.demo.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class NotificationController {
    
    private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);
    
    @Autowired
    private NotificationService notificationService;
    
    /**
     * POST /api/notifications/admin
     * Create notification for admin when user books
     */
    @PostMapping("/admin")
    public ResponseEntity<?> createAdminNotification(@RequestBody Notification notification) {
        try {
            logger.info("POST /api/notifications/admin - Creating admin notification");
            
            Notification saved = notificationService.createAdminNotification(notification);
            Map<String, Object> response = new HashMap<>();
            response.put("notification_id", saved.getNotificationId());
            response.put("message", "Notification created");
            
            return new ResponseEntity<>(response, HttpStatus.CREATED);
            
        } catch (RuntimeException e) {
            logger.error("Error creating admin notification: {}", e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }
    
    /**
     * GET /api/notifications/admin/:admin_user_id
     * Get all notifications for admin (by admin_id which is user_id)
     */
    @GetMapping("/admin/{adminUserId}")
    public ResponseEntity<List<Notification>> getAdminNotifications(@PathVariable("adminUserId") Long adminUserId) {
        try {
            logger.info("GET /api/notifications/admin/{} - Fetching admin notifications", adminUserId);
            
            List<Notification> notifications = notificationService.getAdminNotifications(adminUserId);
            
            return new ResponseEntity<>(notifications, HttpStatus.OK);
            
        } catch (Exception e) {
            logger.error("Error fetching admin notifications: {}", e.getMessage());
            return new ResponseEntity<>(List.of(), HttpStatus.OK);
        }
    }
    
    /**
     * POST /api/notifications/user
     * Create notification for user when admin confirms
     */
    @PostMapping("/user")
    public ResponseEntity<?> createUserNotification(@RequestBody Notification notification) {
        try {
            logger.info("POST /api/notifications/user - Creating user notification");
            
            Notification saved = notificationService.createUserNotification(notification);
            Map<String, Object> response = new HashMap<>();
            response.put("notification_id", saved.getNotificationId());
            response.put("message", "Notification created");
            
            return new ResponseEntity<>(response, HttpStatus.CREATED);
            
        } catch (RuntimeException e) {
            logger.error("Error creating user notification: {}", e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }
    
    /**
     * GET /api/notifications/user/:userId
     * Get all notifications for a user
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Notification>> getUserNotifications(@PathVariable("userId") Long userId) {
        try {
            logger.info("GET /api/notifications/user/{} - Fetching user notifications", userId);
            
            List<Notification> notifications = notificationService.getUserNotifications(userId);
            
            return new ResponseEntity<>(notifications, HttpStatus.OK);
            
        } catch (Exception e) {
            logger.error("Error fetching user notifications: {}", e.getMessage());
            return new ResponseEntity<>(List.of(), HttpStatus.OK);
        }
    }
    
    /**
     * PUT /api/notifications/:notificationId/read
     * Mark notification as read
     */
    @PutMapping("/{notificationId}/read")
    public ResponseEntity<?> markAsRead(@PathVariable("notificationId") Long notificationId) {
        try {
            logger.info("PUT /api/notifications/{}/read - Marking as read", notificationId);
            
            notificationService.markAsRead(notificationId);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Notification marked as read");
            
            return new ResponseEntity<>(response, HttpStatus.OK);
            
        } catch (RuntimeException e) {
            logger.error("Error marking notification as read: {}", e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }
    
    /**
     * PUT /api/notifications/user/:userId/read-all
     * Mark all notifications as read for a user
     */
    @PutMapping("/user/{userId}/read-all")
    public ResponseEntity<?> markAllNotificationsAsRead(@PathVariable("userId") Long userId) {
        try {
            logger.info("PUT /api/notifications/user/{}/read-all - Marking all as read", userId);
            
            notificationService.markAllAsReadByUserId(userId);
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "All notifications marked as read");
            
            return new ResponseEntity<>(response, HttpStatus.OK);
            
        } catch (Exception e) {
            logger.error("Error marking all notifications as read: {}", e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * DELETE /api/notifications/:notificationId
     * Delete a notification
     */
    @DeleteMapping("/{notificationId}")
    public ResponseEntity<?> deleteNotification(@PathVariable("notificationId") Long notificationId) {
        try {
            logger.info("DELETE /api/notifications/{} - Deleting notification", notificationId);
            
            notificationService.deleteNotification(notificationId);
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Notification deleted");
            
            return new ResponseEntity<>(response, HttpStatus.OK);
            
        } catch (Exception e) {
            logger.error("Error deleting notification: {}", e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
