package mycode.doctor_appointment_api.app.appointments.mapper;

import mycode.doctor_appointment_api.app.appointments.dtos.AppointmentResponse;
import mycode.doctor_appointment_api.app.appointments.model.Appointment;
import mycode.doctor_appointment_api.app.doctor.mapper.DoctorMapper;
import mycode.doctor_appointment_api.app.patient.mapper.PatientMapper;

public class AppointmentMapper {

    public static AppointmentResponse appointmentToResponseDto(Appointment appointment){
        return new AppointmentResponse(
                appointment.getId(),
                appointment.getStart(),
                appointment.getEnd(),
                DoctorMapper.doctorToResponseDto(appointment.getDoctor()),
                PatientMapper.patientToResponseDto(appointment.getPatient())
        );
    }

}