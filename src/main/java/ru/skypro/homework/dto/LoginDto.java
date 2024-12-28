package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Класс, представляющий DTO для аутентификации пользователя.
 * Используется для передачи данных логина и пароля.
 */
@Data
public class LoginDto {

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
}