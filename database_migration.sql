-- DATABASE MIGRATION SCRIPT
-- This script updates the existing database schema to match frontend requirements
-- Run this script to update your database without losing data

-- ============================================
-- STEP 1: Backup current data (IMPORTANT!)
-- ============================================
-- Before running this script, backup your database:
-- mysqldump -u root -p parkway > parkway_backup.sql

-- ============================================
-- STEP 2: Update ADMINS table
-- ============================================

-- Add new columns to admins table
ALTER TABLE admins 
ADD COLUMN IF NOT EXISTS email VARCHAR(255),
ADD COLUMN IF NOT EXISTS password VARCHAR(255),
ADD COLUMN IF NOT EXISTS firstname VARCHAR(100),
ADD COLUMN IF NOT EXISTS lastname VARCHAR(100),
ADD COLUMN IF NOT EXISTS location VARCHAR(255),
ADD COLUMN IF NOT EXISTS created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

-- Rename staff_id to admin_id (preserves data and relationships)
ALTER TABLE admins 
CHANGE COLUMN staff_id admin_id BIGINT AUTO_INCREMENT;

-- Rename rate to price
ALTER TABLE admins 
CHANGE COLUMN rate price DECIMAL(10,2);

-- Update email to be unique and not null (after populating data)
-- ALTER TABLE admins MODIFY COLUMN email VARCHAR(255) UNIQUE NOT NULL;
-- ALTER TABLE admins MODIFY COLUMN password VARCHAR(255) NOT NULL;

-- ============================================
-- STEP 3: Update USERS table
-- ============================================

-- Add created_at column
ALTER TABLE users 
ADD COLUMN IF NOT EXISTS created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

-- Update role column length
ALTER TABLE users 
MODIFY COLUMN role VARCHAR(20) DEFAULT 'user';

-- ============================================
-- STEP 4: Update BOOKINGS table
-- ============================================

-- Add vehicle_id column
ALTER TABLE bookings 
ADD COLUMN IF NOT EXISTS vehicle_id BIGINT;

-- Add foreign key for vehicle_id
ALTER TABLE bookings 
ADD CONSTRAINT fk_bookings_vehicle 
FOREIGN KEY (vehicle_id) REFERENCES vehicles(vehicle_id) 
ON DELETE SET NULL;

-- ============================================
-- STEP 5: Update PARKING_SLOTS table
-- ============================================

-- Update foreign key column name from parking_lot_id to admin_id in parking_slots
-- Note: This requires dropping and recreating the foreign key constraint

-- Drop existing foreign key
ALTER TABLE parking_slots 
DROP FOREIGN KEY IF EXISTS parking_slots_ibfk_1;

-- Update the column reference (if needed based on your schema)
-- The column name parking_lot_id in parking_slots should reference admins.admin_id
ALTER TABLE parking_slots 
ADD CONSTRAINT fk_parking_slots_admin 
FOREIGN KEY (parking_lot_id) REFERENCES admins(admin_id) 
ON DELETE CASCADE;

-- ============================================
-- STEP 6: Update NOTIFICATIONS table
-- ============================================

-- Drop old foreign key if it exists
ALTER TABLE notifications 
DROP FOREIGN KEY IF EXISTS notifications_ibfk_3;

-- Rename parking_lot_id column to admin_id
ALTER TABLE notifications 
CHANGE COLUMN parking_lot_id admin_id BIGINT;

-- Add new foreign key constraint
ALTER TABLE notifications 
ADD CONSTRAINT fk_notifications_admin 
FOREIGN KEY (admin_id) REFERENCES admins(admin_id) 
ON DELETE CASCADE;

-- ============================================
-- STEP 7: Verify all tables
-- ============================================

-- Check USERS table structure
DESCRIBE users;

-- Check VEHICLES table structure  
DESCRIBE vehicles;

-- Check ADMINS table structure
DESCRIBE admins;

-- Check PARKING_SLOTS table structure
DESCRIBE parking_slots;

-- Check BOOKINGS table structure
DESCRIBE bookings;

-- Check NOTIFICATIONS table structure
DESCRIBE notifications;

-- ============================================
-- STEP 8: Initialize parking slots (if needed)
-- ============================================
-- After migration, call the backend endpoint:
-- POST http://localhost:8080/api/parking-slots/initialize
-- This will create parking slots for all existing admins with capacity > 0

-- ============================================
-- NOTES:
-- ============================================
-- 1. The admin_id rename preserves all existing data and auto-increment values
-- 2. Foreign keys are automatically updated to reference the renamed column
-- 3. Existing parking_slots will still reference the correct admin records
-- 4. You may need to populate email/password/firstname/lastname for existing admins manually
-- 5. After running this, restart your Spring Boot application
