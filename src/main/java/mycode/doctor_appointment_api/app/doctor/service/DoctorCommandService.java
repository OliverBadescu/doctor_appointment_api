package mycode.doctor_appointment_api.app.doctor.service;

import mycode.doctor_appointment_api.app.doctor.dtos.CreateDoctorRequest;
import mycode.doctor_appointment_api.app.doctor.dtos.DoctorResponse;
import mycode.doctor_appointment_api.app.doctor.dtos.UpdateDoctorRequest;

/**
 * Service interface for handling commands (create, update, delete)
 * related to Doctor entities.
 */
public interface DoctorCommandService {

    /**
     * Adds a new doctor using the provided request data.
     *
     * @param createDoctorRequest the DTO containing doctor creation details
     * @return a DoctorResponse DTO representing the newly created doctor
     */
    DoctorResponse addDoctor(CreateDoctorRequest createDoctorRequest);

    /**
     * Updates an existing doctor identified by the given ID using the provided data.
     *
     * @param updateDoctorRequest the DTO containing updated doctor details
     * @param id the unique identifier of the doctor to update
     * @return a DoctorResponse DTO representing the updated doctor
     */
    DoctorResponse updateDoctor(UpdateDoctorRequest updateDoctorRequest, int id);

    /**
     * Deletes the doctor with the specified ID.
     *
     * @param id the unique identifier of the doctor to delete
     * @return a DoctorResponse DTO representing the deleted doctor
     */
    DoctorResponse deleteDoctor(int id);

}
