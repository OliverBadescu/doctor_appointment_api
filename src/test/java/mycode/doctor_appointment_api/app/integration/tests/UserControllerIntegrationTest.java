package mycode.doctor_appointment_api.app.integration.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import mycode.doctor_appointment_api.app.system.security.UserRole;
import mycode.doctor_appointment_api.app.users.dto.CreateUserRequest;
import mycode.doctor_appointment_api.app.users.dto.LoginRequest;
import mycode.doctor_appointment_api.app.users.dto.UpdateUserRequest;
import mycode.doctor_appointment_api.app.users.mock.UserMockData;
import mycode.doctor_appointment_api.app.users.model.User;
import mycode.doctor_appointment_api.app.users.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private User testUser;

    @BeforeEach
    void setup() {
        userRepository.deleteAll();

        testUser = UserMockData.createUser("test@email.com", "Test User");
        testUser.setPassword(passwordEncoder.encode("testPassword123"));
        testUser = userRepository.save(testUser);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Should return zero total users when none exist")
    void totalUsersEmpty() throws Exception {
        userRepository.deleteAll();

        mockMvc.perform(get("/api/v1/user/totalUsers"))
                .andExpect(status().isOk())
                .andExpect(content().string("0"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Should return correct total users count")
    void getTotalUsers() throws Exception {
        User secondUser = UserMockData.createUser("second@email.com", "Second User");
        secondUser.setPassword(passwordEncoder.encode("password123"));
        userRepository.save(secondUser);

        mockMvc.perform(get("/api/v1/user/totalUsers"))
                .andExpect(status().isOk())
                .andExpect(content().string("2"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Should create user successfully")
    void addUser() throws Exception {
        CreateUserRequest request = new CreateUserRequest("New User", "newuser@test.com", "newPassword123");

        mockMvc.perform(post("/api/v1/user/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.email").value("newuser@test.com"))
                .andExpect(jsonPath("$.fullName").value("New User"))
                .andExpect(jsonPath("$.userRole").value("CLIENT"));

        assert userRepository.count() == 2;
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Should retrieve user by ID")
    void getUserById() throws Exception {
        mockMvc.perform(get("/api/v1/user/getUserById/" + testUser.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testUser.getId()))
                .andExpect(jsonPath("$.email").value("test@email.com"))
                .andExpect(jsonPath("$.fullName").value("Test User"))
                .andExpect(jsonPath("$.userRole").value("CLIENT"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Should return 404 when user not found")
    void getUserByIdNotFound() throws Exception {
        long nonExistentId = 999L;

        mockMvc.perform(get("/api/v1/user/getUserById/" + nonExistentId))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Should update user successfully")
    void updateUser() throws Exception {
        UpdateUserRequest request = new UpdateUserRequest("Updated Name", "updated@email.com");

        mockMvc.perform(put("/api/v1/user/update/" + testUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value(testUser.getId()))
                .andExpect(jsonPath("$.email").value("updated@email.com"))
                .andExpect(jsonPath("$.fullName").value("Updated Name"));

        User updatedUser = userRepository.findById(testUser.getId()).orElse(null);
        assert updatedUser != null;
        assert updatedUser.getEmail().equals("updated@email.com");
        assert updatedUser.getFullName().equals("Updated Name");
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Should return 404 when updating non-existent user")
    void updateUserNotFound() throws Exception {
        UpdateUserRequest request = new UpdateUserRequest("Updated Name", "updated@email.com");
        long nonExistentId = 999L;

        mockMvc.perform(put("/api/v1/user/update/" + nonExistentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Should delete user successfully")
    void deleteUser() throws Exception {
        long userId = testUser.getId();

        mockMvc.perform(delete("/api/v1/user/delete/" + userId))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.email").value("test@email.com"))
                .andExpect(jsonPath("$.fullName").value("Test User"));

        assert userRepository.findById(userId).isEmpty();
        assert userRepository.count() == 0;
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Should return 404 when deleting non-existent user")
    void deleteUserNotFound() throws Exception {
        long nonExistentId = 999L;

        mockMvc.perform(delete("/api/v1/user/delete/" + nonExistentId))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Should get all users")
    void getAllUsers() throws Exception {

        User secondUser = UserMockData.createUser("second@email.com", "Second User");
        secondUser.setPassword(passwordEncoder.encode("password123"));
        userRepository.save(secondUser);

        mockMvc.perform(get("/api/v1/user/getAllUsers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.list", hasSize(2)))
                .andExpect(jsonPath("$.list[0].email").exists())
                .andExpect(jsonPath("$.list[0].fullName").exists())
                .andExpect(jsonPath("$.list[1].email").exists())
                .andExpect(jsonPath("$.list[1].fullName").exists());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Should return empty list when no users exist")
    void getAllUsersEmpty() throws Exception {
        userRepository.deleteAll();

        mockMvc.perform(get("/api/v1/user/getAllUsers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.list", hasSize(0)));
    }

    @Test
    @DisplayName("Should register user successfully")
    void registerUser() throws Exception {
        CreateUserRequest request = new CreateUserRequest("Register User", "register@test.com", "registerPassword123");

        mockMvc.perform(post("/api/v1/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.jwtToken").exists())
                .andExpect(jsonPath("$.fullName").value("Register User"))
                .andExpect(jsonPath("$.email").value("register@test.com"))
                .andExpect(jsonPath("$.userRole").value("CLIENT"));


        assert userRepository.count() == 2;
        User registeredUser = userRepository.findByEmail("register@test.com").orElse(null);
        assert registeredUser != null;
        assert registeredUser.getFullName().equals("Register User");
        assert registeredUser.getUserRole() == UserRole.CLIENT;
    }

    @Test
    @DisplayName("Should login user successfully")
    void loginUser() throws Exception {
        LoginRequest request = new LoginRequest("test@email.com", "testPassword123");

        mockMvc.perform(post("/api/v1/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jwtToken").exists())
                .andExpect(jsonPath("$.id").value(testUser.getId()))
                .andExpect(jsonPath("$.fullName").value("Test User"))
                .andExpect(jsonPath("$.email").value("test@email.com"))
                .andExpect(jsonPath("$.userRole").value("CLIENT"));
    }

//    @Test
//    @DisplayName("Should return 401 for invalid login credentials")
//    void loginUserInvalidCredentials() throws Exception {
//        LoginRequest request = new LoginRequest("test@email.com", "wrongPassword");
//
//        mockMvc.perform(post("/api/v1/user/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isUnauthorized());
//    }

    @Test
    @DisplayName("Should return 400 for duplicate email registration")
    void registerUserDuplicateEmail() throws Exception {
        CreateUserRequest request = new CreateUserRequest("Duplicate User", "test@email.com", "duplicatePassword123");

        mockMvc.perform(post("/api/v1/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());

        assert userRepository.count() == 1;
    }






}