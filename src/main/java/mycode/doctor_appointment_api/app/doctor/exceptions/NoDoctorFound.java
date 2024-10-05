package mycode.doctor_appointment_api.app.doctor.exceptions;

public class NoDoctorFound extends RuntimeException {
  public NoDoctorFound(String message) {
    super(message);
  }
}
