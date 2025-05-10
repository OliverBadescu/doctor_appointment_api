package mycode.doctor_appointment_api.app.doctor.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mycode.doctor_appointment_api.app.appointments.dtos.DoctorAppointmentList;
import mycode.doctor_appointment_api.app.doctor.dtos.*;
import mycode.doctor_appointment_api.app.doctor.mapper.DoctorMapper;
import mycode.doctor_appointment_api.app.doctor.model.Doctor;
import mycode.doctor_appointment_api.app.doctor.service.DoctorCommandService;
import mycode.doctor_appointment_api.app.doctor.service.DoctorQueryService;
import mycode.doctor_appointment_api.app.system.jwt.JWTTokenProvider;
import mycode.doctor_appointment_api.app.users.dtos.LoginRequest;
import mycode.doctor_appointment_api.app.users.dtos.LoginResponse;
import mycode.doctor_appointment_api.app.users.model.User;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static mycode.doctor_appointment_api.app.system.constants.Constants.JWT_TOKEN_HEADER;

@RestController
@AllArgsConstructor
@CrossOrigin
@Slf4j
@RequestMapping("/api/v1/doctor")
public class DoctorController {

    private DoctorQueryService doctorQueryService;
    private DoctorCommandService doctorCommandService;
    private final JWTTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_DOCTOR')")
    @GetMapping(path = "/getAllDoctorAppointments/{doctorId}")
    ResponseEntity<DoctorAppointmentList> getAllDoctorAppointments(@PathVariable int doctorId) {
        return new ResponseEntity<>(doctorQueryService.getAllDoctorAppointments(doctorId), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(path = "/getDoctor/{doctorId}")
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

    @PostMapping("/login/doctor")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest user) {

        Doctor loginUser = doctorQueryService.findByEmail(user.email());
        Doctor userPrincipal = getUser(loginUser);

        authenticate(user.email(), user.password());
        HttpHeaders jwtHeader = getJwtHeader(userPrincipal);
        LoginResponse loginResponse = new LoginResponse(
                jwtHeader.getFirst(JWT_TOKEN_HEADER),
                (long) userPrincipal.getId(),
                userPrincipal.getFullName(),
                userPrincipal.getEmail(),
                userPrincipal.getUserRole()
        );
        return new ResponseEntity<>(loginResponse, jwtHeader, HttpStatus.OK);
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    private Doctor getUser(Doctor loginUser) {
        Doctor userPrincipal = new Doctor();
        userPrincipal.setEmail(loginUser.getEmail());
        userPrincipal.setId(loginUser.getId());
        userPrincipal.setPassword(loginUser.getPassword());
        userPrincipal.setUserRole(loginUser.getUserRole());
        userPrincipal.setFullName(loginUser.getFullName());
        userPrincipal.setPhone(loginUser.getPhone());
        userPrincipal.setSpecialization(loginUser.getSpecialization());
        return userPrincipal;
    }

    private HttpHeaders getJwtHeader(Doctor user) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(JWT_TOKEN_HEADER, jwtTokenProvider.generateJWTToken(user));
        return headers;
    }


}
