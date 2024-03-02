package spring.hometeam.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import spring.hometeam.dto.RegisterUserDTO;
import spring.hometeam.dto.UserInfoDTO;
import spring.hometeam.entity.User;
import spring.hometeam.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterUser() throws Exception {
        User user = new User(); // Populate with necessary data
        when(userRepository.save(user)).thenReturn(user);
        RegisterUserDTO registerUserDTO =  new RegisterUserDTO();

        User registered = userService.registerUser(registerUserDTO);

        assertEquals(user, registered);
        verify(userRepository).save(user);
    }

    @Test
    public void testUpdateUser() {
        User user = new User(); // Populate with necessary data
        when(userRepository.save(user)).thenReturn(user);

        User updated = userService.updateUser(user);

        assertEquals(user, updated);
        verify(userRepository).save(user);
    }

    @Test
    public void testGetUserById() {
        int userId = 1;
        User user = new User(); // Populate with necessary data
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Optional<UserInfoDTO> foundUser = userService.getUserById(userId);

        assertTrue(foundUser.isPresent());
        assertEquals(user, foundUser.get());
        verify(userRepository).findById(userId);
    }

    @Test
    public void testDeleteUserById() {
        int userId = 1;

        userService.deleteUserById(userId);

        verify(userRepository).deleteById(userId);
    }
}