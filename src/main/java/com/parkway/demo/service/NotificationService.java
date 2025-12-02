package com.parkway.demo.service;

import com.parkway.demo.model.Notification;
import com.parkway.demo.repository.NotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NotificationService {
    
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);
    private static final int DEFAULT_RETENTION_DAYS = 30; // Keep notifications for 30 days
    
    @Autowired
    private NotificationRepository notificationRepository;
    
    @Value("${notification.retention.days:30}")
    private int retentionDays;
    
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
    
    /**
     * Automatically delete old read notifications (runs daily at 2 AM)
     * Keeps unread notifications regardless of age for user attention
     */
    @Scheduled(cron = "0 0 2 * * *") // Daily at 2:00 AM
    @Transactional
    public void cleanupOldNotifications() {
        try {
            int retentionDaysToUse = retentionDays > 0 ? retentionDays : DEFAULT_RETENTION_DAYS;
            logger.info("Starting notification cleanup - removing read notifications older than {} days", retentionDaysToUse);
            
            int deletedCount = notificationRepository.deleteOldReadNotifications(retentionDaysToUse);
            logger.info("Cleaned up {} old notifications", deletedCount);
            
            long remainingCount = notificationRepository.countTotalNotifications();
            logger.info("Total notifications remaining in system: {}", remainingCount);
        } catch (Exception e) {
            logger.error("Error during notification cleanup: {}", e.getMessage(), e);
            // Don't throw - cleanup failure shouldn't break the application
        }
    }
    
    /**
     * Manual trigger to cleanup old notifications
     * Useful for admins to manually trigger cleanup
     */
    @Transactional
    public int manualCleanupOldNotifications(int daysToRetain) {
        try {
            logger.info("Manual cleanup triggered - removing read notifications older than {} days", daysToRetain);
            int deletedCount = notificationRepository.deleteOldReadNotifications(daysToRetain);
            logger.info("Manually cleaned up {} old notifications", deletedCount);
            return deletedCount;
        } catch (Exception e) {
            logger.error("Error during manual notification cleanup: {}", e.getMessage(), e);
            throw new RuntimeException("Error cleaning up notifications: " + e.getMessage());
        }
    }
    
    /**
     * Get notification system statistics
     */
    public NotificationStats getNotificationStats() {
        try {
            long totalCount = notificationRepository.countTotalNotifications();
            logger.info("Notification statistics - Total: {}", totalCount);
            return new NotificationStats(totalCount, retentionDays);
        } catch (Exception e) {
            logger.error("Error fetching notification stats: {}", e.getMessage(), e);
            return new NotificationStats(0, retentionDays);
        }
    }
    
    /**
     * Inner class for notification statistics
     */
    public static class NotificationStats {
        public long totalNotifications;
        public int retentionDays;
        
        public NotificationStats(long totalNotifications, int retentionDays) {
            this.totalNotifications = totalNotifications;
            this.retentionDays = retentionDays;
        }
    }
}
