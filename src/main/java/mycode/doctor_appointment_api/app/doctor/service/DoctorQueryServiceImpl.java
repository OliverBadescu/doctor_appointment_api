package mycode.doctor_appointment_api.app.doctor.service;

import lombok.AllArgsConstructor;
import mycode.doctor_appointment_api.app.appointments.exceptions.NoAppointmentFound;
import mycode.doctor_appointment_api.app.appointments.model.Appointment;
import mycode.doctor_appointment_api.app.appointments.repository.AppointmentRepository;
import mycode.doctor_appointment_api.app.doctor.dtos.*;
import mycode.doctor_appointment_api.app.doctor.exceptions.NoDoctorFound;
import mycode.doctor_appointment_api.app.doctor.mapper.DoctorMapper;
import mycode.doctor_appointment_api.app.doctor.model.Doctor;
import mycode.doctor_appointment_api.app.doctor.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class DoctorQueryServiceImpl implements DoctorQueryService {


    private DoctorRepository doctorRepository;
    private AppointmentRepository appointmentRepository;

    @Override
    public DoctorResponse getDoctorById(int id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new NoDoctorFound("No doctor with this id found"));


        return DoctorMapper.doctorToResponseDto(doctor);
    }

    @Override
    public AvailableDoctorTimes getDoctorAvailableTime(int id, LocalDate date) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new NoDoctorFound("No doctor with this id found"));

        List<Appointment> appointments = appointmentRepository.findByDoctorIdAndDate(id, date)
                .orElse(Collections.emptyList());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        List<String> bookedTimes = new ArrayList<>();

        for (Appointment appointment : appointments) {
            String start = appointment.getStart().format(formatter);
            String end = appointment.getEnd().format(formatter);
            bookedTimes.add(start + " - " + end);
        }

        return new AvailableDoctorTimes(id, bookedTimes);
    }


    @Override
    public AvailableDoctorTimesDays getDoctorAvailableTimeDifferentDays(int id, LocalDate start, LocalDate end) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new NoDoctorFound("No doctor with this id found"));

        List<AvailableTimesAndDates> result = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        String workStart = "09:00";
        String workEnd = "17:00";


        LocalDate currentDate = start;
        while (!currentDate.isAfter(end)) {
            List<Appointment> appointments = appointmentRepository.findByDoctorIdAndDate(id, currentDate)
                    .orElse(new ArrayList<>());

            List<String> times = new ArrayList<>();
            String lastEnd = workStart;

            if (appointments.isEmpty()) {

                times.add(workStart + " - " + workEnd);
            } else {

                for (Appointment appointment : appointments) {
                    String startAppointment = appointment.getStart().format(formatter);
                    String endAppointment = appointment.getEnd().format(formatter);

                    if (!lastEnd.equals(startAppointment)) {
                        times.add(lastEnd + " - " + startAppointment);
                    }
                    lastEnd = endAppointment;
                }


                if (!lastEnd.equals(workEnd)) {
                    times.add(lastEnd + " - " + workEnd);
                }
            }

            result.add(new AvailableTimesAndDates(currentDate, times));
            currentDate = currentDate.plusDays(1);
        }

        return new AvailableDoctorTimesDays(id, result);
    }

    @Override
    public DoctorResponseList getAllDoctors() {
        List<Doctor> doctors = doctorRepository.findAll();

        if(doctors.isEmpty()){
            throw new NoDoctorFound("No doctors found");
        }

        List<DoctorResponse> list = new ArrayList<>();

        doctors.forEach(doctor -> {
            list.add(DoctorMapper.doctorToResponseDto(doctor));
        });

        return new DoctorResponseList(list);
    }


}
