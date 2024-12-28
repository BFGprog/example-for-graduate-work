package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Класс, представляющий DTO для передачи данных о пользователе.
 * Используется для передачи информации о пользователе между слоями приложения.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    /**
     * ID пользователя.
     * Поле обязательно для заполнения.
     */
    @Schema(type = "integer", format = "int32", description = "ID пользователя")
    @NotNull(message = "ID пользователя обязательно")
    private Integer id;

    /**
     * Логин пользователя (электронная почта).
     * Поле обязательно для заполнения.
     */
    @Schema(type = "string", description = "логин пользователя")
    @Email(message = "Некорректный формат электронной почты")
    @NotBlank(message = "Логин обязателен")
    private String email;

    /**
     * Имя пользователя.
     * Поле обязательно для заполнения.
     */
    @Schema(type = "string", description = "имя пользователя")
    @NotBlank(message = "Имя обязательно")
    private String firstName;

    /**
     * Фамилия пользователя.
     * Поле обязательно для заполнения.
     */
    @Schema(type = "string", description = "фамилия пользователя")
    @NotBlank(message = "Фамилия обязательна")
    private String lastName;

    /**
     * Телефон пользователя.
     * Поле обязательно для заполнения.
     */
    @Schema(type = "string", description = "телефон пользователя")
    @NotBlank(message = "Телефон обязателен")
    private String phone;

    /**
     * Роль пользователя.
     * Поле обязательно для заполнения.
     */
    @Schema(type = "string", description = "роль пользователя", allowableValues = {"USER", "ADMIN"})
    @NotNull(message = "Роль обязательна")
    private RoleDto roleDto;

    /**
     * Ссылка на аватар пользователя.
     * Поле обязательно для заполнения.
     */
    @Schema(type = "string", description = "ссылка на аватар пользователя")
    @NotBlank(message = "Ссылка на изображение обязательна")
    private String image;
}