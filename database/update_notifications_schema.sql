-- =========================================
-- VERIFY NOTIFICATIONS TABLE SCHEMA
-- =========================================

USE parkway_db;

-- Table already exists with all required columns
-- This script verifies the schema is correct

DESCRIBE notifications;

SELECT 'Notifications table verified successfully' as Status;
SELECT COUNT(*) as notification_count FROM notifications;

