package mycode.doctor_appointment_api.app.appointments.service;

import io.swagger.v3.oas.annotations.servers.Server;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
import mycode.doctor_appointment_api.app.patient.exceptions.NoPatientFound;
import mycode.doctor_appointment_api.app.patient.model.Patient;
import mycode.doctor_appointment_api.app.patient.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AppointmentQueryServiceImpl implements AppointmentQueryService{

    private final PatientRepository patientRepository;
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
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new NoPatientFound("No patient with this id found"));

        Optional<List<Appointment>> appointments = appointmentRepository.getAllByPatientId(id);

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
}
