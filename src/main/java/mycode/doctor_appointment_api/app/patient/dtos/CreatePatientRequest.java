package mycode.doctor_appointment_api.app.patient.dtos;

public record CreatePatientRequest(String fullName, String email, String password, String phone) {
}
