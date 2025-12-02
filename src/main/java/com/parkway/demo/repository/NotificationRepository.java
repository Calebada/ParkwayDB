package com.parkway.demo.repository;

import com.parkway.demo.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    
    /**
     * Find all notifications for admin by admin_id (which is a user_id)
     */
    @Query("SELECT n FROM Notification n WHERE n.adminId = :adminId ORDER BY n.createdAt DESC")
    List<Notification> findByAdminIdOrderByCreatedAtDesc(@Param("adminId") Long adminId);
    
    /**
     * Find all notifications for a user ordered by creation date
     */
    @Query("SELECT n FROM Notification n WHERE n.userId = :userId ORDER BY n.createdAt DESC")
    List<Notification> findByUserIdOrderByCreatedAtDesc(@Param("userId") Long userId);
    
    /**
     * Mark all notifications for a user as read
     */
    @Modifying
    @Transactional
    @Query("UPDATE Notification n SET n.read = true WHERE n.userId = :userId")
    void markAllAsReadByUserId(@Param("userId") Long userId);
    
    /**
     * Delete old notifications (older than specified days)
     */
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM notifications WHERE created_at < DATE_SUB(NOW(), INTERVAL :days DAY) AND `read` = true", nativeQuery = true)
    int deleteOldReadNotifications(@Param("days") int days);
    
    /**
     * Count total notifications in system
     */
    @Query("SELECT COUNT(n) FROM Notification n")
    long countTotalNotifications();
}
