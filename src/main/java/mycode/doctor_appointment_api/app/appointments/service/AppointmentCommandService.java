package mycode.doctor_appointment_api.app.appointments.service;

import mycode.doctor_appointment_api.app.appointments.dtos.AppointmentResponse;
import mycode.doctor_appointment_api.app.appointments.dtos.CreateAppointmentRequest;
import mycode.doctor_appointment_api.app.appointments.dtos.UpdateAppointmentRequest;
import mycode.doctor_appointment_api.app.appointments.model.Appointment;

public interface AppointmentCommandService {

    AppointmentResponse addAppointment(CreateAppointmentRequest createAppointmentRequest);

    AppointmentResponse updateAppointment(UpdateAppointmentRequest updateAppointmentRequest, int id);

    AppointmentResponse deleteAppointment(int id);

}
