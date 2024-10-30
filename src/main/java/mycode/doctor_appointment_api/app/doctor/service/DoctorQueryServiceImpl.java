package mycode.doctor_appointment_api.app.doctor.service;

import lombok.AllArgsConstructor;
import mycode.doctor_appointment_api.app.appointments.exceptions.NoAppointmentFound;
import mycode.doctor_appointment_api.app.appointments.model.Appointment;
import mycode.doctor_appointment_api.app.appointments.repository.AppointmentRepository;
import mycode.doctor_appointment_api.app.doctor.dtos.AvailableDoctorTimes;
import mycode.doctor_appointment_api.app.doctor.dtos.DoctorResponse;
import mycode.doctor_appointment_api.app.doctor.exceptions.NoDoctorFound;
import mycode.doctor_appointment_api.app.doctor.mapper.DoctorMapper;
import mycode.doctor_appointment_api.app.doctor.model.Doctor;
import mycode.doctor_appointment_api.app.doctor.repository.DoctorRepository;
import mycode.doctor_appointment_api.app.working_hours.repository.WorkingHoursRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class DoctorQueryServiceImpl implements DoctorQueryService {

    private DoctorRepository doctorRepository;
    private AppointmentRepository appointmentRepository;
    private WorkingHoursRepository workingHoursRepository;

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
                .orElseThrow(() -> new NoAppointmentFound("No appointments found"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        List<String> times = new ArrayList<>();

        String workStart = "09:00";
        String workEnd = "17:00";

        if (appointments.isEmpty()) {
            times.add(workStart + " - " + workEnd);
            return new AvailableDoctorTimes(id, times);
        }

        String lastEnd = workStart;
        for (Appointment appointment : appointments) {
            String start = appointment.getStart().format(formatter);
            String end = appointment.getEnd().format(formatter);

            if (!lastEnd.equals(start)) {
                times.add(lastEnd + " - " + start);
            }
            lastEnd = end;
        }

        if (!lastEnd.equals(workEnd)) {
            times.add(lastEnd + " - " + workEnd);
        }

        return new AvailableDoctorTimes(id, times);
    }


}
