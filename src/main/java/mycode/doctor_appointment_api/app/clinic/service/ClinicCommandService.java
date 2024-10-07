package mycode.doctor_appointment_api.app.clinic.service;

import mycode.doctor_appointment_api.app.clinic.dtos.ClinicResponse;
import mycode.doctor_appointment_api.app.clinic.dtos.CreateClinicRequest;
import mycode.doctor_appointment_api.app.clinic.dtos.UpdateClinicRequest;

public interface ClinicCommandService {

    ClinicResponse addClinic(CreateClinicRequest createClinicRequest);

    ClinicResponse updateClinic(int id, UpdateClinicRequest updateClinicRequest);

    ClinicResponse deleteClinic(int id);
}
