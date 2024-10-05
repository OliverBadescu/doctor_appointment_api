package mycode.doctor_appointment_api.app.patient.service;

import lombok.AllArgsConstructor;
import mycode.doctor_appointment_api.app.patient.dtos.CreatePatientRequest;
import mycode.doctor_appointment_api.app.patient.dtos.PatientResponse;
import mycode.doctor_appointment_api.app.patient.exceptions.PatientAlreadyExists;
import mycode.doctor_appointment_api.app.patient.mapper.PatientMapper;
import mycode.doctor_appointment_api.app.patient.model.Patient;
import mycode.doctor_appointment_api.app.patient.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class PatientCommandServiceImpl implements PatientCommandService{

    private PatientRepository patientRepository;

    @Override
    public PatientResponse addPatient(CreatePatientRequest createPatientRequest) {
        Patient patient = PatientMapper.patientRequestDtoToPatient(createPatientRequest);

        List<Patient> list = patientRepository.findAll();

        list.forEach(patient1 -> {
            if(patient1 == patient){
                throw new PatientAlreadyExists("Patient with this data already exists");
            }
        });


        return PatientMapper.patientToResponseDto(patient);
    }
}
