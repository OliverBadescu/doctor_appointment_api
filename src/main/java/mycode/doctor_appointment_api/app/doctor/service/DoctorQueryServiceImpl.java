package mycode.doctor_appointment_api.app.doctor.service;

import lombok.AllArgsConstructor;
import mycode.doctor_appointment_api.app.appointments.dto.AppointmentResponse;
import mycode.doctor_appointment_api.app.appointments.dto.DoctorAppointmentList;
import mycode.doctor_appointment_api.app.appointments.exceptions.NoAppointmentFound;
import mycode.doctor_appointment_api.app.appointments.mapper.AppointmentMapper;
import mycode.doctor_appointment_api.app.appointments.model.Appointment;
import mycode.doctor_appointment_api.app.appointments.repository.AppointmentRepository;
import mycode.doctor_appointment_api.app.doctor.dto.*;
import mycode.doctor_appointment_api.app.doctor.exceptions.NoDoctorFound;
import mycode.doctor_appointment_api.app.doctor.mapper.DoctorMapper;
import mycode.doctor_appointment_api.app.doctor.model.Doctor;
import mycode.doctor_appointment_api.app.doctor.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

        LocalDateTime workStart = date.atTime(9, 0);
        LocalDateTime workEnd = date.atTime(17, 0);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        List<String> availableSlots = new ArrayList<>();

        for (LocalDateTime slotStart = workStart; !slotStart.plusMinutes(30).isAfter(workEnd); slotStart = slotStart.plusMinutes(30)) {
            LocalDateTime slotEnd = slotStart.plusMinutes(30);
            boolean isAvailable = true;


            for (Appointment appointment : appointments) {
                LocalDateTime appointmentStart = appointment.getStart();
                LocalDateTime appointmentEnd = appointment.getEnd();
                if (slotStart.isBefore(appointmentEnd) && slotEnd.isAfter(appointmentStart)) {
                    isAvailable = false;
                    break;
                }
            }

            if (isAvailable) {
                availableSlots.add(slotStart.toLocalTime().format(formatter) + " - " + slotEnd.toLocalTime().format(formatter));
            }
        }

        return new AvailableDoctorTimes(id, availableSlots);
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

    @Override
    public int totalDoctors(){
        return doctorRepository.findAll().size();
    }

    @Override
    public Doctor findByEmail(String email){
        return doctorRepository.findByEmail(email).orElseThrow(() -> new NoDoctorFound("No doctor with this email found"));
    }

    @Override
    public DoctorAppointmentList getAllDoctorAppointments(int id){
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new NoDoctorFound("No doctor with this id found"));

        Optional<List<Appointment>> list = appointmentRepository.getAllByDoctorId(id);

        if(list.isPresent()){
            List<AppointmentResponse> res = new ArrayList<>();

            list.get().forEach(appointment -> {
                res.add(AppointmentMapper.appointmentToResponseDto(appointment));
            });

            return new DoctorAppointmentList(res);
        }else{
            throw new NoAppointmentFound("No appointments found");
        }
    }

}
