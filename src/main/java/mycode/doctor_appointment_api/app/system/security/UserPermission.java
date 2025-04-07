package mycode.doctor_appointment_api.app.system.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserPermission {

    USER_READ("user:read"),
    USER_WRITE("user:write"),
    ORDER_WRITE("order:write"),
    ORDER_READ("order:read"),
    PRODUCT_WRITE("product:write"),
    PRODUCT_READ("product:read");
    private final String permission;
}
