package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

/**
 * Класс, представляющий DTO для создания или обновления объявления.
 * Используется для передачи данных об объявлении между слоями приложения.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrUpdateAdDto {

    /**
     * Заголовок объявления.
     * Должен содержать от 4 до 32 символов.
     * Поле обязательно для заполнения.
     */
    @Schema(type = "string", description = "заголовок объявления", minLength = 4, maxLength = 32)
    @Size(min = 4, max = 32, message = "Заголовок должен содержать от 4 до 32 символов")
    @NotBlank(message = "Заголовок обязателен")
    private String title;

    /**
     * Цена объявления.
     * Должна быть в диапазоне от 0 до 10 000 000.
     * Поле обязательно для заполнения.
     */
    @Schema(type = "integer", format = "int32", description = "цена объявления", minimum = "0", maximum = "10000000")
    @Min(value = 0, message = "Цена не может быть отрицательной")
    @Max(value = 10000000, message = "Цена не может превышать 10 000 000")
    @NotNull(message = "Цена обязательна")
    private Integer price;

    /**
     * Описание объявления.
     * Должно содержать от 8 до 64 символов.
     * Поле обязательно для заполнения.
     */
    @Schema(type = "string", description = "описание объявления", minLength = 8, maxLength = 64)
    @Size(min = 8, max = 64, message = "Описание должно содержать от 8 до 64 символов")
    @NotBlank(message = "Описание обязательно")
    private String description;
}