package mycode.doctor_appointment_api;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class DoctorAppointmentApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DoctorAppointmentApiApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner() {

        return args -> {


        };
    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder(){
        return  new BCryptPasswordEncoder();
    }

}
