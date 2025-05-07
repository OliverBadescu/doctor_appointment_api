package mycode.doctor_appointment_api.app.appointments.enums;


public enum AppointmentStatus {
    UPCOMING("upcoming"),
    COMPLETED("completed"),
    CANCELLED("cancelled");

    private final String value;

    AppointmentStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static AppointmentStatus fromString(String status) {
        if (status == null) {
            return UPCOMING;
        }

        for (AppointmentStatus appointmentStatus : AppointmentStatus.values()) {
            if (appointmentStatus.value.equalsIgnoreCase(status)) {
                return appointmentStatus;
            }
        }

        return UPCOMING;
    }
}