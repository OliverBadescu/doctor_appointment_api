package mycode.doctor_appointment_api.app.clinic.web;

import lombok.AllArgsConstructor;
import mycode.doctor_appointment_api.app.clinic.dtos.ClinicResponse;
import mycode.doctor_appointment_api.app.clinic.dtos.CreateClinicRequest;
import mycode.doctor_appointment_api.app.clinic.dtos.UpdateClinicRequest;
import mycode.doctor_appointment_api.app.clinic.service.ClinicCommandService;
import mycode.doctor_appointment_api.app.clinic.service.ClinicQueryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/clinic")
public class ClinicController {

    private ClinicCommandService clinicCommandService;
    private ClinicQueryService clinicQueryService;

    @GetMapping(path = "/{clinicId}")
    ResponseEntity<ClinicResponse> getClinic(@PathVariable int clinicId){

        return new ResponseEntity<>(clinicQueryService.getClinicById(clinicId), HttpStatus.ACCEPTED);

    }

    @PostMapping
    ResponseEntity<ClinicResponse> addClinic(@RequestBody CreateClinicRequest createClinicRequest){
        return new ResponseEntity<>(clinicCommandService.addClinic(createClinicRequest), HttpStatus.CREATED);
    }


    @PutMapping(path = "/{clinicId}")
    ResponseEntity<ClinicResponse> updateClinic(@RequestBody UpdateClinicRequest updateClinicRequest, @PathVariable int clinicId){
        return new ResponseEntity<>(clinicCommandService.updateClinic(clinicId, updateClinicRequest), HttpStatus.ACCEPTED);
    }

    @DeleteMapping(path = "/{clinicId}")
    ResponseEntity<ClinicResponse> deleteClinic(@PathVariable int clinicId){
        return new ResponseEntity<>(clinicCommandService.deleteClinic(clinicId), HttpStatus.ACCEPTED);
    }
}
