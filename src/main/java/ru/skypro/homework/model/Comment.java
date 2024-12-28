package ru.skypro.homework.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Класс, представляющий сущность "Комментарий".
 * Используется для хранения комментариев к объявлениям.
 */
@Entity
@Table(name = "comment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    /**
     * Уникальный идентификатор комментария (первичный ключ).
     * Генерируется автоматически при добавлении записи в базу данных.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Текст комментария.
     * Может быть длинным, поэтому используется тип TEXT.
     */
    @Column(columnDefinition = "TEXT")
    private String text;


    /**
     * Пользователь, оставивший комментарий.
     * Используется ленивая загрузка (FetchType.LAZY) для оптимизации.
     * Поле обязательно для заполнения (nullable = false).
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Объявление, к которому относится комментарий.
     * Используется ленивая загрузка (FetchType.LAZY) для оптимизации.
     * Поле обязательно для заполнения (nullable = false).
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ad_id", nullable = false)
    private Ad ad;
}