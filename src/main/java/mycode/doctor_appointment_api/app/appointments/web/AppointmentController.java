package mycode.doctor_appointment_api.app.appointments.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mycode.doctor_appointment_api.app.appointments.dtos.*;
import mycode.doctor_appointment_api.app.appointments.service.AppointmentCommandService;
import mycode.doctor_appointment_api.app.appointments.service.AppointmentQueryService;
import mycode.doctor_appointment_api.app.system.response.ApiResponse;
import mycode.doctor_appointment_api.app.system.response.PagedResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;



@RestController
@AllArgsConstructor
@CrossOrigin
@Slf4j
@RequestMapping("/api/v1/appointment")
public class AppointmentController {

    private AppointmentCommandService appointmentCommandService;
    private AppointmentQueryService appointmentQueryService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @PostMapping("/addAppointment")
    ResponseEntity<AppointmentResponse> addAppointment(@RequestBody CreateAppointmentRequest createAppointmentRequest) {
        return new ResponseEntity<>(appointmentCommandService.addAppointment(createAppointmentRequest), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @GetMapping(path = "/getAppointment/{appointmentId}")
    ResponseEntity<AppointmentResponse> getAppointment(@PathVariable int appointmentId) {
        return new ResponseEntity<>(appointmentQueryService.getAppointment(appointmentId), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @PutMapping(path = "/updateAppointment/{appointmentId}")
    ResponseEntity<AppointmentResponse> updateAppointment(@PathVariable int appointmentId, UpdateAppointmentRequest updateAppointmentRequest) {
        return new ResponseEntity<>(appointmentCommandService.updateAppointment(updateAppointmentRequest, appointmentId), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @DeleteMapping(path = "/deleteAppointment/{appointmentId}")
    ResponseEntity<AppointmentResponse> deleteAppointment(@PathVariable int appointmentId) {
        return new ResponseEntity<>(appointmentCommandService.deleteAppointment(appointmentId), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT') or hasRole('ROLE_DOCTOR')")
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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/getTotalAppointments")
    ResponseEntity<Integer> getTotalAppointments(){
        return new ResponseEntity<>(appointmentQueryService.totalAppointments(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_DOCTOR')")
    @PutMapping("/updateStatus/{appointmentId}")
    ResponseEntity<AppointmentResponse> updateAppointmentStatus(@PathVariable int appointmentId, @RequestBody StatusUpdateRequest status){
        return new ResponseEntity<>(appointmentCommandService.updateStatus(status, appointmentId), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/appointments")
    public ResponseEntity<ApiResponse<PagedResponse<AppointmentResponse>>> getAppointmentsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                Sort.by(sortBy).descending() : 
                Sort.by(sortBy).ascending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<AppointmentResponse> appointmentPage = appointmentQueryService.getAllAppointmentsPaginated(pageable);
        
        return ResponseEntity.ok(ApiResponse.success(
                PagedResponse.of(appointmentPage), 
                "Appointments retrieved successfully"
        ));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT') or hasRole('ROLE_DOCTOR')")
    @GetMapping("/patient/{patientId}/appointments")
    public ResponseEntity<ApiResponse<PagedResponse<AppointmentResponse>>> getPatientAppointmentsPaginated(
            @PathVariable int patientId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "start") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                Sort.by(sortBy).descending() : 
                Sort.by(sortBy).ascending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<AppointmentResponse> appointmentPage = appointmentQueryService.getPatientAppointmentsPaginated(patientId, pageable);
        
        return ResponseEntity.ok(ApiResponse.success(
                PagedResponse.of(appointmentPage), 
                "Patient appointments retrieved successfully"
        ));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_DOCTOR')")
    @GetMapping("/doctor/{doctorId}/appointments")
    public ResponseEntity<ApiResponse<PagedResponse<AppointmentResponse>>> getDoctorAppointmentsPaginated(
            @PathVariable int doctorId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "start") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                Sort.by(sortBy).descending() : 
                Sort.by(sortBy).ascending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<AppointmentResponse> appointmentPage = appointmentQueryService.getDoctorAppointmentsPaginated(doctorId, pageable);
        
        return ResponseEntity.ok(ApiResponse.success(
                PagedResponse.of(appointmentPage),
                "Doctor appointments retrieved successfully"
        ));
    }

    @GetMapping("/confirm/{token}")
    public ResponseEntity<ApiResponse<AppointmentResponse>> confirmAppointment(@PathVariable String token) {
        AppointmentResponse response = appointmentCommandService.confirmAppointment(token);
        return ResponseEntity.ok(ApiResponse.success(response, "Appointment confirmed successfully"));
    }
}
