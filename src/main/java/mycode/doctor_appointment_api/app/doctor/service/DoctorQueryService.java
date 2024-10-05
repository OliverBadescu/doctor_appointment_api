package mycode.doctor_appointment_api.app.doctor.service;

import mycode.doctor_appointment_api.app.doctor.dtos.DoctorResponse;

public interface DoctorQueryService {

    DoctorResponse getDoctorById(int id);

}
