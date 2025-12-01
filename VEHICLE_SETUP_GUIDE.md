# Vehicle Management System - Setup Guide

## ✅ What Has Been Implemented

Complete vehicle management system with all 5 required API endpoints:

1. **POST** `/api/vehicles` - Add Vehicle
2. **GET** `/api/vehicles/user/:userId` - Get User Vehicle  
3. **PUT** `/api/vehicles/:vehicleId` - Update Vehicle
4. **DELETE** `/api/vehicles/:vehicleId` - Delete Vehicle
5. **GET** `/api/vehicles` - Get All Vehicles (Admin)

---

## 📁 Files Created/Modified

### New DTOs:
- `VehicleDTO.java` - Standard vehicle data transfer object
- `VehicleWithUserDTO.java` - Vehicle with user info (for admin endpoint)
- `VehicleRequest.java` - Request payload for add/update
- `VehicleResponse.java` - Response with vehicle_id and message

### Updated Model:
- `Vehicle.java` - Added user relationship, timestamps, proper field names

### Updated Repository:
- `VehicleRepository.java` - Added custom query methods

### Updated Service:
- `VehicleService.java` - Complete business logic with error handling and logging

### Updated Controller:
- `VehicleController.java` - All 5 RESTful endpoints with CORS

### Database Files:
- `database/create_vehicles_table.sql` - Original schema
- `database/fix_vehicles_table.sql` - Schema fix script
- `database/MANUAL_SETUP.sql` - **USE THIS ONE!**

### Documentation:
- `VEHICLE_API_DOCUMENTATION.md` - Complete API reference

---

## 🗄️ Database Setup

### Option 1: Execute SQL Manually (RECOMMENDED)

1. Open **MySQL Workbench** or **phpMyAdmin**
2. Connect to your `parkway` database
3. Execute this SQL:

```sql
USE parkway;

-- Drop old table
DROP TABLE IF EXISTS vehicles;

-- Create new table
CREATE TABLE vehicles (
    vehicle_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    plate_number VARCHAR(50) NOT NULL,
    model VARCHAR(100) NOT NULL,
    vehicle_type VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_plate_number (plate_number),
    KEY idx_user_id (user_id),
    CONSTRAINT fk_vehicles_user FOREIGN KEY (user_id) 
        REFERENCES users(user_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

4. Verify:
```sql
DESCRIBE vehicles;
SELECT * FROM vehicles;
```

### Option 2: Use SQL File

Execute the file: `database/MANUAL_SETUP.sql`

---

## 🚀 Start the Application

After setting up the database, start the Spring Boot application:

```powershell
mvn spring-boot:run
```

The application will start on **http://localhost:8080**

---

## 🧪 Testing the API

### Test with cURL (PowerShell):

**1. Add Vehicle:**
```powershell
curl -X POST http://localhost:8080/api/vehicles `
  -H "Content-Type: application/json" `
  -d '{\"userId\": 1, \"plateNumber\": \"ABC 1234\", \"model\": \"Toyota Vios\", \"vehicleType\": \"Car\"}'
```

**2. Get User Vehicle:**
```powershell
curl http://localhost:8080/api/vehicles/user/1
```

**3. Update Vehicle:**
```powershell
curl -X PUT http://localhost:8080/api/vehicles/1 `
  -H "Content-Type: application/json" `
  -d '{\"plateNumber\": \"XYZ 5678\", \"model\": \"Honda Civic\", \"vehicleType\": \"Car\"}'
```

**4. Delete Vehicle:**
```powershell
curl -X DELETE http://localhost:8080/api/vehicles/1
```

**5. Get All Vehicles:**
```powershell
curl http://localhost:8080/api/vehicles
```

### Test with Postman/Thunder Client:

Import this collection or test manually:

**Base URL:** `http://localhost:8080`

