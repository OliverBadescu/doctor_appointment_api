package mycode.doctor_appointment_api.app.appointments.service;

import mycode.doctor_appointment_api.app.appointments.dtos.AppointmentResponse;
import mycode.doctor_appointment_api.app.appointments.dtos.DoctorAppointmentList;
import mycode.doctor_appointment_api.app.appointments.dtos.PatientAppointmentList;

public interface AppointmentQueryService {

    AppointmentResponse getAppointment(int id);

    PatientAppointmentList getAllPatientAppointments(int id);

    DoctorAppointmentList getAllDoctorAppointments(int id);
}
