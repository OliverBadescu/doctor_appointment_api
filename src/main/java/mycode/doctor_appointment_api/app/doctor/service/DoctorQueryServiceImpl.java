package mycode.doctor_appointment_api.app.doctor.service;

import lombok.AllArgsConstructor;
import mycode.doctor_appointment_api.app.appointments.exceptions.NoAppointmentFound;
import mycode.doctor_appointment_api.app.appointments.model.Appointment;
import mycode.doctor_appointment_api.app.appointments.repository.AppointmentRepository;
import mycode.doctor_appointment_api.app.doctor.dtos.AvailableDoctorTimes;
import mycode.doctor_appointment_api.app.doctor.dtos.AvailableTimeSlot;
import mycode.doctor_appointment_api.app.doctor.dtos.DoctorResponse;
import mycode.doctor_appointment_api.app.doctor.exceptions.NoDoctorFound;
import mycode.doctor_appointment_api.app.doctor.mapper.DoctorMapper;
import mycode.doctor_appointment_api.app.doctor.model.Doctor;
import mycode.doctor_appointment_api.app.doctor.repository.DoctorRepository;
import mycode.doctor_appointment_api.app.working_hours.exceptions.NoWorkingHoursFound;
import mycode.doctor_appointment_api.app.working_hours.model.WorkingHours;
import mycode.doctor_appointment_api.app.working_hours.repository.WorkingHoursRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class DoctorQueryServiceImpl implements DoctorQueryService{

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

        List<WorkingHours> workingHours = workingHoursRepository.findByDoctorId(id)
                .orElseThrow(() -> new NoWorkingHoursFound("No working hours found for this doctor"));

        List<Appointment> appointments = appointmentRepository.findByDoctorIdAndStart(id, date.atStartOfDay())
                .orElseThrow(() -> new NoAppointmentFound("No appointments found"));

        List<AvailableTimeSlot> availableSlots = new ArrayList<>();

        for (WorkingHours hours : workingHours) {
            LocalDateTime startOfDay = date.atTime(hours.getStartTime());
            LocalDateTime endOfDay = date.atTime(hours.getEndTime());

            LocalDateTime current = startOfDay;
            while (current.isBefore(endOfDay)) {
                LocalDateTime nextSlot = current.plusMinutes(30);
                if (nextSlot.isAfter(endOfDay)) {
                    break;
                }

                LocalDateTime currentForCheck = current;
                boolean isAvailable = appointments.stream().noneMatch(appointment ->
                        (appointment.getStart().isBefore(nextSlot) && appointment.getEnd().isAfter(currentForCheck)));

                if (isAvailable) {
                    availableSlots.add(new AvailableTimeSlot(current, nextSlot));
                }

                current = nextSlot;
            }
        }


        return new AvailableDoctorTimes(id, availableSlots);
    }


}
