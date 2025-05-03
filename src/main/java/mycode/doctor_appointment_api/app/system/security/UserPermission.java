package mycode.doctor_appointment_api.app.system.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserPermission {

    USER_READ("read"),
    USER_WRITE("write"),
    APPOINTMENT_READ("appointment:read"),
    APPOINTMENT_WRITE("appointment:write");
    private final String permission;
}
