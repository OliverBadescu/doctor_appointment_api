package mycode.doctor_appointment_api.app.system.security;

import mycode.doctor_appointment_api.app.doctor.repository.DoctorRepository;
import mycode.doctor_appointment_api.app.users.model.User;
import mycode.doctor_appointment_api.app.users.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDetailsImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;

    public UserDetailsImpl(UserRepository userRepository, DoctorRepository doctorRepository) {
        this.userRepository = userRepository;
        this.doctorRepository = doctorRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .<UserDetails>map(user -> user)
                .or(() -> doctorRepository.findByEmail(email))
                .orElseThrow(() -> new UsernameNotFoundException("No user or doctor found with email: " + email));
    }
}
