package mycode.doctor_appointment_api.app.clinic.service;

import mycode.doctor_appointment_api.app.clinic.dto.ClinicResponse;
import mycode.doctor_appointment_api.app.clinic.dto.ClinicResponseList;

public interface ClinicQueryService {

    ClinicResponse getClinicById(int id);

    ClinicResponseList getAllClinics();

    int getTotalClinics();

}