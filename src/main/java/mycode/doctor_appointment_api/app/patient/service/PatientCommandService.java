package mycode.doctor_appointment_api.app.patient.service;

import mycode.doctor_appointment_api.app.patient.dtos.CreatePatientRequest;
import mycode.doctor_appointment_api.app.patient.dtos.PatientResponse;

public interface PatientCommandService {

    PatientResponse addPatient(CreatePatientRequest createPatientRequest);

}
