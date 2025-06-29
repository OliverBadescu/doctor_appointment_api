package mycode.doctor_appointment_api;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Main entry point for the Doctor Appointment API Spring Boot application.
 * <p>
 * Configures the application context and provides necessary beans such as
 * the BCryptPasswordEncoder for password hashing.
 */
@SpringBootApplication
public class DoctorAppointmentApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DoctorAppointmentApiApplication.class, args);
    }

    /**
     * CommandLineRunner bean executed after the Spring Boot application
     * context is loaded and started.
     * <p>
     * Useful for running startup logic such as initializing default users,
     * database seeding, or testing bean wiring.
     *
     * @return CommandLineRunner instance
     */
    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {

        };
    }

    /**
     * Provides a BCryptPasswordEncoder bean used for password hashing.
     *
     * @return BCryptPasswordEncoder instance
     */
    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder(){
        return  new BCryptPasswordEncoder();
    }
}
