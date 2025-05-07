package mycode.doctor_appointment_api.app.appointments.exceptions;

public class NoStatusFound extends RuntimeException {
    public NoStatusFound(String message) {
        super(message);
    }
}
