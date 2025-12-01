package com.parkway.demo.service;

import com.parkway.demo.model.Notification;
import com.parkway.demo.repository.NotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NotificationService {
    
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);
    
    @Autowired
    private NotificationRepository notificationRepository;
    
    /**
     * Create notification for admin when user makes a booking
     */
    @Transactional
    public Notification createAdminNotification(Notification notification) {
        try {
            logger.info("Creating admin notification for admin_id: {}", notification.getAdminId());
            notification.setRead(false);
            Notification saved = notificationRepository.save(notification);
            logger.info("Admin notification created successfully with ID: {}", saved.getNotificationId());
            return saved;
        } catch (Exception e) {
            logger.error("Error creating admin notification: {}", e.getMessage(), e);
            throw new RuntimeException("Error creating notification: " + e.getMessage());
        }
    }
    
    /**
     * Get all notifications for admin by admin_id (which is a user_id)
     */
    public List<Notification> getAdminNotifications(Long adminId) {
        try {
            logger.info("Fetching admin notifications for admin_id: {}", adminId);
            List<Notification> notifications = notificationRepository.findByAdminIdOrderByCreatedAtDesc(adminId);
            logger.info("Found {} notification(s) for admin", notifications.size());
            return notifications;
        } catch (Exception e) {
            logger.error("Error fetching admin notifications: {}", e.getMessage(), e);
            return List.of();
        }
    }
    
    /**
     * Create notification for user when admin confirms
     */
    @Transactional
    public Notification createUserNotification(Notification notification) {
        try {
            logger.info("Creating user notification for user_id: {}", notification.getUserId());
            notification.setRead(false);
            Notification saved = notificationRepository.save(notification);
            logger.info("User notification created successfully with ID: {}", saved.getNotificationId());
            return saved;
        } catch (Exception e) {
            logger.error("Error creating user notification: {}", e.getMessage(), e);
            throw new RuntimeException("Error creating notification: " + e.getMessage());
        }
    }
    
    /**
     * Get all notifications for user
     */
    public List<Notification> getUserNotifications(Long userId) {
        try {
            logger.info("Fetching user notifications for user_id: {}", userId);
            List<Notification> notifications = notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
            logger.info("Found {} notification(s) for user", notifications.size());
            return notifications;
        } catch (Exception e) {
            logger.error("Error fetching user notifications: {}", e.getMessage(), e);
            return List.of();
        }
    }
    
    /**
     * Mark notification as read
     */
    @Transactional
    public void markAsRead(Long notificationId) {
        try {
            logger.info("Marking notification {} as read", notificationId);
            Notification notification = notificationRepository.findById(notificationId)
                    .orElseThrow(() -> new RuntimeException("Notification not found"));
            notification.setRead(true);
            notificationRepository.save(notification);
            logger.info("Notification marked as read");
        } catch (Exception e) {
            logger.error("Error marking notification as read: {}", e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * Mark all user notifications as read
     */
    @Transactional
    public void markAllAsReadByUserId(Long userId) {
        try {
            logger.info("Marking all notifications as read for user_id: {}", userId);
            notificationRepository.markAllAsReadByUserId(userId);
            logger.info("All notifications marked as read for user");
        } catch (Exception e) {
            logger.error("Error marking all notifications as read: {}", e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * Delete notification
     */
    @Transactional
    public void deleteNotification(Long notificationId) {
        try {
            logger.info("Deleting notification {}", notificationId);
            notificationRepository.deleteById(notificationId);
            logger.info("Notification deleted successfully");
        } catch (Exception e) {
            logger.error("Error deleting notification: {}", e.getMessage(), e);
            throw e;
        }
    }
}
