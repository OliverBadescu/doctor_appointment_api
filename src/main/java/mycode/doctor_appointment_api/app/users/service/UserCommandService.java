package mycode.doctor_appointment_api.app.users.service;

import mycode.doctor_appointment_api.app.users.dtos.CreateUserRequest;
import mycode.doctor_appointment_api.app.users.dtos.UpdateUserRequest;
import mycode.doctor_appointment_api.app.users.dtos.UserResponse;

public interface UserCommandService {

    UserResponse createUser(CreateUserRequest createUserRequest);

    UserResponse deleteUser(long id);

    UserResponse updateUser(UpdateUserRequest updateUserRequest, long id);

}
