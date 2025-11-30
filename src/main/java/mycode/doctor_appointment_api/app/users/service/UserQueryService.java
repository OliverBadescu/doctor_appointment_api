package mycode.doctor_appointment_api.app.users.service;

import mycode.doctor_appointment_api.app.users.dto.UserResponse;
import mycode.doctor_appointment_api.app.users.dto.UserResponseList;
import mycode.doctor_appointment_api.app.users.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserQueryService {

    UserResponse findUserById(long id);

    UserResponseList getAllUsers();

    User findByEmail(String email);

    int totalUsers();

    Page<UserResponse> getAllUsersPaginated(Pageable pageable);
}
