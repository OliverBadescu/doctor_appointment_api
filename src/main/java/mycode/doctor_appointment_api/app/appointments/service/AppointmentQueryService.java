package mycode.doctor_appointment_api.app.appointments.service;

import mycode.doctor_appointment_api.app.appointments.dtos.AppointmentResponse;
import mycode.doctor_appointment_api.app.appointments.dtos.DoctorAppointmentList;
import mycode.doctor_appointment_api.app.appointments.dtos.PatientAppointmentList;


/**
 * Service interface for querying appointment-related data.
 * <p>
 * Provides methods to retrieve appointments for users, doctors, and general stats.
 */
public interface AppointmentQueryService {

    /**
     * Returns the total number of appointments in the system.
     *
     * @return total appointment count
     */
    int totalAppointments();

    /**
     * Retrieves a specific appointment by its ID.
     *
     * @param id the appointment ID
     * @return appointment details
     */
    AppointmentResponse getAppointment(int id);

    /**
     * Retrieves all appointments for a specific patient.
     *
     * @param id the patient (user) ID
     * @return list of patient's appointments
     */
    PatientAppointmentList getAllPatientAppointments(int id);

    /**
     * Retrieves all appointments for a specific doctor.
     *
     * @param id the doctor ID
     * @return list of doctor's appointments
     */
    DoctorAppointmentList getAllDoctorAppointments(int id);
}