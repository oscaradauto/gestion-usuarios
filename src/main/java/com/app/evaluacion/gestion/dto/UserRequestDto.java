package com.app.evaluacion.gestion.dto;

import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Email;

public class UserRequestDto {

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @NotBlank(message = "Correo es obligatorio")
    @Email(message = "Formato de correo invalido")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "El correo debe tener un dominio valido")
    private String email;


    @NotBlank(message = "Password es obligatorio")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[0-9]).{6,}$",
            message = "Password debe tener al menos 6 caracteres, incluir mayuscula y un numero"
    )
    private String password;


    private List<PhoneRequestDto> phones;

    public UserRequestDto() {}

    public UserRequestDto(String name, String email, String password, List<PhoneRequestDto> phones) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phones = phones;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<PhoneRequestDto> getPhones() {
        return phones;
    }

    public void setPhones(List<PhoneRequestDto> phones) {
        this.phones = phones;
    }
}
