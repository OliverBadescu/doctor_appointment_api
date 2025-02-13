package mycode.doctor_appointment_api.app.patient.service;

import mycode.doctor_appointment_api.app.patient.dtos.PatientResponse;
import mycode.doctor_appointment_api.app.patient.dtos.PatientResponseList;

public interface PatientQueryService {

    PatientResponse findPatientById(int id);

    PatientResponseList getAllPatients();
}
