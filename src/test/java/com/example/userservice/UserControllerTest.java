package com.example.userservice;

import com.example.userservice.controllers.UserController;
import com.example.userservice.model.User;
import com.example.userservice.payload.request.LoginRequest;
import com.example.userservice.payload.request.SignupRequest;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.service.UserService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    //@MockBean
    //private UserRepository userRepository;

    private final UserService userService = Mockito.mock(UserService.class);
    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);

    private final UserController userController = new UserController(userService, userRepository, encoder);


    @Test
    public void testRegisterUser() {
        SignupRequest signupRequest = new SignupRequest("testuser", "test@example.com", "password");

        when(userRepository.existsByUsername("testuser")).thenReturn(false);
        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(encoder.encode("password")).thenReturn("encodedPassword");

        ResponseEntity<?> response = userController.registerUser(signupRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testLoginUser() {
        LoginRequest loginRequest = new LoginRequest("username", "password");
        User user = new User("username", "email@example.com", "encodedPassword");
        when(userService.loginUser(anyString(), anyString())).thenReturn(user);
        when(encoder.matches(anyString(), anyString())).thenReturn(true);

        ResponseEntity<User> responseEntity = userController.loginUser(loginRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testGetUserById() {
        ObjectId userId = new ObjectId();
        User user = new User("username", "email@example.com", "encodedPassword");
        when(userService.getUserById(userId.toHexString())).thenReturn(user);

        ResponseEntity<User> responseEntity = userController.getUserById(userId.toHexString());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testUpdateUser() {
        User updatedUser = new User("updatedUsername", "updatedEmail@example.com", "encodedPassword");
        ObjectId userId = new ObjectId();
        when(userService.updateUser(anyString(), any(User.class))).thenReturn(updatedUser);

        ResponseEntity<User> responseEntity = userController.updateUser(userId.toHexString(), updatedUser);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testGetAllUsers() {
        List<User> userList = Collections.singletonList(new User("username", "email@example.com", "encodedPassword"));
        when(userService.getAllUsers()).thenReturn(userList);

        ResponseEntity<List<User>> responseEntity = userController.getAllUsers();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(userList, responseEntity.getBody());
    }

    @Test
    public void testDeleteUser() {
        ObjectId userId = new ObjectId();

        ResponseEntity<Void> responseEntity = userController.deleteUser(userId.toHexString());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
