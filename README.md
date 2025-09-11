# 🏥 Doctor Appointment API

A secure, enterprise-grade backend REST API built with **Java 17** and **Spring Boot**, designed for comprehensive management of doctor appointments. The system supports multiple user roles, advanced features like pagination, comprehensive monitoring, and production-ready configurations.

---

## 🚀 Key Features

### 📅 **Appointment Management**
- **Complete CRUD Operations**: Create, read, update, and delete appointments
- **Advanced Scheduling**: Support for appointment time validation and conflict detection
- **Status Management**: Track appointment status (UPCOMING, COMPLETED, CANCELLED)
- **Paginated Lists**: Efficient handling of large datasets with sorting and filtering

### 👥 **Multi-Role System**
- **Patients (CLIENT)**: Book, view, and manage their appointments
- **Doctors**: Access their schedule, manage appointments, and view patient information
- **Administrators**: Complete system oversight, user management, and analytics

### 🔐 **Enterprise Security**
- **JWT Authentication**: Stateless, secure token-based authentication
- **Role-based Access Control**: Granular permissions system
- **Input Validation**: Comprehensive request validation with detailed error messages
- **Password Security**: BCrypt encryption with strong password policies

### 📊 **Monitoring & Health Checks**
- **Custom Health Indicators**: Database connectivity monitoring
- **Actuator Endpoints**: Production-ready monitoring with Prometheus metrics
- **Comprehensive Logging**: Structured logging across all environments

### 🏗️ **Enterprise Architecture**
- **Standardized API Responses**: Consistent response format with error codes
- **Global Exception Handling**: Centralized error management
- **Environment Configurations**: Separate configs for dev, test, production, and Docker
- **Transaction Management**: Proper database transaction handling
- **Caching Support**: Built-in caching with Caffeine

---

## 🛠️ Technology Stack

### Core Technologies
- **Java 17** - Modern JVM with latest features
- **Spring Boot 3.1.3** - Enterprise application framework
- **Spring Security** - Comprehensive security framework
- **Spring Data JPA** - Data persistence layer
- **Hibernate** - ORM framework

### Database
- **MySQL 8.0** - Primary database (production)
- **H2** - In-memory database (testing)

### Security & Authentication
- **JWT (JSON Web Tokens)** - Stateless authentication
- **BCrypt** - Password hashing
- **Spring Security** - Authorization framework

### Monitoring & Documentation
- **Spring Boot Actuator** - Production monitoring
- **Prometheus** - Metrics collection
- **OpenAPI/Swagger** - API documentation
- **Slf4j + Logback** - Logging framework

### Development & Build Tools
- **Maven** - Project management and build
- **Lombok** - Code generation
- **MapStruct** - Object mapping
- **Docker** - Containerization

---

## 🐳 Docker Setup

### Prerequisites
- Docker and Docker Compose installed
- Ports 3000, 8080, and 3308 available

### Quick Start with Docker

1. **Clone the repository**:
   ```bash
   git clone <repository-url>
   cd doctor_appointment_api
   ```

2. **Start the entire application stack**:
   ```bash
   docker-compose up -d
   ```

3. **Services will be available at**:
   - **API**: http://localhost:8080/api
   - **MySQL**: localhost:3308
   - **Health Check**: http://localhost:8080/api/actuator/health

### Docker Services

The `docker-compose.yaml` includes:

- **MySQL Database** (`mysql-container-doctor-appointment-api`)
  - Port: 3308
  - Database: `doctor_appointment_api`
  - Credentials: root/root

- **Spring Boot API** (`service-container-doctor-appointment-api`)
  - Port: 8080
  - Profile: docker
  - Auto-restart enabled

- **React Frontend** (`client-container-doctor-appointment-api`)
  - Port: 3000
  - Nginx-based serving

### Build and Deploy Custom Images

1. **Build the Spring Boot application**:
   ```bash
   ./mvnw clean package -DskipTests
   ```

2. **Build Docker image**:
   ```bash
   docker build -t doctor-appointment-api .
   ```

3. **Run with custom image**:
   ```bash
   # Update docker-compose.yaml to use your image
   docker-compose up -d
   ```

### Environment Variables

Configure the following environment variables for production:

