package ru.skypro.homework.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
    private Integer id;

    /**
     * Путь к изображению в файловой системе или URL.
     * Поле обязательно для заполнения (валидация через @NotNull).
     */
    @Column(name = "filepath")
    private String filePath; // Путь к файлу изображения

    @Column(name = "pathforendpoint")
    private String pathForEndpoint; // Путь для доступа к изображению через API
//    /**
//     * Бинарные данные изображения (опционально).
//     * Используется тип LONGBLOB для хранения больших объектов.
//     */
//    @Lob
//    @Column(nullable = false)
//    private byte[] data;
    @Column(name = "mediatype")
    private String mediaType; // Тип медиа (например, image/png)

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ads_id")
    private Ad ad; // Связь с объявлением, к которому относится изображение


}