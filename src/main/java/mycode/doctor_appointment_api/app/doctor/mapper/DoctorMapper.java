package mycode.doctor_appointment_api.app.doctor.mapper;

import mycode.doctor_appointment_api.app.clinic.mapper.ClinicMapper;
import mycode.doctor_appointment_api.app.doctor.dto.DoctorResponse;
import mycode.doctor_appointment_api.app.doctor.model.Doctor;

public class DoctorMapper {

    public static DoctorResponse doctorToResponseDto(Doctor doctor) {
        return new DoctorResponse(doctor.getId(),
                doctor.getFullName(),
                doctor.getPassword(),
                doctor.getEmail(),
                doctor.getSpecialization(),
                doctor.getPhone(),
                ClinicMapper.clinicToResponseDto(doctor.getClinic()),
                doctor.getUserRole());
    }


}
