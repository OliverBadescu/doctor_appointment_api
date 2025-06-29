package mycode.doctor_appointment_api.app.clinic.service;

import lombok.AllArgsConstructor;
import mycode.doctor_appointment_api.app.clinic.dtos.ClinicResponse;
import mycode.doctor_appointment_api.app.clinic.dtos.ClinicResponseList;
import mycode.doctor_appointment_api.app.clinic.exceptions.NoClinicFound;
import mycode.doctor_appointment_api.app.clinic.mapper.ClinicMapper;
import mycode.doctor_appointment_api.app.clinic.model.Clinic;
import mycode.doctor_appointment_api.app.clinic.repository.ClinicRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link ClinicQueryService} to handle clinic queries.
 */
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

    @Override
    public ClinicResponseList getAllClinics() {
        List<Clinic> clinicList = clinicRepository.findAll();

        if (clinicList.isEmpty()){
            throw new NoClinicFound("No clinics found");
        }

        List<ClinicResponse> list = new ArrayList<>();

        clinicList.forEach(clinic -> {
            list.add(ClinicMapper.clinicToResponseDto(clinic));
        });

        return new ClinicResponseList(list);
    }

    @Override
    public int getTotalClinics(){
        return clinicRepository.findAll().size();
    }
}
