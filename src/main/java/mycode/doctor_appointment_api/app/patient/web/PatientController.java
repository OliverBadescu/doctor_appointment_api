package mycode.doctor_appointment_api.app.patient.web;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mycode.doctor_appointment_api.app.patient.dtos.CreatePatientRequest;
import mycode.doctor_appointment_api.app.patient.dtos.PatientResponse;
import mycode.doctor_appointment_api.app.patient.dtos.PatientResponseList;
import mycode.doctor_appointment_api.app.patient.dtos.UpdatePatientRequest;
import mycode.doctor_appointment_api.app.patient.service.PatientCommandService;
import mycode.doctor_appointment_api.app.patient.service.PatientQueryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@CrossOrigin
@Slf4j
@RequestMapping("/patient")
public class PatientController {

    private PatientCommandService patientCommandService;
    private PatientQueryService patientQueryService;

    @GetMapping(path = "/{patientId}")
    public ResponseEntity<PatientResponse> getPatient(@PathVariable int patientId) {

        return new ResponseEntity<>(patientQueryService.findPatientById(patientId), HttpStatus.OK);

    }

    @PostMapping("/addPatient")
    public ResponseEntity<PatientResponse> addPatient(@RequestBody CreatePatientRequest createPatientRequest) {
        return new ResponseEntity<>(patientCommandService.addPatient(createPatientRequest), HttpStatus.CREATED);
    }


    @PutMapping(path = "/{patientId}")
    public ResponseEntity<PatientResponse> updatePatient(@RequestBody UpdatePatientRequest updatePatientRequest, @PathVariable int patientId) {
        return new ResponseEntity<>(patientCommandService.updatePatient(updatePatientRequest, patientId), HttpStatus.ACCEPTED);
    }


    @DeleteMapping(path = "/{patientId}")
    public ResponseEntity<PatientResponse> deletePatient(@PathVariable int patientId) {
        return new ResponseEntity<>(patientCommandService.deletePatient(patientId), HttpStatus.ACCEPTED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<PatientResponseList> getAll(){
        return new ResponseEntity<>(patientQueryService.getAllPatients(), HttpStatus.OK);
    }
}
