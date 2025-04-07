package mycode.doctor_appointment_api.app.users.dtos;


import mycode.doctor_appointment_api.app.system.security.UserRole;

public record RegisterResponse(String jwtToken,
                               String fullName,
                               String email,
                               UserRole userRole) {
}
