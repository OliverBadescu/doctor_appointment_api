package mycode.doctor_appointment_api.app.system.email;

public interface EmailService {
    void sendConfirmationEmail(String to, String patientName, String doctorName,
                               String appointmentTime, String confirmationToken);
}
