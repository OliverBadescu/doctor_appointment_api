package mycode.doctor_appointment_api;

import jakarta.validation.Validator;
import mycode.doctor_appointment_api.app.appointments.repository.AppointmentRepository;
import mycode.doctor_appointment_api.app.clinic.repository.ClinicRepository;
import mycode.doctor_appointment_api.app.doctor.repository.DoctorRepository;
import mycode.doctor_appointment_api.app.patient.repository.PatientRepository;
import mycode.doctor_appointment_api.app.working_hours.repository.WorkingHoursRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DoctorAppointmentApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DoctorAppointmentApiApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(PatientRepository patientRepository, ClinicRepository clinicRepository, DoctorRepository doctorRepository, AppointmentRepository appointmentRepository, WorkingHoursRepository workingHoursRepository){

        return args -> {


        };
    }

}
