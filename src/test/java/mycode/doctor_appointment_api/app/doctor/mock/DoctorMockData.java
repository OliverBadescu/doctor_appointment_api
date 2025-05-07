package mycode.doctor_appointment_api.app.doctor.mock;

import mycode.doctor_appointment_api.app.clinic.mock.ClinicMockData;
import mycode.doctor_appointment_api.app.doctor.model.Doctor;
import mycode.doctor_appointment_api.app.system.security.UserRole;

public class DoctorMockData {

    public static Doctor createDoctor() {
        return Doctor.builder()
                .id(1)
                .email("johndoe@example.com")
                .password("securePass123")
                .fullName("John Doe")
                .phone("+1234567890")
                .specialization("Cardiology")
                .clinic(ClinicMockData.createClinic())
                .userRole(UserRole.DOCTOR)
                .build();
    }
    
}
