# 🚀 Quick Start Guide

## Run These Commands in Order

### 1. Backup Database (Optional but Recommended)
```powershell
mysqldump -u root -p02102020 parkway > parkway_backup_$(Get-Date -Format "yyyyMMdd_HHmmss").sql
```

### 2. Update Database Schema
```powershell
mysql -u root -p02102020 parkway < database_migration.sql
```

### 3. Compile Backend
```powershell
mvn clean compile
```

### 4. Start Backend
```powershell
mvn spring-boot:run
```

### 5. Initialize Parking Slots (Run Once After Backend Starts)
Open a new terminal and run:
```powershell
curl -X POST http://localhost:8080/api/parking-slots/initialize
```

Or use this PowerShell command:
```powershell
Invoke-WebRequest -Uri "http://localhost:8080/api/parking-slots/initialize" -Method POST
```

### 6. Start Frontend
```powershell
cd path\to\your\frontend
npm start
```

---

## 🧪 Test the Complete Flow

1. **Register Admin:**
   - Go to admin registration
   - Fill in: email, password, firstname, lastname, parking_lot_name, capacity, price, location
   - Submit → Admin created + Parking slots auto-created

2. **Login as Admin:**
   - Use registered email and password
   - Verify parking lot details show correctly

3. **Register User:**
   - Go to user registration
   - Fill in: email, password, firstname, lastname
   - Submit → User account created

4. **Register Vehicle (as User):**
   - Login as user
   - Go to vehicle registration
   - Enter: plate_number, vehicle_type, model
   - Submit → Vehicle saved

5. **Create Booking (as User):**
   - Select parking lot
   - Choose date, time in, time out
   - Submit → Booking created with status "pending"
   - Admin receives notification

6. **Confirm Booking (as Admin):**
   - Login as admin
   - View bookings list
   - Click confirm on pending booking
   - Verify: booking status → "confirmed", one slot → "occupied"
   - User receives confirmation notification

7. **Verify Parking Slots:**
   - Check parking slots display
   - Verify occupied count increased by 1
   - Verify total capacity is correct

---

## ✅ Success Indicators

You'll know everything is working when:

- ✅ Admin registration creates parking lot AND auto-creates all parking slots
- ✅ User can book a parking spot
- ✅ Admin confirmation changes booking status to "confirmed" and marks a slot as "occupied"
- ✅ Deleting a confirmed booking frees up one occupied slot
- ✅ Notifications appear for both admin and user
- ✅ All parking slot counts are accurate
- ✅ No console errors in frontend or backend

---

## 🐛 Troubleshooting

### Backend won't start:
```powershell
# Check for compilation errors
mvn clean compile

# Check errors
mvn spring-boot:run
```

### Database connection error:
- Verify MySQL is running
- Check credentials in `src/main/resources/application.properties`
- Database: `parkway`, User: `root`, Password: `02102020`

### Foreign key errors after migration:
- Drop and recreate database if migration fails
- Let Spring Boot auto-create tables
- Call `/api/parking-slots/initialize` to populate slots

### Parking slots not showing:
- Call `POST /api/parking-slots/initialize` once
- Check backend logs for slot creation messages
- Verify admin has capacity > 0

### CORS errors:
- Verify backend has `@CrossOrigin(origins = "http://localhost:3000")`
- Check SecurityConfig.java allows localhost:3000
- Clear browser cache

---

## 📞 Need Help?

Check these files for details:
- **BACKEND_SYNC_COMPLETE.md** - Full documentation
- **database_migration.sql** - Database schema changes
- Backend logs in terminal for error messages
