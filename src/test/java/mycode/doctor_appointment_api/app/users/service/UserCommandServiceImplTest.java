package mycode.doctor_appointment_api.app.users.service;


import mycode.doctor_appointment_api.app.system.security.UserRole;
import mycode.doctor_appointment_api.app.users.dto.CreateUserRequest;
import mycode.doctor_appointment_api.app.users.dto.UpdateUserRequest;
import mycode.doctor_appointment_api.app.users.dto.UserResponse;
import mycode.doctor_appointment_api.app.users.exceptions.NoUserFound;
import mycode.doctor_appointment_api.app.users.exceptions.UserAlreadyExists;
import mycode.doctor_appointment_api.app.users.model.User;
import mycode.doctor_appointment_api.app.users.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserCommandServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;


    @InjectMocks
    private UserCommandServiceImpl userCommandService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateUserSuccessfully() {
        String encodedPassword = "encodedPassword";
        CreateUserRequest request = new CreateUserRequest(
                "John Doe",
                "john@example.com",
                "password123"
        );

        when(passwordEncoder.encode("password123")).thenReturn(encodedPassword);
        when(userRepository.findAll()).thenReturn(new ArrayList<>());

        User savedUser = User.builder()
                .id(1L)
                .email("john@example.com")
                .password(encodedPassword)
                .fullName("John Doe")
                .userRole(UserRole.CLIENT)
                .build();

        when(userRepository.saveAndFlush(any(User.class))).thenReturn(savedUser);

        UserResponse response = userCommandService.createUser(request);

        assertEquals("john@example.com", response.email());
        assertEquals("John Doe", response.fullName());

        verify(userRepository).saveAndFlush(any(User.class));
        verify(passwordEncoder).encode("password123");
    }

    @Test
    void shouldThrowExceptionWhenUserAlreadyExists() {
        CreateUserRequest request = new CreateUserRequest(
                "John Doe",
                "existing@example.com",
                "password123"
        );

        List<User> existingUsers = new ArrayList<>();
        User existingUser = User.builder()
                .email("existing@example.com")
                .build();
        existingUsers.add(existingUser);

        when(userRepository.findAll()).thenReturn(existingUsers);

        assertThrows(UserAlreadyExists.class, () -> userCommandService.createUser(request));

        verify(userRepository, never()).saveAndFlush(any(User.class));
    }

    @Test
    void shouldDeleteUserSuccessfully() {
        User user = User.builder()
                .id(1L)
                .email("john@example.com")
                .fullName("John Doe")
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserResponse response = userCommandService.deleteUser(1L);

        assertEquals("john@example.com", response.email());
        assertEquals("John Doe", response.fullName());

        verify(userRepository).delete(user);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentUser() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NoUserFound.class, () -> userCommandService.deleteUser(99L));

        verify(userRepository, never()).delete(any(User.class));
    }

    @Test
    void shouldUpdateUserSuccessfully() {
        User existingUser = User.builder()
                .id(1L)
                .email("old@example.com")
                .fullName("Old Name")
                .build();

        UpdateUserRequest updateRequest = new UpdateUserRequest(
                "New Name",
                "new@example.com"
        );

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.findAll()).thenReturn(new ArrayList<>());

        UserResponse response = userCommandService.updateUser(updateRequest, 1L);

        assertEquals("new@example.com", response.email());
        assertEquals("New Name", response.fullName());
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        User updatedUser = userCaptor.getValue();
        assertEquals("new@example.com", updatedUser.getEmail());
        assertEquals("New Name", updatedUser.getFullName());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingWithExistingEmail() {
        User user1 = User.builder()
                .id(1L)
                .email("user1@example.com")
                .build();

        User user2 = User.builder()
                .id(2L)
                .email("user2@example.com")
                .build();

        List<User> userList = new ArrayList<>();
        userList.add(user2);

        UpdateUserRequest updateRequest = new UpdateUserRequest(
                "Updated Name",
                "user2@example.com"
        );

        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        when(userRepository.findAll()).thenReturn(userList);

        assertThrows(UserAlreadyExists.class, () -> userCommandService.updateUser(updateRequest, 1L));

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistentUser() {
        UpdateUserRequest updateRequest = new UpdateUserRequest(
                "name",
                "email@example.com"
        );

        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NoUserFound.class, () -> userCommandService.updateUser(updateRequest, 99L));

        verify(userRepository, never()).save(any(User.class));
    }
}