package com.app.evaluacion.gestion.service;

import com.app.evaluacion.gestion.dto.UserRequestDto;
import com.app.evaluacion.gestion.exception.EmailAlreadyExistsException;
import com.app.evaluacion.gestion.exception.UserNotFoundException;
import com.app.evaluacion.gestion.mapper.UserMapper;
import com.app.evaluacion.gestion.model.User;
import com.app.evaluacion.gestion.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }


    public User register(UserRequestDto dto) {

        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException(
                    String.format("Correo %s ya se encuentra registrado", dto.getEmail())
            );
        }

        User user = userMapper.toEntity(dto);
        return userRepository.save(user);
    }

    public User getUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format("Usuario con id %s no encontrado", id)
                ));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(String id, UserRequestDto dto) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format("Usuario con id %s no encontrado", id)));

        User existingByEmail = userRepository.findByEmail(dto.getEmail()).orElse(null);
        if (existingByEmail != null && !existingByEmail.getId().equals(id)) {
            throw new EmailAlreadyExistsException(
                    String.format("Correo %s ya esta en uso", dto.getEmail()));
        }

        userMapper.updateEntity(dto, user);
        user.setModified(LocalDateTime.now());
        return userRepository.save(user);
    }



    public void deleteUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format("Usuario con id %s no encontrado", id)));

        userRepository.delete(user);
    }
}
