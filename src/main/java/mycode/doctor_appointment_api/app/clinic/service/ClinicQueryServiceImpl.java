package mycode.doctor_appointment_api.app.clinic.service;

import lombok.AllArgsConstructor;
import mycode.doctor_appointment_api.app.clinic.dtos.ClinicResponse;
import mycode.doctor_appointment_api.app.clinic.exceptions.NoClinicFound;
import mycode.doctor_appointment_api.app.clinic.mapper.ClinicMapper;
import mycode.doctor_appointment_api.app.clinic.model.Clinic;
import mycode.doctor_appointment_api.app.clinic.repository.ClinicRepository;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ClinicQueryServiceImpl implements ClinicQueryService {

    private ClinicRepository clinicRepository;

    @Override
    public ClinicResponse getClinicById(int id) {
        Clinic clinic = clinicRepository.findById(id)
                .orElseThrow(() -> new NoClinicFound("No clinic with this id found"));


        return ClinicMapper.clinicToResponseDto(clinic);
    }
}
