package mycode.doctor_appointment_api.app.patient.service;


import lombok.AllArgsConstructor;
import mycode.doctor_appointment_api.app.patient.dtos.PatientResponse;
import mycode.doctor_appointment_api.app.patient.exceptions.NoPatientFound;
import mycode.doctor_appointment_api.app.patient.mapper.PatientMapper;
import mycode.doctor_appointment_api.app.patient.model.Patient;
import mycode.doctor_appointment_api.app.patient.repository.PatientRepository;
import org.springframework.stereotype.Service;

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
}
