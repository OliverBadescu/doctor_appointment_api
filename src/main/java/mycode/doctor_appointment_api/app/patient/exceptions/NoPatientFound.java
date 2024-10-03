package mycode.doctor_appointment_api.app.patient.exceptions;

public class NoPatientFound extends RuntimeException {
  public NoPatientFound(String message) {
    super(message);
  }
}
