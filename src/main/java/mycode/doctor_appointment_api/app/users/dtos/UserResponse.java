package mycode.doctor_appointment_api.app.users.dtos;

import mycode.doctor_appointment_api.app.system.security.UserRole;

public record UserResponse(long id, String email, String password, String fullName, UserRole userRole) {
}
