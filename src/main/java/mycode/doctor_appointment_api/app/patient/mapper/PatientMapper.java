package mycode.doctor_appointment_api.app.patient.mapper;

import mycode.doctor_appointment_api.app.patient.dtos.PatientResponse;
import mycode.doctor_appointment_api.app.patient.model.Patient;

public class PatientMapper {

    public static PatientResponse patientToResponseDto(Patient patient){
        return new PatientResponse(
                patient.getId(),
                patient.getFullName(),
                patient.getEmail(),
                patient.getPassword(),
                patient.getPhone());
    }


}
