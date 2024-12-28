package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Класс, представляющий DTO для регистрации пользователя.
 * Используется для передачи данных при регистрации.
 */
@Data
public class RegisterDto {

    /**
     * Логин пользователя.
     * Должен содержать от 4 до 32 символов.
     * Поле обязательно для заполнения.
     */
    @Schema(type = "string", description = "логин", minLength = 4, maxLength = 32)
    @Size(min = 4, max = 32, message = "Логин должен содержать от 4 до 32 символов")
    @NotBlank(message = "Логин обязателен")
    private String username;

    /**
     * Пароль пользователя.
     * Должен содержать от 8 до 16 символов.
     * Поле обязательно для заполнения.
     */
    @Schema(type = "string", description = "пароль", minLength = 8, maxLength = 16)
    @Size(min = 8, max = 16, message = "Пароль должен содержать от 8 до 16 символов")
    @NotBlank(message = "Пароль обязателен")
    private String password;

    /**
     * Имя пользователя.
     * Должно содержать от 2 до 16 символов.
     * Поле обязательно для заполнения.
     */
    @Schema(type = "string", description = "имя пользователя", minLength = 2, maxLength = 16)
    @Size(min = 2, max = 16, message = "Имя должно содержать от 2 до 16 символов")
    @NotBlank(message = "Имя обязательно")
    private String firstName;

    /**
     * Фамилия пользователя.
     * Должна содержать от 2 до 16 символов.
     * Поле обязательно для заполнения.
     */
    @Schema(type = "string", description = "фамилия пользователя", minLength = 2, maxLength = 16)
    @Size(min = 2, max = 16, message = "Фамилия должна содержать от 2 до 16 символов")
    @NotBlank(message = "Фамилия обязательна")
    private String lastName;

    /**
     * Номер телефона пользователя.
     * Должен соответствовать формату +7 (XXX) XXX-XX-XX.
     * Поле обязательно для заполнения.
     */
    @Schema(type = "string", description = "телефон пользователя", pattern = "\\+7\\s?\\(?\\d{3}\\)?\\s?\\d{3}-?\\d{2}-?\\d{2}")
    @Pattern(regexp = "\\+7\\s?\\(?\\d{3}\\)?\\s?\\d{3}-?\\d{2}-?\\d{2}", message = "Номер телефона должен соответствовать формату +7 (XXX) XXX-XX-XX")
    @NotBlank(message = "Телефон обязателен")
    private String phone;

    /**
     * Роль пользователя.
     * Поле обязательно для заполнения.
     */
    @Schema(type = "string", description = "роль пользователя", allowableValues = {"USER", "ADMIN"})
    @NotNull(message = "Роль обязательна")
    private RoleDto roleDto;
}