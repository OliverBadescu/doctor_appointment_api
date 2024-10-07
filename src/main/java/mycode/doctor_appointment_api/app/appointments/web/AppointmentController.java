package mycode.doctor_appointment_api.app.appointments.web;

import lombok.AllArgsConstructor;
import mycode.doctor_appointment_api.app.appointments.dtos.AppointmentResponse;
import mycode.doctor_appointment_api.app.appointments.dtos.CreateAppointmentRequest;
import mycode.doctor_appointment_api.app.appointments.dtos.UpdateAppointmentRequest;
import mycode.doctor_appointment_api.app.appointments.service.AppointmentCommandService;
import mycode.doctor_appointment_api.app.appointments.service.AppointmentQueryService;
import org.hibernate.dialect.unique.CreateTableUniqueDelegate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/appointment")
public class AppointmentController {

    private AppointmentCommandService appointmentCommandService;
    private AppointmentQueryService appointmentQueryService;

    @PostMapping
    ResponseEntity<AppointmentResponse> addAppointment(@RequestBody CreateAppointmentRequest createAppointmentRequest){
        return null;
    }

    @GetMapping(path = "/{appointmentId}")
    ResponseEntity<AppointmentResponse> getAppointment(@PathVariable int appointmentId){
        return new ResponseEntity<>(appointmentQueryService.getAppointment(appointmentId), HttpStatus.ACCEPTED);
    }

    @PutMapping(path = "/{appointmentId}")
    ResponseEntity<AppointmentResponse> updateAppointment(@PathVariable int appointmentId, UpdateAppointmentRequest updateAppointmentRequest){
        return new ResponseEntity<>(appointmentCommandService.updateAppointment(updateAppointmentRequest, appointmentId), HttpStatus.ACCEPTED);
    }

    @DeleteMapping(path = "/{appointmentId}")
    ResponseEntity<AppointmentResponse> deleteAppointment(@PathVariable int appointmentId){
        return new ResponseEntity<>(appointmentCommandService.deleteAppointment(appointmentId), HttpStatus.ACCEPTED);
    }
}
