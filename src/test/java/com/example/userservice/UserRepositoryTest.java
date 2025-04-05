package com.example.userservice;

import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

//    @AfterEach
//    public void cleanup() {
//        userRepository.deleteAll();
//    }

//    @Test
//    public void testFindByUsername() {
//        // Check if username or email already exists
//        if (userRepository.existsByUsername("testUser") || userRepository.existsByEmail("test@example.com")) {
//            // Username or email already exists, skip the test
//            System.out.println("Username or email already exists, skipping the test.");
//            return;
//        }
//
//        // Save the new user
//        User user = new User("testUser", "test@example.com", "password");
//        userRepository.save(user);
//
//        // Perform the test
//        Optional<User> foundUser = userRepository.findByUsername("testUser");
//        assertThat(foundUser).isPresent();
//        assertThat(foundUser.get().getEmail()).isEqualTo("test@example.com");
//    }

    // Add more tests for other repository methods
}
