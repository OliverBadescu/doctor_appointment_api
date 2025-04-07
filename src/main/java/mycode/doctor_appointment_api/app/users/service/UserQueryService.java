    package mycode.doctor_appointment_api.app.users.service;

    import mycode.doctor_appointment_api.app.users.dtos.UserResponse;
    import mycode.doctor_appointment_api.app.users.dtos.UserResponseList;
    import mycode.doctor_appointment_api.app.users.model.User;

    public interface UserQueryService {

        UserResponse findUserById(long id);

        UserResponseList getAllUsers();

        User findByEmail(String email);


    }
