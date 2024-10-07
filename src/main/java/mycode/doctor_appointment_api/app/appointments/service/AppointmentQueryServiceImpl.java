package mycode.doctor_appointment_api.app.appointments.service;

import io.swagger.v3.oas.annotations.servers.Server;
import lombok.AllArgsConstructor;
import lombok.Builder;
import mycode.doctor_appointment_api.app.appointments.dtos.AppointmentResponse;
import mycode.doctor_appointment_api.app.appointments.exceptions.NoAppointmentFound;
import mycode.doctor_appointment_api.app.appointments.mapper.AppointmentMapper;
import mycode.doctor_appointment_api.app.appointments.model.Appointment;
import mycode.doctor_appointment_api.app.appointments.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AppointmentQueryServiceImpl implements AppointmentQueryService{

    private AppointmentRepository appointmentRepository;

    @Override
    public AppointmentResponse getAppointment(int id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new NoAppointmentFound("No appointment with this id found"));

        return AppointmentMapper.appointmentToResponseDto(appointment);

    }
}
