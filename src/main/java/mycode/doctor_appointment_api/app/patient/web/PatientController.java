package mycode.doctor_appointment_api.app.patient.web;


import lombok.AllArgsConstructor;
import mycode.doctor_appointment_api.app.patient.dtos.CreatePatientRequest;
import mycode.doctor_appointment_api.app.patient.dtos.PatientResponse;
import mycode.doctor_appointment_api.app.patient.service.PatientCommandService;
import mycode.doctor_appointment_api.app.patient.service.PatientQueryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/patient")
public class PatientController {

    private PatientCommandService patientCommandService;
    private PatientQueryService patientQueryService;

    @GetMapping(path = "/{patientId}")
    public ResponseEntity<PatientResponse> getPatient(@PathVariable int patientId){

        return new ResponseEntity<>(patientQueryService.findPatientById(patientId), HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<PatientResponse> addPatient(@RequestBody CreatePatientRequest createPatientRequest){

    }

}
