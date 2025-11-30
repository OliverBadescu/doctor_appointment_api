package mycode.doctor_appointment_api.app.appointments.service;

import mycode.doctor_appointment_api.app.appointments.dto.AppointmentResponse;
import mycode.doctor_appointment_api.app.appointments.dto.CreateAppointmentRequest;
import mycode.doctor_appointment_api.app.appointments.dto.StatusUpdateRequest;
import mycode.doctor_appointment_api.app.appointments.dto.UpdateAppointmentRequest;

public interface AppointmentCommandService {

    AppointmentResponse addAppointment(CreateAppointmentRequest createAppointmentRequest);

    AppointmentResponse updateAppointment(UpdateAppointmentRequest updateAppointmentRequest, int id);

    AppointmentResponse deleteAppointment(int id);

    AppointmentResponse deletePatientAppointment(int patientId, int appointmentId);

    AppointmentResponse updateStatus(StatusUpdateRequest status, int appointmentId);

    AppointmentResponse confirmAppointment(String confirmationToken);
}
