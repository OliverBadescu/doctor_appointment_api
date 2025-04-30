package mycode.doctor_appointment_api.app.doctor.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mycode.doctor_appointment_api.app.doctor.dtos.*;
import mycode.doctor_appointment_api.app.doctor.service.DoctorCommandService;
import mycode.doctor_appointment_api.app.doctor.service.DoctorQueryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@AllArgsConstructor
@CrossOrigin
@Slf4j
@RequestMapping("/doctor")
public class DoctorController {

    private DoctorQueryService doctorQueryService;
    private DoctorCommandService doctorCommandService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @GetMapping(path = "/{doctorId}")
    ResponseEntity<DoctorResponse> getDoctor(@PathVariable int doctorId) {
        return new ResponseEntity<>(doctorQueryService.getDoctorById(doctorId), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/addDoctor")
    ResponseEntity<DoctorResponse> addDoctor(@RequestBody CreateDoctorRequest createDoctorRequest) {
        return new ResponseEntity<>(doctorCommandService.addDoctor(createDoctorRequest), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(path = "/updateDoctor/{doctorId}")
    ResponseEntity<DoctorResponse> updateDoctor(@PathVariable int doctorId, @RequestBody UpdateDoctorRequest updateDoctorRequest) {
        return new ResponseEntity<>(doctorCommandService.updateDoctor(updateDoctorRequest, doctorId), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(path = "/deleteDoctor/{doctorId}")
    ResponseEntity<DoctorResponse> deleteDoctor(@PathVariable int doctorId) {
        return new ResponseEntity<>(doctorCommandService.deleteDoctor(doctorId), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @PostMapping(path = "/available/{doctorId}")
    ResponseEntity<AvailableDoctorTimes> getDoctorAvailability(@PathVariable int doctorId, @RequestBody DateRequest date) {
        LocalDate localDate = date.getDate();
        return new ResponseEntity<>(doctorQueryService.getDoctorAvailableTime(doctorId, localDate), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @GetMapping(path = "/available/days/{doctorId}")
    ResponseEntity<AvailableDoctorTimesDays> getDoctorAvailability(@PathVariable int doctorId, @RequestBody TwoDateRequest date){
        LocalDate start = date.start();
        LocalDate end = date.end();

        return new ResponseEntity<>(doctorQueryService.getDoctorAvailableTimeDifferentDays(doctorId, start,end), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @GetMapping("/getAllDoctors")
    ResponseEntity<DoctorResponseList> getAllDoctors(){
        return new ResponseEntity<>(doctorQueryService.getAllDoctors(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/getTotalDoctors")
    ResponseEntity<Integer> getTotalDoctors(){
        return new ResponseEntity<>(doctorQueryService.totalDoctors(), HttpStatus.OK);
    }
}
