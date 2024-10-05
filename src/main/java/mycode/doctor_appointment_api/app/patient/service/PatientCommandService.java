package mycode.doctor_appointment_api.app.patient.service;

import mycode.doctor_appointment_api.app.patient.dtos.CreatePatientRequest;
import mycode.doctor_appointment_api.app.patient.dtos.PatientResponse;
import mycode.doctor_appointment_api.app.patient.dtos.UpdatePatientRequest;

public interface PatientCommandService {

    PatientResponse addPatient(CreatePatientRequest createPatientRequest);

    PatientResponse updatePatient(UpdatePatientRequest updatePatientRequest, int id);

    PatientResponse deletePatient(int id);

}
