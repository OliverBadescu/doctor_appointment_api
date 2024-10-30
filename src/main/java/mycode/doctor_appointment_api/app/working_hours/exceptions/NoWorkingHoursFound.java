package mycode.doctor_appointment_api.app.working_hours.exceptions;

public class NoWorkingHoursFound extends RuntimeException {

    public NoWorkingHoursFound(String message) {
        super(message);
    }

}
