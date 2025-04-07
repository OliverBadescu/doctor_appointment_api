package mycode.doctor_appointment_api.app.users.dtos;


import mycode.doctor_appointment_api.app.system.security.UserRole;

public record LoginResponse(String jwtToken,
                            Long id,
                            String fullName,
                            String email,
                            UserRole userRole) {
}
