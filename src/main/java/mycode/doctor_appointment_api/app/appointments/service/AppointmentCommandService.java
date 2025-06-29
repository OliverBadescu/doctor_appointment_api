package mycode.doctor_appointment_api.app.appointments.service;

import mycode.doctor_appointment_api.app.appointments.dtos.AppointmentResponse;
import mycode.doctor_appointment_api.app.appointments.dtos.CreateAppointmentRequest;
import mycode.doctor_appointment_api.app.appointments.dtos.StatusUpdateRequest;
import mycode.doctor_appointment_api.app.appointments.dtos.UpdateAppointmentRequest;

/**
 * Service interface for handling appointment commands such as create, update, delete, and status changes.
 */
public interface AppointmentCommandService {

    /**
     * Creates a new appointment.
     *
     * @param createAppointmentRequest the appointment creation request
     * @return the created appointment
     */
    AppointmentResponse addAppointment(CreateAppointmentRequest createAppointmentRequest);

    /**
     * Updates an existing appointment.
     *
     * @param updateAppointmentRequest the update request
     * @param id the appointment ID
     * @return the updated appointment
     */
    AppointmentResponse updateAppointment(UpdateAppointmentRequest updateAppointmentRequest, int id);

    /**
     * Deletes an appointment by ID.
     *
     * @param id the appointment ID
     * @return the deleted appointment
     */
    AppointmentResponse deleteAppointment(int id);

    /**
     * Deletes a specific patient’s appointment.
     *
     * @param patientId the patient ID
     * @param appointmentId the appointment ID
     * @return the deleted appointment
     */
    AppointmentResponse deletePatientAppointment(int patientId, int appointmentId);

    /**
     * Updates the status of an appointment.
     *
     * @param status the status update request
     * @param appointmentId the appointment ID
     * @return the updated appointment
     */
    AppointmentResponse updateStatus(StatusUpdateRequest status, int appointmentId);
}
