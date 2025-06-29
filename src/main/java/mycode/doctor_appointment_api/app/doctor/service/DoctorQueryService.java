package mycode.doctor_appointment_api.app.doctor.service;

import mycode.doctor_appointment_api.app.appointments.dtos.DoctorAppointmentList;
import mycode.doctor_appointment_api.app.doctor.dtos.AvailableDoctorTimes;
import mycode.doctor_appointment_api.app.doctor.dtos.AvailableDoctorTimesDays;
import mycode.doctor_appointment_api.app.doctor.dtos.DoctorResponse;
import mycode.doctor_appointment_api.app.doctor.dtos.DoctorResponseList;
import mycode.doctor_appointment_api.app.doctor.model.Doctor;

import java.time.LocalDate;

/**
 * Service interface for querying doctor-related data.
 */
public interface DoctorQueryService {

    /**
     * Finds a doctor entity by email.
     *
     * @param email the doctor's email
     * @return the Doctor entity matching the email
     */
    Doctor findByEmail(String email);

    /**
     * Retrieves a doctor's details by their ID.
     *
     * @param id the doctor's ID
     * @return a DoctorResponse DTO with doctor details
     */
    DoctorResponse getDoctorById(int id);

    /**
     * Retrieves available appointment times for a doctor on a specific date.
     *
     * @param id the doctor's ID
     * @param date the date for which to get available times
     * @return an AvailableDoctorTimes DTO with available time slots for the date
     */
    AvailableDoctorTimes getDoctorAvailableTime(int id, LocalDate date);

    /**
     * Retrieves available appointment times for a doctor over a range of dates.
     *
     * @param id the doctor's ID
     * @param start the start date of the range
     * @param end the end date of the range
     * @return an AvailableDoctorTimesDays DTO with available time slots for each day in the range
     */
    AvailableDoctorTimesDays getDoctorAvailableTimeDifferentDays(int id, LocalDate start, LocalDate end);

    /**
     * Retrieves a list of all doctors.
     *
     * @return a DoctorResponseList DTO containing all doctors
     */
    DoctorResponseList getAllDoctors();

    /**
     * Gets the total count of doctors.
     *
     * @return the total number of doctors
     */
    int totalDoctors();

    /**
     * Retrieves all appointments for a specific doctor.
     *
     * @param id the doctor's ID
     * @return a DoctorAppointmentList DTO containing the doctor's appointments
     */
    DoctorAppointmentList getAllDoctorAppointments(int id);

}
