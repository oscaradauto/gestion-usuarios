package com.app.evaluacion.gestion.service;

import com.app.evaluacion.gestion.dto.UserRequestDto;
import com.app.evaluacion.gestion.exception.EmailAlreadyExistsException;
import com.app.evaluacion.gestion.exception.UserNotFoundException;
import com.app.evaluacion.gestion.mapper.UserMapper;
import com.app.evaluacion.gestion.model.User;
import com.app.evaluacion.gestion.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserMapper userMapper;

    private UserService userService;

    private UserRequestDto userRequestDto;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        ModelMapper modelMapper = new ModelMapper();
        userMapper = new UserMapper(modelMapper);
        userService = new UserService(userRepository, userMapper);

        userRequestDto = new UserRequestDto();
        userRequestDto.setName("Oscar");
        userRequestDto.setEmail("oscar@email.com");
        userRequestDto.setPassword("Admin123");

        user = new User();
        user.setId("123");
        user.setName("Oscar");
        user.setEmail("oscar@email.com");
        user.setPassword("Admin123");
        user.setCreated(LocalDateTime.now());
        user.setActive(true);
    }


    @Test
    void register_UserNew() {
        when(userRepository.findByEmail(userRequestDto.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User user = userService.register(userRequestDto);

        assertNotNull(user);
        assertEquals("Oscar", user.getName());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void getUserById_UserExists() {
        when(userRepository.findById("123")).thenReturn(Optional.of(user));

        User user = userService.getUserById("123");

        assertNotNull(user);
        assertEquals("Oscar", user.getName());
    }

    @Test
    void getUserById_UserNotFound() {
        when(userRepository.findById("123")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserById("123"));
    }


    @Test
    void updateUser_EmailNotUsed_ShouldUpdateUser() {
        String userId = "123";

        UserRequestDto dto = new UserRequestDto();
        dto.setName("Oscar Updated");
        dto.setEmail("oscar.adauto@email.com");
        dto.setPassword("Pass123");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User updated = userService.updateUser(userId, dto);

        assertEquals("Oscar Updated", updated.getName());
        assertEquals("oscar.adauto@email.com", updated.getEmail());

    }

    @Test
    void deleteUser_UserExists() {
        when(userRepository.findById("123")).thenReturn(Optional.of(user));

        userService.deleteUser("123");

        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void deleteUser_UserNotFound() {
        when(userRepository.findById("123")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.deleteUser("123"));
    }



}
