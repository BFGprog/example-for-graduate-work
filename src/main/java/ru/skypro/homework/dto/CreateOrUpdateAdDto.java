package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrUpdateAdDto {

    @NotBlank(message = "Заголовок обязателен")
    private String title;

    @Min(value = 0, message = "Цена не может быть отрицательной")
    @NotNull(message = "Цена обязательна")
    private String price;

    @Schema(type = "string", description = "Описание объявления", minLength = 8, maxLength = 64)
    private String description;
}