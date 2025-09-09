package mycode.doctor_appointment_api.app.appointments.service;

import mycode.doctor_appointment_api.app.appointments.dtos.AppointmentResponse;
import mycode.doctor_appointment_api.app.appointments.dtos.DoctorAppointmentList;
import mycode.doctor_appointment_api.app.appointments.dtos.PatientAppointmentList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface AppointmentQueryService {

    int totalAppointments();

    AppointmentResponse getAppointment(int id);

    PatientAppointmentList getAllPatientAppointments(int id);

    DoctorAppointmentList getAllDoctorAppointments(int id);
    
    Page<AppointmentResponse> getAllAppointmentsPaginated(Pageable pageable);
    Page<AppointmentResponse> getPatientAppointmentsPaginated(int patientId, Pageable pageable);
    Page<AppointmentResponse> getDoctorAppointmentsPaginated(int doctorId, Pageable pageable);
}