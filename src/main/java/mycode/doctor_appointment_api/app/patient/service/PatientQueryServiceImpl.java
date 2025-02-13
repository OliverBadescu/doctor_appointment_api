package mycode.doctor_appointment_api.app.patient.service;


import lombok.AllArgsConstructor;
import mycode.doctor_appointment_api.app.patient.dtos.PatientResponse;
import mycode.doctor_appointment_api.app.patient.dtos.PatientResponseList;
import mycode.doctor_appointment_api.app.patient.exceptions.NoPatientFound;
import mycode.doctor_appointment_api.app.patient.mapper.PatientMapper;
import mycode.doctor_appointment_api.app.patient.model.Patient;
import mycode.doctor_appointment_api.app.patient.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class PatientQueryServiceImpl implements PatientQueryService {

    private PatientRepository patientRepository;

    @Override
    public PatientResponse findPatientById(int id) {

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new NoPatientFound("No patient with this id found"));

        return PatientMapper.patientToResponseDto(patient);
    }

    @Override
    public PatientResponseList getAllPatients() {
        List<Patient> list = patientRepository.findAll();

        List<PatientResponse> responses = new ArrayList<>();

        list.forEach(patient -> {
            responses.add(PatientMapper.patientToResponseDto(patient));

        });

        return new PatientResponseList(responses);
    }
}