```bash
# Database Configuration
DB_USERNAME=your_db_user
DB_PASSWORD=your_db_password
DATABASE_URL=jdbc:mysql://your-host:3306/doctor_appointment_api

# JWT Configuration
JWT_SECRET=your-super-secure-jwt-secret-key
JWT_EXPIRATION=3600000

# Server Configuration
SERVER_PORT=8080
APP_PROFILE=prod
```

---

## 💻 Local Development Setup

### Prerequisites
- Java 17+
- Maven 3.6+
- MySQL 8.0 (or use Docker for database only)

### Development Setup

1. **Clone and navigate to project**:
   ```bash
   git clone <repository-url>
   cd doctor_appointment_api
   ```

2. **Start MySQL Database** (Docker):
   ```bash
   docker run --name mysql-dev \
     -e MYSQL_ROOT_PASSWORD=Makona200512 \
     -e MYSQL_DATABASE=doctor_appointment_api_dev \
     -p 3306:3306 -d mysql:8.0
   ```

3. **Run the application**:
   ```bash
   ./mvnw spring-boot:run -Dspring.profiles.active=dev
   ```

4. **Access the application**:
   - API: http://localhost:8080/api
   - Health: http://localhost:8080/api/actuator/health
   - API Docs: http://localhost:8080/api/swagger-ui.html

### Available Profiles

- **dev** - Development with detailed logging
- **test** - Testing with H2 in-memory database
- **prod** - Production with optimized settings
- **docker** - Container-specific configuration

---

## 📡 API Endpoints

### Authentication Endpoints
```http
POST /api/v1/user/login          # User login
POST /api/v1/user/register       # User registration
GET  /api/v1/user/getUserRole    # Get current user role
```

### User Management (Admin)
```http
GET    /api/v1/user/users                    # Paginated users list
GET    /api/v1/user/getAllUsers              # All users (legacy)
GET    /api/v1/user/getUserById/{id}         # Get user by ID
POST   /api/v1/user/add                      # Create user
PUT    /api/v1/user/update/{id}              # Update user
DELETE /api/v1/user/delete/{id}              # Delete user
GET    /api/v1/user/totalUsers               # User count
```

### Appointment Management
```http
# CRUD Operations
POST   /api/v1/appointment/addAppointment           # Create appointment
GET    /api/v1/appointment/getAppointment/{id}      # Get appointment
PUT    /api/v1/appointment/updateAppointment/{id}   # Update appointment
DELETE /api/v1/appointment/deleteAppointment/{id}   # Delete appointment

# Paginated Lists
GET    /api/v1/appointment/appointments             # All appointments (paginated, Admin)
GET    /api/v1/appointment/patient/{id}/appointments   # Patient appointments (paginated)
GET    /api/v1/appointment/doctor/{id}/appointments    # Doctor appointments (paginated)

# Legacy Endpoints
GET    /api/v1/appointment/patient/{id}             # Patient appointments (legacy)
GET    /api/v1/appointment/doctor/{id}              # Doctor appointments (legacy)

# Status Management
PUT    /api/v1/appointment/updateStatus/{id}        # Update appointment status
GET    /api/v1/appointment/getTotalAppointments     # Appointment count
```

### Health & System Endpoints
```http
GET /api/v1/system/health          # Application health
GET /api/v1/system/health/database # Database health
GET /api/v1/system/info            # Application info
GET /api/actuator/health           # Actuator health
GET /api/actuator/metrics          # Prometheus metrics
```

### Pagination Parameters
All paginated endpoints support:
- `page` (default: 0) - Page number
- `size` (default: 10) - Items per page
- `sortBy` (default: varies) - Field to sort by
- `sortDir` (default: asc) - Sort direction (asc/desc)

Example:
```http
GET /api/v1/user/users?page=0&size=20&sortBy=fullName&sortDir=asc
```

---

## 🔐 Authentication & Authorization

### JWT Token Flow

1. **Login** with credentials:
   ```json
   POST /api/v1/user/login
   {
     "email": "user@example.com",
     "password": "SecurePass123!"
   }
   ```

2. **Receive JWT token** in response header:
   ```http
   Jwt-Token: eyJhbGciOiJIUzI1NiJ9...
   ```

