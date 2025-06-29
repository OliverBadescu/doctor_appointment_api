package mycode.doctor_appointment_api.app.clinic.service;

import mycode.doctor_appointment_api.app.clinic.dtos.ClinicResponse;
import mycode.doctor_appointment_api.app.clinic.dtos.CreateClinicRequest;
import mycode.doctor_appointment_api.app.clinic.dtos.UpdateClinicRequest;


/**
 * Service interface for managing clinics.
 * Supports creating, updating, and deleting clinic records.
 */
public interface ClinicCommandService {

    /**
     * Adds a new clinic.
     *
     * @param createClinicRequest the clinic creation data
     * @return the created clinic response
     */
    ClinicResponse addClinic(CreateClinicRequest createClinicRequest);

    /**
     * Updates an existing clinic by ID.
     *
     * @param id the clinic ID
     * @param updateClinicRequest the updated clinic data
     * @return the updated clinic response
     */
    ClinicResponse updateClinic(int id, UpdateClinicRequest updateClinicRequest);

    /**
     * Deletes a clinic by ID.
     *
     * @param id the clinic ID
     * @return the deleted clinic response
     */
    ClinicResponse deleteClinic(int id);
}
