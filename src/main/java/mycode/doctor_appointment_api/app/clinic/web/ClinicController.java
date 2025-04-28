package mycode.doctor_appointment_api.app.clinic.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mycode.doctor_appointment_api.app.clinic.dtos.ClinicResponse;
import mycode.doctor_appointment_api.app.clinic.dtos.ClinicResponseList;
import mycode.doctor_appointment_api.app.clinic.dtos.CreateClinicRequest;
import mycode.doctor_appointment_api.app.clinic.dtos.UpdateClinicRequest;
import mycode.doctor_appointment_api.app.clinic.service.ClinicCommandService;
import mycode.doctor_appointment_api.app.clinic.service.ClinicQueryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@CrossOrigin
@Slf4j
@RequestMapping("/clinic")
public class ClinicController {

    private ClinicCommandService clinicCommandService;
    private ClinicQueryService clinicQueryService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @GetMapping(path = "/{clinicId}")
    ResponseEntity<ClinicResponse> getClinic(@PathVariable int clinicId) {

        return new ResponseEntity<>(clinicQueryService.getClinicById(clinicId), HttpStatus.ACCEPTED);

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/createClinic")
    ResponseEntity<ClinicResponse> addClinic(@RequestBody CreateClinicRequest createClinicRequest) {
        return new ResponseEntity<>(clinicCommandService.addClinic(createClinicRequest), HttpStatus.CREATED);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(path = "/updateClinic/{clinicId}")
    ResponseEntity<ClinicResponse> updateClinic(@RequestBody UpdateClinicRequest updateClinicRequest, @PathVariable int clinicId) {
        return new ResponseEntity<>(clinicCommandService.updateClinic(clinicId, updateClinicRequest), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(path = "/deleteClinic/{clinicId}")
    ResponseEntity<ClinicResponse> deleteClinic(@PathVariable int clinicId) {
        return new ResponseEntity<>(clinicCommandService.deleteClinic(clinicId), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @GetMapping(path = "/getAllClinics")
    ResponseEntity<ClinicResponseList> getAllClinics(){
        return new ResponseEntity<>(clinicQueryService.getAllClinics(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/getTotalClinics")
    ResponseEntity<Integer> getTotalClinics(){
        return new ResponseEntity<>(clinicQueryService.getTotalClinics(), HttpStatus.OK);
    }
}
