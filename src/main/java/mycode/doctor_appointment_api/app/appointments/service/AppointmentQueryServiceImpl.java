package mycode.doctor_appointment_api.app.appointments.service;

import lombok.AllArgsConstructor;
import mycode.doctor_appointment_api.app.appointments.dtos.AppointmentResponse;
import mycode.doctor_appointment_api.app.appointments.dtos.DoctorAppointmentList;
import mycode.doctor_appointment_api.app.appointments.dtos.PatientAppointmentList;
import mycode.doctor_appointment_api.app.appointments.exceptions.NoAppointmentFound;
import mycode.doctor_appointment_api.app.appointments.mapper.AppointmentMapper;
import mycode.doctor_appointment_api.app.appointments.model.Appointment;
import mycode.doctor_appointment_api.app.appointments.repository.AppointmentRepository;
import mycode.doctor_appointment_api.app.doctor.exceptions.NoDoctorFound;
import mycode.doctor_appointment_api.app.doctor.model.Doctor;
import mycode.doctor_appointment_api.app.doctor.repository.DoctorRepository;
import mycode.doctor_appointment_api.app.users.exceptions.NoUserFound;
import mycode.doctor_appointment_api.app.users.model.User;
import mycode.doctor_appointment_api.app.users.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



/**
 * Implementation of {@link AppointmentQueryService}.
 * <p>
 * Provides methods to retrieve appointment data for patients, doctors, and overall statistics.
 */
@AllArgsConstructor
@Service
public class AppointmentQueryServiceImpl implements AppointmentQueryService {

    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private AppointmentRepository appointmentRepository;


    @Override
    public AppointmentResponse getAppointment(int id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new NoAppointmentFound("No appointment with this id found"));

        return AppointmentMapper.appointmentToResponseDto(appointment);

    }

    @Override
    public PatientAppointmentList getAllPatientAppointments(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoUserFound("No user with this id found"));

        Optional<List<Appointment>> appointments = appointmentRepository.getAllByUserId(id);

        List<AppointmentResponse> appointmentResponses = new ArrayList<>();

        appointments.get().forEach(appointment -> {
            appointmentResponses.add(AppointmentMapper.appointmentToResponseDto(appointment));
        });


        return new PatientAppointmentList(appointmentResponses);
    }

    @Override
    public DoctorAppointmentList getAllDoctorAppointments(int id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new NoDoctorFound("No doctor with this id found"));


        Optional<List<Appointment>> appointments = appointmentRepository.getAllByDoctorId(id);

        List<AppointmentResponse> appointmentResponses = new ArrayList<>();

        appointments.get().forEach(appointment -> {
            appointmentResponses.add(AppointmentMapper.appointmentToResponseDto(appointment));
        });


        return new DoctorAppointmentList(appointmentResponses);
    }

    @Override
    public int totalAppointments(){
        return appointmentRepository.findAll().size();
    }
}
