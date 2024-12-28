package ru.skypro.homework.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Класс, представляющий сущность "Изображение".
 * Используется для хранения бинарных данных изображений.
 */
@Entity
@Table(name = "image")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Image {

    /**
     * Уникальный идентификатор изображения (первичный ключ).
     * Генерируется автоматически при добавлении записи в базу данных.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Бинарные данные изображения.
     * Используется тип LONGBLOB для хранения больших объектов.
     * Поле обязательно для заполнения (валидация через @NotNull).
     */
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    @NotNull(message = "Данные изображения обязательны")
    private byte[] data;
}