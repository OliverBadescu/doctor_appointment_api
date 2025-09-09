package mycode.doctor_appointment_api.app.global_exceptions;


import lombok.extern.slf4j.Slf4j;
import mycode.doctor_appointment_api.app.appointments.exceptions.AppointmentAlreadyExistsAtThisDateAndTime;
import mycode.doctor_appointment_api.app.appointments.exceptions.NoAppointmentFound;
import mycode.doctor_appointment_api.app.appointments.exceptions.NoStatusFound;
import mycode.doctor_appointment_api.app.clinic.exceptions.ClinicAlreadyExists;
import mycode.doctor_appointment_api.app.clinic.exceptions.NoClinicFound;
import mycode.doctor_appointment_api.app.doctor.exceptions.DoctorAlreadyExists;
import mycode.doctor_appointment_api.app.doctor.exceptions.NoDoctorFound;
import mycode.doctor_appointment_api.app.reviews.exceptions.NoReviewsFound;
import mycode.doctor_appointment_api.app.system.response.ApiResponse;
import mycode.doctor_appointment_api.app.users.exceptions.NoUserFound;
import mycode.doctor_appointment_api.app.users.exceptions.UserAlreadyExists;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Enhanced GlobalExceptionHandler with standardized API responses.
 * Handles all exceptions thrown by the application controllers and maps them
 * to standardized error responses with proper HTTP status codes.
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // Authentication & Authorization Exceptions
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<Object>> handleBadCredentials(BadCredentialsException ex) {
        log.warn("Bad credentials: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error("Invalid username or password", "AUTH_001"));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<Object>> handleAuthentication(AuthenticationException ex) {
        log.warn("Authentication failed: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error("Authentication failed", "AUTH_002"));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Object>> handleAccessDenied(AccessDeniedException ex) {
        log.warn("Access denied: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ApiResponse.error("Access denied", "AUTH_003"));
    }

    // Validation Exceptions
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        log.warn("Validation failed: {}", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.<Map<String, String>>builder()
                        .success(false)
                        .message("Validation failed")
                        .data(errors)
                        .errorCode("VALIDATION_001")
                        .timestamp(java.time.LocalDateTime.now())
                        .build());
    }

    // Database Exceptions
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<Object>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        log.error("Data integrity violation: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiResponse.error("Data integrity violation", "DB_001"));
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ApiResponse<Object>> handleDataAccess(DataAccessException ex) {
        log.error("Database access error: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Database error occurred", "DB_002"));
    }

    // Business Logic Exceptions
    @ExceptionHandler(NoAppointmentFound.class)
    public ResponseEntity<ApiResponse<Object>> handleAppointmentNotFoundException(NoAppointmentFound ex) {
        log.warn("Appointment not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(ex.getMessage(), "APPOINTMENT_001"));
    }

    @ExceptionHandler(AppointmentAlreadyExistsAtThisDateAndTime.class)
    public ResponseEntity<ApiResponse<Object>> handleAppointmentAlreadyExistsException(AppointmentAlreadyExistsAtThisDateAndTime ex) {
        log.warn("Appointment conflict: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiResponse.error(ex.getMessage(), "APPOINTMENT_002"));
    }

    @ExceptionHandler(NoDoctorFound.class)
    public ResponseEntity<ApiResponse<Object>> handleDoctorNotFoundException(NoDoctorFound ex) {
        log.warn("Doctor not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(ex.getMessage(), "DOCTOR_001"));
    }

    @ExceptionHandler(DoctorAlreadyExists.class)
    public ResponseEntity<ApiResponse<Object>> handleDoctorAlreadyException(DoctorAlreadyExists ex) {
        log.warn("Doctor already exists: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiResponse.error(ex.getMessage(), "DOCTOR_002"));
    }

    @ExceptionHandler(ClinicAlreadyExists.class)
    public ResponseEntity<ApiResponse<Object>> handleClinicAlreadyExistsException(ClinicAlreadyExists ex) {
        log.warn("Clinic already exists: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiResponse.error(ex.getMessage(), "CLINIC_001"));
    }

    @ExceptionHandler(NoClinicFound.class)
    public ResponseEntity<ApiResponse<Object>> handleNoClinicFoundException(NoClinicFound ex) {
        log.warn("Clinic not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(ex.getMessage(), "CLINIC_002"));
    }

    @ExceptionHandler(NoUserFound.class)
    public ResponseEntity<ApiResponse<Object>> handleNoUserFoundException(NoUserFound ex) {
        log.warn("User not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(ex.getMessage(), "USER_001"));
    }

    @ExceptionHandler(UserAlreadyExists.class)
    public ResponseEntity<ApiResponse<Object>> handleUserAlreadyExistsException(UserAlreadyExists ex) {
        log.warn("User already exists: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiResponse.error(ex.getMessage(), "USER_002"));
    }

    @ExceptionHandler(NoStatusFound.class)
    public ResponseEntity<ApiResponse<Object>> handleNoStatusFound(NoStatusFound ex) {
        log.warn("Status not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(ex.getMessage(), "STATUS_001"));
    }

    @ExceptionHandler(NoReviewsFound.class)
    public ResponseEntity<ApiResponse<Object>> handleNoReviewFound(NoReviewsFound ex) {
        log.warn("Reviews not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(ex.getMessage(), "REVIEW_001"));
    }

    // Generic Exception Handler
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGenericException(Exception ex) {
        log.error("Unexpected error occurred", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("An unexpected error occurred", "SYSTEM_001"));
    }
}