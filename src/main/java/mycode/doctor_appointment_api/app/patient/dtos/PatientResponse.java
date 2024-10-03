package mycode.doctor_appointment_api.app.patient.dtos;

public record PatientResponse(int id, String fullName, String email, String password, String phone) {
}
