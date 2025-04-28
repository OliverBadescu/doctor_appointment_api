package mycode.doctor_appointment_api.app.users.mock;

import mycode.doctor_appointment_api.app.system.security.UserRole;
import mycode.doctor_appointment_api.app.users.model.User;

public class UserMockData {

    public static User createUser(String email, String name) {
        return User.builder()
                .email(email)
                .fullName(name)
                .password("testPassword123")
                .userRole(UserRole.CLIENT)
                .build();
    }
}