# Vehicle Management System API Documentation

## Overview
Complete API endpoints for managing vehicles in the Parkway parking system.

---

## Base URL
```
http://localhost:8080/api/vehicles
```

---

## Endpoints

### 1. Add Vehicle
**POST** `/api/vehicles`

Register a new vehicle for a user.

#### Request Body:
```json
{
  "userId": 10,
  "plateNumber": "ABC 1234",
  "model": "Toyota Vios",
  "vehicleType": "Car"
}
```

#### Success Response (201 Created):
```json
{
  "vehicleId": 1,
  "message": "Vehicle added successfully"
}
```

#### Error Response (400 Bad Request):
```json
{
  "message": "Plate number already exists: ABC 1234"
}
```
OR
```json
{
  "message": "User not found with id: 10"
}
```

---

### 2. Get User Vehicle
**GET** `/api/vehicles/user/:userId`

Retrieve the vehicle registered to a specific user.

#### Example Request:
```
GET /api/vehicles/user/10
```

#### Success Response (200 OK):
```json
{
  "vehicleId": 1,
  "userId": 10,
  "plateNumber": "ABC 1234",
  "model": "Toyota Vios",
  "vehicleType": "Car",
  "createdAt": "2025-12-01T10:00:00.000"
}
```

#### Error Response (404 Not Found):
```json
{
  "message": "Vehicle not found for user id: 10"
}
```

---

### 3. Update Vehicle
**PUT** `/api/vehicles/:vehicleId`

Update vehicle information.

#### Example Request:
```
PUT /api/vehicles/1
```

#### Request Body:
```json
{
  "plateNumber": "XYZ 5678",
  "model": "Honda Civic",
  "vehicleType": "Car"
}
```

#### Success Response (200 OK):
```json
{
  "message": "Vehicle updated successfully"
}
```

#### Error Responses:
**404 Not Found:**
```json
{
  "message": "Vehicle not found with id: 1"
}
```

**400 Bad Request:**
```json
{
  "message": "Plate number already exists: XYZ 5678"
}
```

---

### 4. Delete Vehicle
**DELETE** `/api/vehicles/:vehicleId`

Delete a vehicle by its ID.

#### Example Request:
```
DELETE /api/vehicles/1
```

#### Success Response (200 OK):
```json
{
  "message": "Vehicle deleted successfully"
}
```

#### Error Response (404 Not Found):
```json
{
  "message": "Vehicle not found with id: 1"
}
```

---

### 5. Get All Vehicles (Admin)
**GET** `/api/vehicles`

Retrieve all vehicles with their associated user information. This endpoint is intended for admin use.

#### Example Request:
```
GET /api/vehicles
```

#### Success Response (200 OK):
```json
[
  {
    "vehicleId": 1,
    "userId": 10,
    "plateNumber": "ABC 1234",
    "model": "Toyota Vios",
    "vehicleType": "Car",
    "createdAt": "2025-12-01T10:00:00.000",
    "userFirstname": "John",
    "userLastname": "Doe"
  },
  {
    "vehicleId": 2,
    "userId": 11,
    "plateNumber": "XYZ 5678",
    "model": "Honda Civic",
    "vehicleType": "Car",
    "createdAt": "2025-12-01T11:00:00.000",
    "userFirstname": "Jane",
    "userLastname": "Smith"
  }
]
```

---

## Error Handling

All endpoints include comprehensive error handling with:
- **Try-catch blocks** to handle exceptions
- **Logging** for debugging and monitoring
- **Proper HTTP status codes** (200, 201, 400, 404, 500)
- **Descriptive error messages** in JSON format

---

## Security Features

### 1. SQL Injection Prevention
- Uses **JPA/Hibernate** with parameterized queries
- Repository methods use Spring Data JPA query methods
- Custom queries use `@Query` with `@Param` annotations

### 2. Unique Constraint Handling
- Database enforces `UNIQUE` constraint on `plate_number`
- Application validates before insert/update
- Catches `DataIntegrityViolationException` with user-friendly messages

### 3. Foreign Key Cascade
- Vehicles automatically deleted when user is removed (`ON DELETE CASCADE`)
- Ensures referential integrity

### 4. CORS Configuration
- Configured to allow requests from `http://localhost:3000`
- Allows credentials for authenticated requests

---

## Database Schema

```sql
CREATE TABLE vehicles (
  vehicle_id INT PRIMARY KEY AUTO_INCREMENT,
  user_id INT NOT NULL,
  plate_number VARCHAR(50) NOT NULL UNIQUE,
  model VARCHAR(100) NOT NULL,
  vehicle_type VARCHAR(20) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);
```

---

## Testing Examples

### Using cURL

**Add Vehicle:**
```bash
curl -X POST http://localhost:8080/api/vehicles \
  -H "Content-Type: application/json" \
  -d '{
    "userId": 1,
    "plateNumber": "ABC 1234",
    "model": "Toyota Vios",
    "vehicleType": "Car"
  }'
```

**Get User Vehicle:**
```bash
curl -X GET http://localhost:8080/api/vehicles/user/1
```

**Update Vehicle:**
```bash
curl -X PUT http://localhost:8080/api/vehicles/1 \
  -H "Content-Type: application/json" \
  -d '{
    "plateNumber": "XYZ 9999",
    "model": "Honda City",
    "vehicleType": "Car"
  }'
```

**Delete Vehicle:**
```bash
curl -X DELETE http://localhost:8080/api/vehicles/1
```

**Get All Vehicles:**
```bash
curl -X GET http://localhost:8080/api/vehicles
```

---

## Implementation Details

### Technologies Used:
- **Spring Boot 4.0.0**
- **Spring Data JPA** (ORM)
- **Hibernate** (Query builder)
- **MySQL 8.0** (Database)
- **SLF4J** (Logging)

### Key Features:
- ✅ All 5 API endpoints implemented
- ✅ Proper error handling with try-catch blocks
- ✅ Comprehensive logging (SLF4J)
- ✅ SQL injection prevention (JPA/Hibernate)
- ✅ Unique constraint validation for plate numbers
- ✅ Foreign key relationship with users table
- ✅ CORS enabled for React frontend
- ✅ RESTful API design
- ✅ Transactional operations with `@Transactional`
- ✅ DTO pattern for clean API responses
