package mycode.doctor_appointment_api.app.users.service;

import lombok.AllArgsConstructor;
import mycode.doctor_appointment_api.app.users.dto.UserResponse;
import mycode.doctor_appointment_api.app.users.dto.UserResponseList;
import mycode.doctor_appointment_api.app.users.exceptions.NoUserFound;
import mycode.doctor_appointment_api.app.users.mapper.UserMapper;
import mycode.doctor_appointment_api.app.users.model.User;
import mycode.doctor_appointment_api.app.users.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@Service
public class UserQueryServiceImpl implements UserQueryService{

    private UserRepository userRepository;

    @Override
    public UserResponse findUserById(long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoUserFound("No user with this id found"));

        return UserMapper.userToResponseDto(user);
    }

    @Override
    public UserResponseList getAllUsers() {
        List<User> list = userRepository.findAll();
        List<UserResponse> responses = new ArrayList<>();

        list.forEach(user -> {
            responses.add(UserMapper.userToResponseDto(user));
        });

        return new UserResponseList(responses);
    }

    @Override
    public User findByEmail(String email) {

        return userRepository.findByEmail(email).orElseThrow(() -> new NoUserFound("No user with this email found"));
    }

    @Override
    public  int totalUsers(){
        return userRepository.findAll().size();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserResponse> getAllUsersPaginated(Pageable pageable) {
        Page<User> userPage = userRepository.findAll(pageable);
        return userPage.map(UserMapper::userToResponseDto);
    }

}
