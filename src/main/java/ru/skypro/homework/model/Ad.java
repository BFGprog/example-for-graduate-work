package ru.skypro.homework.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Класс, представляющий сущность "Объявление".
 * Используется для хранения информации о товаре, размещенном на продажу.
 */
@Entity
@Table(name = "ad")
@Data // Автоматически генерирует геттеры, сеттеры, toString, equals и hashCode
@NoArgsConstructor // Конструктор без аргументов
@AllArgsConstructor // Конструктор со всеми аргументами
public class Ad {

    /**
     * Уникальный идентификатор объявления (первичный ключ).
     * Генерируется автоматически при добавлении записи в базу данных.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pk;

    /**
     * Ссылка на изображение товара.
     * Может быть длинным URL-адресом, поэтому используется тип TEXT.
     */
    @Column(columnDefinition = "TEXT")
    private String image;

    /**
     * Цена товара.
     * Не может быть отрицательной (валидация через @Min).
     * Поле обязательно для заполнения (валидация через @NotNull).
     */
    @Min(value = 0, message = "Цена не может быть отрицательной")
    @NotNull(message = "Цена обязательна")
    private Integer price;

    /**
     * Заголовок объявления.
     * Может быть длинным, поэтому используется тип TEXT.
     */
    @Column(columnDefinition = "TEXT")
    private String title;

    /**
     * Связь с пользователем, который создал объявление.
     * Используется ленивая загрузка (FetchType.LAZY) для оптимизации.
     * Поле обязательно для заполнения (nullable = false).
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}