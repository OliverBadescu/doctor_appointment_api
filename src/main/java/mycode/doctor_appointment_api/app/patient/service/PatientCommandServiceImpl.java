package mycode.doctor_appointment_api.app.patient.service;

import lombok.AllArgsConstructor;
import mycode.doctor_appointment_api.app.doctor.exceptions.DoctorAlreadyExists;
import mycode.doctor_appointment_api.app.patient.dtos.CreatePatientRequest;
import mycode.doctor_appointment_api.app.patient.dtos.PatientResponse;
import mycode.doctor_appointment_api.app.patient.dtos.UpdatePatientRequest;
import mycode.doctor_appointment_api.app.patient.exceptions.NoPatientFound;
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
            if(patient1.getFullName().equals(patient.getFullName()) && patient1.getEmail().equals(patient.getEmail())){
                throw new PatientAlreadyExists("Patient with this name and email already exists");
            }
        });

        patientRepository.saveAndFlush(patient);

        return PatientMapper.patientToResponseDto(patient);
    }

    @Override
    public PatientResponse updatePatient(UpdatePatientRequest up, int id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new NoPatientFound("No patient with this id found"));


        patient.setEmail(up.email());
        patient.setFullName(up.email());
        patient.setPassword(up.password());
        patient.setPhone(up.phone());

        patientRepository.save(patient);

        return PatientMapper.patientToResponseDto(patient);
    }

    @Override
    public PatientResponse deletePatient(int id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new NoPatientFound("No patient with this id found"));

        PatientResponse patientResponse = PatientMapper.patientToResponseDto(patient);

        patientRepository.delete(patient);

        return patientResponse;
    }
}
