package com.app.evaluacion.gestion.mapper;

import com.app.evaluacion.gestion.dto.*;
import com.app.evaluacion.gestion.model.Phone;
import com.app.evaluacion.gestion.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    private final ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserResponseDto toDto(User user) {
        UserResponseDto dto = modelMapper.map(user, UserResponseDto.class);

        return dto;
    }

    public User toEntity(UserRequestDto dto) {
        User user = modelMapper.map(dto, User.class);

        if (dto.getPhones() != null) {
            List<Phone> phones = dto.getPhones().stream()
                    .map(this::toEntityPhone)
                    .collect(Collectors.toList());
            phones.forEach(phone -> phone.setUser(user));
            user.setPhones(phones);
        }

        return user;
    }

    public void updateEntity(UserRequestDto dto, User user) {
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());

        if (dto.getPhones() != null) {
            user.getPhones().clear();

            dto.getPhones().stream()
                    .map(this::toEntityPhone)
                    .forEach(phone -> {
                        phone.setUser(user);
                        user.getPhones().add(phone);
                    });
        }
    }



    public Phone toEntityPhone(PhoneRequestDto dto) {
        return modelMapper.map(dto, Phone.class);
    }

    public PhoneResponseDto toDtoPhone(Phone phone) {
        return modelMapper.map(phone, PhoneResponseDto.class);
    }
}
