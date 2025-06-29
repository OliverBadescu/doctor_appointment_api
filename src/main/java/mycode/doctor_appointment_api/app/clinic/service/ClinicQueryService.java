package mycode.doctor_appointment_api.app.clinic.service;

import mycode.doctor_appointment_api.app.clinic.dtos.ClinicResponse;
import mycode.doctor_appointment_api.app.clinic.dtos.ClinicResponseList;

/**
 * Service interface for querying clinic information.
 */
public interface ClinicQueryService {

    /**
     * Retrieves a clinic by its ID.
     *
     * @param id the clinic ID
     * @return the clinic response DTO
     */
    ClinicResponse getClinicById(int id);

    /**
     * Retrieves all clinics.
     *
     * @return a list of clinic response DTOs
     */
    ClinicResponseList getAllClinics();

    /**
     * Returns the total number of clinics.
     *
     * @return total clinics count
     */
    int getTotalClinics();

}