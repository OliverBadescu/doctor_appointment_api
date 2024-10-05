package mycode.doctor_appointment_api.app.doctor.mapper;

import mycode.doctor_appointment_api.app.doctor.dtos.DoctorResponse;
import mycode.doctor_appointment_api.app.doctor.model.Doctor;
import mycode.doctor_appointment_api.app.patient.model.Patient;

import javax.print.Doc;

public class DoctorMapper {

    public static DoctorResponse doctorToResponseDto(Doctor doctor){
        return new DoctorResponse(doctor.getId(),
                doctor.getFullName(),
                doctor.getPassword(),
                doctor.getEmail(),
                doctor.getEmail(),
                doctor.getPhone(),
                doctor.getClinic());
    }


}