3. **Use token** in subsequent requests:
   ```http
   Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
   ```

### Role-Based Permissions

| Endpoint | CLIENT | DOCTOR | ADMIN |
|----------|--------|--------|-------|
| Book Appointment | ✅ | ❌ | ✅ |
| View Own Appointments | ✅ | ✅ | ✅ |
| View All Appointments | ❌ | ❌ | ✅ |
| Manage Users | ❌ | ❌ | ✅ |
| Update Appointment Status | ❌ | ✅ | ✅ |
| System Health | ❌ | ❌ | ✅ |

---

## 🧪 Testing

### Run Tests
```bash
# All tests
./mvnw test

# Specific test class
./mvnw test -Dtest=AppointmentControllerTest

# Integration tests
./mvnw test -Dtest=*IntegrationTest
```

### Test Database
Tests use H2 in-memory database with the `test` profile automatically.

---

## 📈 Monitoring & Health Checks

### Built-in Health Checks
- **Database connectivity**
- **Application status**
- **Custom health indicators**

### Metrics Available
- **HTTP request metrics**
- **JVM metrics**
- **Database connection pool**
- **Custom business metrics**

### Production Monitoring
```bash
# Health check
curl http://localhost:8080/api/actuator/health

# Prometheus metrics
curl http://localhost:8080/api/actuator/prometheus

# Application info
curl http://localhost:8080/api/v1/system/info
```

---

## 🔧 Configuration

### Environment-Specific Settings

Each environment has optimized configurations:

- **Development**: Debug logging, SQL tracing, auto-reload
- **Production**: Minimal logging, connection pooling, security hardening
- **Docker**: Container-optimized settings
- **Testing**: In-memory database, fast startup

### Key Configuration Properties
```yaml
# JWT Settings
jwt:
  secret: ${JWT_SECRET}
  expiration: ${JWT_EXPIRATION:3600000}

# Database
spring:
  datasource:
    url: ${DATABASE_URL}
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}

# Caching
spring:
  cache:
    type: caffeine
    caffeine:
      spec: maximumSize=1000,expireAfterWrite=300s
```

---

## 🚀 Deployment

### Production Deployment with Docker

1. **Set environment variables**:
   ```bash
   export JWT_SECRET="your-super-secure-secret"
   export DB_USERNAME="prod_user"
   export DB_PASSWORD="secure_password"
   export DATABASE_URL="jdbc:mysql://prod-db:3306/doctor_appointment_api"
   ```

2. **Deploy with Docker Compose**:
   ```bash
   docker-compose -f docker-compose.prod.yml up -d
   ```

### Traditional Deployment

1. **Build application**:
   ```bash
   ./mvnw clean package -DskipTests
   ```

2. **Run with production profile**:
   ```bash
   java -jar target/doctor_appointment-api-0.0.1-SNAPSHOT.jar \
     --spring.profiles.active=prod \
     --server.port=8080
   ```

---

## 📚 API Documentation

### Interactive API Documentation
- **Swagger UI**: http://localhost:8080/api/swagger-ui.html
- **OpenAPI Spec**: http://localhost:8080/api/api-docs

### Example Requests

**Create Appointment**:
```json
POST /api/v1/appointment/addAppointment
{
  "start": "2024-12-15 09:00",
  "end": "2024-12-15 09:30",
  "reason": "Regular checkup",
  "doctorName": "Dr. Smith",
  "patientId": 1
}
```

**Register User**:
```json
POST /api/v1/user/register
{
  "fullName": "John Doe",
  "email": "john.doe@example.com",
  "password": "SecurePass123!"
}
```

---

## 🔒 Security Best Practices

- **Strong Password Policy**: Minimum 8 characters with complexity requirements
- **JWT Token Expiration**: Configurable token lifetime
- **HTTPS Only**: Production deployment should use HTTPS
- **Input Validation**: All inputs validated with detailed error messages
- **SQL Injection Protection**: Parameterized queries via JPA
- **CORS Configuration**: Properly configured for frontend integration

---

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

---

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

---

## 📞 Support

For support and questions:
- Create an issue in the repository
- Contact the development team
- Check the API documentation for detailed endpoint information

---

**Built with ❤️ using Spring Boot and modern Java practices**
