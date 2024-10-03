package mycode.doctor_appointment_api;

import jakarta.validation.Validator;
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
    CommandLineRunner commandLineRunner(){

        return args -> {


        };
    }

}
