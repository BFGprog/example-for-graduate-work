package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RegisterDto {
    @Schema(type = "string",
            description = "логин",
            minLength = 4,
            maxLength = 32)
    private String username;
    @Schema(type = "string",
            description = "пароль",
            minLength = 8,
            maxLength = 16)
    private String password;
    @Schema(type = "string",
            description = "имя пользователя",
            minLength = 2,
            maxLength = 16)
    private String firstName;
    @Schema(type = "string",
            description = "фамилия пользователя",
            minLength = 2,
            maxLength = 16)
    private String lastName;
    @Schema(type = "string",
            description = "телефон пользователя",
            pattern = "\\+7\\s?\\(?\\d{3}\\)?\\s?\\d{3}-?\\d{2}-?\\d{2}")
    private String phone;
    @Schema(type = "string",
            description = "роль пользователя",
            allowableValues = {"USER", "ADMIN"})
    private RoleDto roleDto;

    public RegisterDto(String username, String password, String firstName, String lastName, String phone, RoleDto roleDto) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.roleDto = roleDto;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public RoleDto getRoleDto() {
        return roleDto;
    }

    public void setRoleDto(RoleDto roleDto) {
        this.roleDto = roleDto;
    }
}
