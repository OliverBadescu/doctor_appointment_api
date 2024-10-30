package mycode.doctor_appointment_api.app.global_exceptions;


import mycode.doctor_appointment_api.app.appointments.exceptions.AppointmentAlreadyExistsAtThisDateAndTime;
import mycode.doctor_appointment_api.app.appointments.exceptions.NoAppointmentFound;
import mycode.doctor_appointment_api.app.clinic.exceptions.ClinicAlreadyExists;
import mycode.doctor_appointment_api.app.clinic.exceptions.NoClinicFound;
import mycode.doctor_appointment_api.app.doctor.exceptions.DoctorAlreadyExists;
import mycode.doctor_appointment_api.app.doctor.exceptions.NoDoctorFound;
import mycode.doctor_appointment_api.app.patient.exceptions.NoPatientFound;
import mycode.doctor_appointment_api.app.patient.exceptions.PatientAlreadyExists;
import mycode.doctor_appointment_api.app.working_hours.exceptions.NoWorkingHoursFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({NoAppointmentFound.class})
    public ResponseEntity<Object> handleAppointmentNotFoundException(NoAppointmentFound exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }

    @ExceptionHandler({AppointmentAlreadyExistsAtThisDateAndTime.class})
    public ResponseEntity<Object> handleAppointmentAlreadyExistsException(AppointmentAlreadyExistsAtThisDateAndTime exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }

    @ExceptionHandler({NoDoctorFound.class})
    public ResponseEntity<Object> handleDoctorNotFoundException(NoDoctorFound exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }

    @ExceptionHandler({DoctorAlreadyExists.class})
    public ResponseEntity<Object> handleDoctorAlreadyException(DoctorAlreadyExists exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }

    @ExceptionHandler({ClinicAlreadyExists.class})
    public ResponseEntity<Object> handleClinicAlreadyExistsException(ClinicAlreadyExists exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }

    @ExceptionHandler({NoClinicFound.class})
    public ResponseEntity<Object> handleNoClinicFoundException(NoClinicFound exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler({NoPatientFound.class})
    public ResponseEntity<Object> handleNoPatientFoundException(NoPatientFound exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler({PatientAlreadyExists.class})
    public ResponseEntity<Object> handlePatientAlreadyExistsException(PatientAlreadyExists exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }

    @ExceptionHandler({NoWorkingHoursFound.class})
    public ResponseEntity<Object> handleNoWorkingHoursFoundException(NoWorkingHoursFound exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }


}
