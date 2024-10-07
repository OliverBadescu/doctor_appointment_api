package mycode.doctor_appointment_api.app.clinic.mapper;

import mycode.doctor_appointment_api.app.clinic.dtos.ClinicResponse;
import mycode.doctor_appointment_api.app.clinic.model.Clinic;

public class ClinicMapper {

    public static ClinicResponse clinicToResponseDto(Clinic clinic){
        return new ClinicResponse(
                clinic.getId(),
                clinic.getName(),
                clinic.getAddress()
        );
    }

}
