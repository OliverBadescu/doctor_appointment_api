package mycode.doctor_appointment_api.app.appointments.service;


import lombok.AllArgsConstructor;
import mycode.doctor_appointment_api.app.appointments.dtos.AppointmentResponse;
import mycode.doctor_appointment_api.app.appointments.dtos.CreateAppointmentRequest;
import mycode.doctor_appointment_api.app.appointments.dtos.StatusUpdateRequest;
import mycode.doctor_appointment_api.app.appointments.dtos.UpdateAppointmentRequest;
import mycode.doctor_appointment_api.app.appointments.enums.AppointmentStatus;
import mycode.doctor_appointment_api.app.appointments.exceptions.AppointmentAlreadyExistsAtThisDateAndTime;
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


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 *Implementation of {@link AppointmentCommandService}.
 * <p>
 * Interacts with repositories for users, doctors, and appointments.
 */

@AllArgsConstructor
@Service
public class AppointmentCommandServiceImpl implements AppointmentCommandService {

    private AppointmentRepository appointmentRepository;
    private UserRepository userRepository;
    private DoctorRepository doctorRepository;


    @Override
    public AppointmentResponse addAppointment(CreateAppointmentRequest createAppointmentRequest) {

        User user = userRepository.findById(createAppointmentRequest.patientId())
                .orElseThrow(() -> new NoUserFound("No user with this id found"));


        Doctor doctor = doctorRepository.findByFullName(createAppointmentRequest.doctorName())
                .orElseThrow(() -> new NoDoctorFound("No doctor with this name found"));


        LocalDateTime start = createAppointmentRequest.start();
        LocalDateTime end = createAppointmentRequest.end();


        Optional<List<Appointment>> appointmentsOpt = appointmentRepository.getAllByDoctorId(doctor.getId());


        if (appointmentsOpt.isPresent()) {
            boolean hasAppointment = appointmentsOpt.get().stream().anyMatch(existingAppointment -> {
                LocalDateTime existingStart = existingAppointment.getStart();
                LocalDateTime existingEnd = existingAppointment.getEnd();

                boolean sameDate = existingStart.toLocalDate().equals(start.toLocalDate());
                boolean overlaps = start.isBefore(existingEnd) && end.isAfter(existingStart);
                return sameDate && overlaps;
            });

            if (hasAppointment) {
                throw new AppointmentAlreadyExistsAtThisDateAndTime(
                        "Doctor already has an appointment at this time."
                );
            }
        }

        Appointment appointment = Appointment.builder()
                .start(start)
                .end(end)
                .user(user)
                .reason(createAppointmentRequest.reason())
                .doctor(doctor)
                .status(AppointmentStatus.UPCOMING)
                .build();

        appointmentRepository.saveAndFlush(appointment);

        return AppointmentMapper.appointmentToResponseDto(appointment);
    }


    @Override
    public AppointmentResponse updateAppointment(UpdateAppointmentRequest updateAppointmentRequest, int id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new NoAppointmentFound("No appointment with this id found"));

        appointment.setStart(updateAppointmentRequest.start());
        appointment.setEnd(updateAppointmentRequest.end());

        appointmentRepository.save(appointment);

        return AppointmentMapper.appointmentToResponseDto(appointment);
    }

    @Override
    public AppointmentResponse deleteAppointment(int id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new NoAppointmentFound("No appointment with this id found"));

        AppointmentResponse appointmentResponse = AppointmentMapper.appointmentToResponseDto(appointment);

        appointmentRepository.delete(appointment);

        return appointmentResponse;
    }


    @Override
    public AppointmentResponse deletePatientAppointment(int userId, int appointmentId) {

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new NoAppointmentFound("No appointment with this id found"));


        if (appointment.getUser().getId() == userId) {
            AppointmentResponse appointmentResponse = AppointmentMapper.appointmentToResponseDto(appointment);

            appointmentRepository.delete(appointment);

            return appointmentResponse;

        } else {
            throw new NoAppointmentFound("This patient has no appointment with this id");
        }
    }
    
    @Override
    public AppointmentResponse updateStatus(StatusUpdateRequest status, int appointmentId){

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new NoAppointmentFound("No appointment with this id found"));

        String statusReq = status.status();
        String cleanStatus = statusReq.replace("\"", "").trim();

        System.out.println("Updating appointment ID: " + appointmentId + " with cleaned status: " + cleanStatus);

        if (cleanStatus.equalsIgnoreCase("COMPLETED")) {
            appointment.setStatus(AppointmentStatus.COMPLETED);
        } else if (cleanStatus.equalsIgnoreCase("CANCELLED")) {
            appointment.setStatus(AppointmentStatus.CANCELLED);
        } else if (cleanStatus.equalsIgnoreCase("UPCOMING")) {
            appointment.setStatus(AppointmentStatus.UPCOMING);
        } else {
            System.out.println("Warning: Unrecognized status '" + cleanStatus + "' - no update performed");
        }

        Appointment savedAppointment = appointmentRepository.save(appointment);

        System.out.println("Updated appointment status: " + savedAppointment.getStatus());

        return AppointmentMapper.appointmentToResponseDto(savedAppointment);

    }
}
