package mycode.doctor_appointment_api.app.doctor.service;

import mycode.doctor_appointment_api.app.appointments.dtos.DoctorAppointmentList;
import mycode.doctor_appointment_api.app.doctor.dtos.AvailableDoctorTimes;
import mycode.doctor_appointment_api.app.doctor.dtos.AvailableDoctorTimesDays;
import mycode.doctor_appointment_api.app.doctor.dtos.DoctorResponse;
import mycode.doctor_appointment_api.app.doctor.dtos.DoctorResponseList;
import mycode.doctor_appointment_api.app.doctor.model.Doctor;

import java.time.LocalDate;

public interface DoctorQueryService {

    Doctor findByEmail(String email);

    DoctorResponse getDoctorById(int id);

    AvailableDoctorTimes getDoctorAvailableTime(int id, LocalDate date);

    AvailableDoctorTimesDays getDoctorAvailableTimeDifferentDays(int id, LocalDate start, LocalDate end);

    DoctorResponseList getAllDoctors();

    int totalDoctors();

    DoctorAppointmentList getAllDoctorAppointments(int id);

}
