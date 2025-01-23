package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
/**
 * DTO (Data Transfer Object) для создания или обновления объявления.
 * <p>
 * Этот класс представляет данные, необходимые для создания или обновления объявления,
 * включая заголовок, цену и описание. Заголовок должен содержать от 4 до 32 символов,
 * цена - от 0 до 10,000,000, а описание - от 8 до 64 символов.
 * </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrUpdateAdDto {

    @NotBlank(message = "Заголовок обязателен")
    private String title;

    @Min(value = 0, message = "Цена не может быть отрицательной")
    @NotNull(message = "Цена обязательна")

    private String price;

    @Schema(type = "string", description = "Описание", minLength = 8, maxLength = 64)

    private String description;
}