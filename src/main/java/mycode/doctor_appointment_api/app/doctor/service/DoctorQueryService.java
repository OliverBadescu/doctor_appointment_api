package mycode.doctor_appointment_api.app.doctor.service;

import mycode.doctor_appointment_api.app.doctor.dtos.AvailableDoctorTimes;
import mycode.doctor_appointment_api.app.doctor.dtos.DoctorResponse;

import java.time.LocalDate;

public interface DoctorQueryService {

    DoctorResponse getDoctorById(int id);

    AvailableDoctorTimes getDoctorAvailableTime(int id, LocalDate date);

}
