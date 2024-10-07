package mycode.doctor_appointment_api.app.clinic.service;

import mycode.doctor_appointment_api.app.clinic.dtos.ClinicResponse;

public interface ClinicQueryService {

    ClinicResponse getClinicById(int id);

}
