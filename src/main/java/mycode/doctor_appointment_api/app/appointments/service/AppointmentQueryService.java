package mycode.doctor_appointment_api.app.appointments.service;

import mycode.doctor_appointment_api.app.appointments.dtos.AppointmentResponse;
import mycode.doctor_appointment_api.app.appointments.model.Appointment;

public interface AppointmentQueryService {

    AppointmentResponse getAppointment(int id);

}
