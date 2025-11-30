package mycode.doctor_appointment_api.app.users.mapper;


import mycode.doctor_appointment_api.app.users.dto.CreateUserRequest;
import mycode.doctor_appointment_api.app.users.dto.UserResponse;
import mycode.doctor_appointment_api.app.users.model.User;

public class UserMapper {

    public static UserResponse userToResponseDto(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getFullName(),
                user.getUserRole());
    }

    public static User userRequestDtoToUser(CreateUserRequest createUserRequest) {
        return User.builder()
                .email(createUserRequest.email())
                .fullName(createUserRequest.fullName())
                .password(createUserRequest.password()).build();
    }

}
