package mycode.doctor_appointment_api.app.users.service;

import mycode.doctor_appointment_api.app.users.dtos.UserResponse;
import mycode.doctor_appointment_api.app.users.dtos.UserResponseList;
import mycode.doctor_appointment_api.app.users.model.User;

/**
 * Service interface for querying user data.
 */
public interface UserQueryService {

    /**
     * Finds a user by their ID.
     *
     * @param id user ID
     * @return user response DTO
     */
    UserResponse findUserById(long id);

    /**
     * Retrieves a list of all users.
     *
     * @return list of user response DTOs
     */
    UserResponseList getAllUsers();

    /**
     * Finds a user by their email.
     *
     * @param email user email
     * @return user entity
     */
    User findByEmail(String email);

    /**
     * Gets the total number of users.
     *
     * @return total user count
     */
    int totalUsers();
}
