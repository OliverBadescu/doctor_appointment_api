package mycode.doctor_appointment_api.app.appointments.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mycode.doctor_appointment_api.app.appointments.dtos.*;
import mycode.doctor_appointment_api.app.appointments.service.AppointmentCommandService;
import mycode.doctor_appointment_api.app.appointments.service.AppointmentQueryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@CrossOrigin
@Slf4j
@RequestMapping("/appointment")
public class AppointmentController {

    private AppointmentCommandService appointmentCommandService;
    private AppointmentQueryService appointmentQueryService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @PostMapping("/addAppointment")
    ResponseEntity<AppointmentResponse> addAppointment(@RequestBody CreateAppointmentRequest createAppointmentRequest) {
        return new ResponseEntity<>(appointmentCommandService.addAppointment(createAppointmentRequest), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @GetMapping(path = "/{appointmentId}")
    ResponseEntity<AppointmentResponse> getAppointment(@PathVariable int appointmentId) {
        return new ResponseEntity<>(appointmentQueryService.getAppointment(appointmentId), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @PutMapping(path = "/{appointmentId}")
    ResponseEntity<AppointmentResponse> updateAppointment(@PathVariable int appointmentId, UpdateAppointmentRequest updateAppointmentRequest) {
        return new ResponseEntity<>(appointmentCommandService.updateAppointment(updateAppointmentRequest, appointmentId), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @DeleteMapping(path = "/{appointmentId}")
    ResponseEntity<AppointmentResponse> deleteAppointment(@PathVariable int appointmentId) {
        return new ResponseEntity<>(appointmentCommandService.deleteAppointment(appointmentId), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @GetMapping(path = "/patient/{patientId}")
    ResponseEntity<PatientAppointmentList> getPatientAppointments(@PathVariable int patientId) {

        return new ResponseEntity<>(appointmentQueryService.getAllPatientAppointments(patientId), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @GetMapping(path = "/doctor/{doctorId}")
    ResponseEntity<DoctorAppointmentList> getDoctorAppointments(@PathVariable int doctorId) {
        return new ResponseEntity<>(appointmentQueryService.getAllDoctorAppointments(doctorId), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(path = "/patient/{patientId}/{appointmentId}")
    ResponseEntity<AppointmentResponse> deletePatientAppointment(@PathVariable int patientId, @PathVariable int appointmentId) {

        return new ResponseEntity<>(appointmentCommandService.deletePatientAppointment(patientId, appointmentId), HttpStatus.ACCEPTED);

    }
}
