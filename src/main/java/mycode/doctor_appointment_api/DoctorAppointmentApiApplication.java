package mycode.doctor_appointment_api;

import mycode.doctor_appointment_api.app.appointments.repository.AppointmentRepository;
import mycode.doctor_appointment_api.app.doctor.repository.DoctorRepository;
import mycode.doctor_appointment_api.app.patient.repository.PatientRepository;
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
    CommandLineRunner commandLineRunner(PatientRepository patientRepository, DoctorRepository doctorRepository, AppointmentRepository appointmentRepository) {

        return args -> {


        };
    }

}
