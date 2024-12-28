package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Класс, представляющий DTO для расширенных данных об объявлении.
 * Используется для передачи данных об объявлении и информации об авторе.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExtendedAdDto {

    /**
     * ID объявления.
     * Поле обязательно для заполнения.
     */
    @Schema(type = "integer", description = "id объявления")
    @NotNull(message = "ID объявления обязательно")
    private Integer pk;

    /**
     * Имя автора объявления.
     * Поле обязательно для заполнения.
     */
    @Schema(type = "string", description = "имя автора объявления")
    @NotBlank(message = "Имя автора обязательно")
    private String authorFirstName;

    /**
     * Фамилия автора объявления.
     * Поле обязательно для заполнения.
     */
    @Schema(type = "string", description = "фамилия автора объявления")
    @NotBlank(message = "Фамилия автора обязательна")
    private String authorLastName;

    /**
     * Описание объявления.
     * Поле обязательно для заполнения.
     */
    @Schema(type = "string", description = "описание объявления")
    @NotBlank(message = "Описание обязательно")
    private String description;

    /**
     * Логин автора объявления.
     * Поле обязательно для заполнения.
     */
    @Schema(type = "string", description = "логин автора объявления")
    @NotBlank(message = "Логин автора обязателен")
    private String email;

    /**
     * Ссылка на изображение объявления.
     * Поле обязательно для заполнения.
     */
    @Schema(type = "string", description = "ссылка на картинку объявления")
    @NotBlank(message = "Ссылка на изображение обязательна")
    private String image;

    /**
     * Телефон автора объявления.
     * Поле обязательно для заполнения.
     */
    @Schema(type = "string", description = "телефон автора объявления")
    @NotBlank(message = "Телефон автора обязателен")
    private String phone;

    /**
     * Цена объявления.
     * Поле не может быть отрицательным.
     */
    @Schema(type = "integer", description = "цена объявления")
    @Min(value = 0, message = "Цена не может быть отрицательной")
    private Integer price;

    /**
     * Заголовок объявления.
     * Поле обязательно для заполнения.
     */
    @Schema(type = "string", description = "заголовок объявления")
    @NotBlank(message = "Заголовок обязателен")
    private String title;
}