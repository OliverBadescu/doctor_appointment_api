# 🏥 Doctor Appointment App

A secure backend REST API built using **Java** and **Spring Boot**, designed for managing doctor appointments. Patients can book appointments, while doctors and admins can view statistics and manage appointment data. The app uses **JWT (JSON Web Token)** authentication to protect and restrict access based on user roles.

---

## 🚀 Features

- 📅 **Appointment Scheduling**: Patients can book, view, and cancel appointments.
- 👨‍⚕️ **Doctor Portal**: Doctors can access their schedule and statistics.
- 📊 **Admin Dashboard**: Admins can view global statistics and monitor clinic performance.
- 🔐 **JWT Authentication**: Secured login system with token-based authentication.
- ⚙️ **Role-based Access Control**: Separate privileges for patients, doctors, and admins.
- 🏥 **Clinic and Working Hours**: Doctors are associated with clinics and have defined working hours.
---

## 🛠️ Technologies Used

- **Java 17+**
- **Spring Boot**
- **Spring Security + JWT**
- **Spring Data JPA**
- **Hibernate**
- **MySQL** 
- **Lombok**
- **Swagger/OpenAPI** (for API docs)
- **Postman** (for manual testing)

---

## 🔐 Authentication & Authorization

### 🔑 JWT Auth Flow

1. **Login Endpoint**:  
   `POST /api/user/login`  
   Accepts username and password, returns a JWT token.

2. **Register Endpoint** (optional):  
   `POST /api/user/register`  
   Used to register new users with roles like `USER`, `DOCTOR`, or `ADMIN`.

3. **Token Usage**:  
   Add the JWT token to the `Authorization` header:
    Authorization: Bearer <your_token_here>

4. **Role-Based Access**:
- `USER`: Can book and view own appointments.
- `DOCTOR`: Can view their own appointments and working hours.
- `ADMIN`: Can access statistics and manage clinics/doctors.


   
