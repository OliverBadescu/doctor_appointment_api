package mycode.doctor_appointment_api.app.users.service;

import mycode.doctor_appointment_api.app.users.dtos.CreateUserRequest;
import mycode.doctor_appointment_api.app.users.dtos.UpdateUserRequest;
import mycode.doctor_appointment_api.app.users.dtos.UserResponse;

/**
 * Service interface for user management commands.
 */
public interface UserCommandService {

    /**
     * Creates a new user.
     *
     * @param createUserRequest data for creating a user
     * @return response with created user details
     */
    UserResponse createUser(CreateUserRequest createUserRequest);

    /**
     * Deletes a user by ID.
     *
     * @param id the user ID
     * @return response with deleted user details
     */
    UserResponse deleteUser(long id);

    /**
     * Updates an existing user.
     *
     * @param updateUserRequest data for updating the user
     * @param id the user ID
     * @return response with updated user details
     */
    UserResponse updateUser(UpdateUserRequest updateUserRequest, long id);

}
