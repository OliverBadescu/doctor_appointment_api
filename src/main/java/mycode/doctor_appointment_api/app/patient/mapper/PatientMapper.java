package mycode.doctor_appointment_api.app.patient.mapper;

import mycode.doctor_appointment_api.app.patient.dtos.CreatePatientRequest;
import mycode.doctor_appointment_api.app.patient.dtos.PatientResponse;
import mycode.doctor_appointment_api.app.patient.model.Patient;

public class PatientMapper {

    public static PatientResponse patientToResponseDto(Patient patient) {
        return new PatientResponse(
                patient.getId(),
                patient.getFullName(),
                patient.getEmail(),
                patient.getPassword(),
                patient.getPhone());
    }

    public static Patient patientRequestDtoToPatient(CreatePatientRequest createPatientRequest) {
        return Patient.builder()
                .email(createPatientRequest.email())
                .fullName(createPatientRequest.fullName())
                .password(createPatientRequest.password())
                .phone(createPatientRequest.phone()).build();
    }

}