| Method | Endpoint | Body |
|--------|----------|------|
| POST | `/api/vehicles` | `{"userId": 1, "plateNumber": "ABC 1234", "model": "Toyota Vios", "vehicleType": "Car"}` |
| GET | `/api/vehicles/user/1` | None |
| PUT | `/api/vehicles/1` | `{"plateNumber": "XYZ 5678", "model": "Honda Civic", "vehicleType": "Car"}` |
| DELETE | `/api/vehicles/1` | None |
| GET | `/api/vehicles` | None |

---

## 🔒 Security Features Implemented

✅ **SQL Injection Prevention:**
- Uses JPA/Hibernate with parameterized queries
- Spring Data JPA repository methods
- No raw SQL concatenation

✅ **Unique Constraint Handling:**
- Database enforces UNIQUE on `plate_number`
- Application validates before insert/update
- Catches `DataIntegrityViolationException`

✅ **Error Handling:**
- Try-catch blocks in all service methods
- Descriptive error messages
- Proper HTTP status codes

✅ **Logging:**
- SLF4J logger in service and controller
- Logs all operations and errors

✅ **Foreign Key Cascade:**
- Vehicles deleted when user is removed
- Maintains referential integrity

✅ **CORS Configuration:**
- Allows React frontend (localhost:3000)
- Configured in controller and SecurityConfig

✅ **Transaction Management:**
- `@Transactional` annotations on write operations

---

## 📊 Database Schema

```
vehicles
├── vehicle_id (INT, PRIMARY KEY, AUTO_INCREMENT)
├── user_id (INT, NOT NULL, FOREIGN KEY → users.user_id)
├── plate_number (VARCHAR(50), NOT NULL, UNIQUE)
├── model (VARCHAR(100), NOT NULL)
├── vehicle_type (VARCHAR(20), NOT NULL)
└── created_at (TIMESTAMP, DEFAULT CURRENT_TIMESTAMP)

Indexes:
- PRIMARY KEY on vehicle_id
- UNIQUE KEY on plate_number
- INDEX on user_id

Foreign Key:
- user_id REFERENCES users(user_id) ON DELETE CASCADE
```

---

## 📖 Response Examples

### Success Response (Add Vehicle):
```json
{
  "vehicleId": 1,
  "message": "Vehicle added successfully"
}
```

### Success Response (Get User Vehicle):
```json
{
  "vehicleId": 1,
  "userId": 1,
  "plateNumber": "ABC 1234",
  "model": "Toyota Vios",
  "vehicleType": "Car",
  "createdAt": "2025-12-01T10:00:00"
}
```

### Error Response:
```json
{
  "message": "Plate number already exists: ABC 1234"
}
```

---

## 🐛 Troubleshooting

### Issue: Port 8080 already in use
```powershell
# Find process
Get-NetTCPConnection -LocalPort 8080 | Select-Object -ExpandProperty OwningProcess

# Kill process (replace PID with actual number)
Stop-Process -Id <PID> -Force
```

### Issue: Foreign key constraint fails
1. Make sure `users` table exists with `user_id` column
2. Drop and recreate vehicles table using `MANUAL_SETUP.sql`
3. Restart Spring Boot application

### Issue: Plate number duplicate error
- This is expected behavior for duplicate plate numbers
- Check existing vehicles: `SELECT * FROM vehicles;`

---

## 📚 Additional Documentation

See `VEHICLE_API_DOCUMENTATION.md` for complete API reference with:
- Detailed endpoint descriptions
- Request/response examples
- Error codes and messages
- cURL examples
- Testing strategies

---

## ✨ Features Summary

| Feature | Status |
|---------|--------|
| Add Vehicle | ✅ |
| Get User Vehicle | ✅ |
| Update Vehicle | ✅ |
| Delete Vehicle | ✅ |
| Get All Vehicles (Admin) | ✅ |
| Unique Plate Number Validation | ✅ |
| User Relationship (Foreign Key) | ✅ |
| Timestamp (created_at) | ✅ |
| Error Handling | ✅ |
| Logging | ✅ |
| SQL Injection Prevention | ✅ |
| CORS Support | ✅ |
| RESTful Design | ✅ |

All requirements completed! 🎉
