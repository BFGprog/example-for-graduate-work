package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Класс, представляющий DTO для списка объявлений.
 * Используется для передачи списка объявлений и общего количества объявлений.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ads {

    /**
     * Общее количество объявлений.
     * Поле не может быть отрицательным, но может быть равно 0.
     */
    @Schema(type = "integer", format = "int32", description = "общее количество объявлений")
    @Min(value = 0, message = "Количество объявлений не может быть отрицательным")
    private Integer count;

    /**
     * Список объявлений.
     * Поле может быть пустым, но не может быть null.
     */
    @Schema(description = "Список объявлений")
    @NotNull(message = "Список объявлений обязателен")
    private List<AdDto> results;
}