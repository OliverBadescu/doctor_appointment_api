package mycode.doctor_appointment_api.app.doctor.service;

import mycode.doctor_appointment_api.app.doctor.dto.CreateDoctorRequest;
import mycode.doctor_appointment_api.app.doctor.dto.DoctorResponse;
import mycode.doctor_appointment_api.app.doctor.dto.UpdateDoctorRequest;

public interface DoctorCommandService {

    DoctorResponse addDoctor(CreateDoctorRequest createDoctorRequest);

    DoctorResponse updateDoctor(UpdateDoctorRequest updateDoctorRequest, int id);

    DoctorResponse deleteDoctor(int id);

}
