package mycode.doctor_appointment_api.app.doctor.exceptions;

public class DoctorAlreadyExists extends RuntimeException {
  public DoctorAlreadyExists(String message) {
    super(message);
  }
}
