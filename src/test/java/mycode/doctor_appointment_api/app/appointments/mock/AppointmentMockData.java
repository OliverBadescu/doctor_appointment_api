package mycode.doctor_appointment_api.app.appointments.mock;

import mycode.doctor_appointment_api.app.appointments.model.Appointment;
import mycode.doctor_appointment_api.app.doctor.mock.DoctorMockData;
import mycode.doctor_appointment_api.app.doctor.model.Doctor;
import mycode.doctor_appointment_api.app.users.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public class AppointmentMockData {

    public static Appointment createAppointment(int id, LocalDate date, int startHour, int startMinute, int durationMinutes) {
        LocalDateTime start = date.atTime(startHour, startMinute);
        LocalDateTime end = start.plusMinutes(durationMinutes);
        Doctor doctor = DoctorMockData.createDoctor();
        doctor.setId(1);
        return Appointment.builder()
                .id(id)
                .start(start)
                .end(end)
                .reason("Checkup")
                .doctor(doctor)
                .user(null)
                .build();
    }


}
