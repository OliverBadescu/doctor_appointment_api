package mycode.doctor_appointment_api.app.patient.exceptions;

public class PatientAlreadyExists extends RuntimeException {
    public PatientAlreadyExists(String message) {
        super(message);
    }
}
