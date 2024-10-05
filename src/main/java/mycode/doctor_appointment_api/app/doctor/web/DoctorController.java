package mycode.doctor_appointment_api.app.doctor.web;

import lombok.AllArgsConstructor;
import mycode.doctor_appointment_api.app.doctor.dtos.CreateDoctorRequest;
import mycode.doctor_appointment_api.app.doctor.dtos.DoctorResponse;
import mycode.doctor_appointment_api.app.doctor.dtos.UpdateDoctorRequest;
import mycode.doctor_appointment_api.app.doctor.service.DoctorCommandService;
import mycode.doctor_appointment_api.app.doctor.service.DoctorQueryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/doctor")
public class DoctorController {

    private DoctorQueryService doctorQueryService;
    private DoctorCommandService doctorCommandService;

    @GetMapping(path = "/{doctorId}")
    ResponseEntity<DoctorResponse> getDoctor(@PathVariable int doctorId){
        return new ResponseEntity<>(doctorQueryService.getDoctorById(doctorId), HttpStatus.ACCEPTED);
    }

    @PostMapping
    ResponseEntity<DoctorResponse> addDoctor(@RequestBody CreateDoctorRequest createDoctorRequest){
        return new ResponseEntity<>(doctorCommandService.addDoctor(createDoctorRequest), HttpStatus.ACCEPTED);
    }

    @PutMapping(path = "/{doctorId}")
    ResponseEntity<DoctorResponse> updateDoctor(@PathVariable int doctorId, @RequestBody UpdateDoctorRequest updateDoctorRequest){
        return new ResponseEntity<>(doctorCommandService.updateDoctor(updateDoctorRequest, doctorId), HttpStatus.ACCEPTED);
    }

    @DeleteMapping(path = "/{doctorId}")
    ResponseEntity<DoctorResponse> deleteDoctor(@PathVariable int doctorId){
        return new ResponseEntity<>(doctorCommandService.deleteDoctor(doctorId), HttpStatus.ACCEPTED);
    }
}
