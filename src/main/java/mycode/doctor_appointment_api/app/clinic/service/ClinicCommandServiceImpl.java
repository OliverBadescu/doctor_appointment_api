package mycode.doctor_appointment_api.app.clinic.service;

import lombok.AllArgsConstructor;
import mycode.doctor_appointment_api.app.clinic.dtos.ClinicResponse;
import mycode.doctor_appointment_api.app.clinic.dtos.CreateClinicRequest;
import mycode.doctor_appointment_api.app.clinic.dtos.UpdateClinicRequest;
import mycode.doctor_appointment_api.app.clinic.exceptions.ClinicAlreadyExists;
import mycode.doctor_appointment_api.app.clinic.exceptions.NoClinicFound;
import mycode.doctor_appointment_api.app.clinic.mapper.ClinicMapper;
import mycode.doctor_appointment_api.app.clinic.model.Clinic;
import mycode.doctor_appointment_api.app.clinic.repository.ClinicRepository;
import org.springframework.stereotype.Service;


/**
 * Implementation of {@link ClinicCommandService} for managing clinics.
 * Provides methods to add, update, and delete clinics with validation and mapping.
 */
@Service
@AllArgsConstructor
public class ClinicCommandServiceImpl implements ClinicCommandService {

    private ClinicRepository clinicRepository;

    @Override
    public ClinicResponse addClinic(CreateClinicRequest createClinicRequest) {
        Clinic clinic = Clinic.builder()
                .address(createClinicRequest.address())
                .name(createClinicRequest.name()).build();


        clinicRepository.findAll().forEach(clinic1 -> {
            if (clinic1.getAddress().equals(clinic.getAddress()) && clinic1.getName().equals(clinic.getName())) {
                throw new ClinicAlreadyExists("Clinic with this name and address already exists");
            }
        });

        clinicRepository.saveAndFlush(clinic);

        return ClinicMapper.clinicToResponseDto(clinic);
    }

    @Override
    public ClinicResponse updateClinic(int id, UpdateClinicRequest updateClinicRequest) {
        Clinic clinic = clinicRepository.findById(id)
                .orElseThrow(() -> new NoClinicFound("No clinic with this id found"));

        clinic.setAddress(updateClinicRequest.address());
        clinic.setName(updateClinicRequest.name());

        clinicRepository.save(clinic);

        return ClinicMapper.clinicToResponseDto(clinic);
    }

    @Override
    public ClinicResponse deleteClinic(int id) {
        Clinic clinic = clinicRepository.findById(id)
                .orElseThrow(() -> new NoClinicFound("No clinic with this id found"));

        ClinicResponse clinicResponse = ClinicMapper.clinicToResponseDto(clinic);

        clinicRepository.delete(clinic);


        return clinicResponse;
    }
}
