package mycode.doctor_appointment_api.app.users.service;

import mycode.doctor_appointment_api.app.users.dto.CreateUserRequest;
import mycode.doctor_appointment_api.app.users.dto.UpdateUserRequest;
import mycode.doctor_appointment_api.app.users.dto.UserResponse;

public interface UserCommandService {

    UserResponse createUser(CreateUserRequest createUserRequest);

    UserResponse deleteUser(long id);

    UserResponse updateUser(UpdateUserRequest updateUserRequest, long id);

}
