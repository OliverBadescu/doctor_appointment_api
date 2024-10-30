package mycode.doctor_appointment_api.app.appointments.exceptions;

public class NoAppointmentFound extends RuntimeException {

    public NoAppointmentFound(String message) {
        super(message);
    }

}
