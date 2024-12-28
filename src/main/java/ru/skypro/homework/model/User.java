package ru.skypro.homework.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.dto.RoleDto;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * Класс, представляющий сущность "Пользователь".
 * Используется для хранения информации о пользователях системы.
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    /**
     * Уникальный идентификатор пользователя (первичный ключ).
     * Генерируется автоматически при добавлении записи в базу данных.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Электронная почта пользователя (логин).
     * Поле обязательно для заполнения и должно быть уникальным.
     */
    @Column(nullable = false, unique = true)
    @Email(message = "Некорректный формат электронной почты")
    @NotBlank(message = "Электронная почта обязательна")
    private String email;

    /**
     * Имя пользователя.
     * Поле обязательно для заполнения.
     */
    @Column(length = 255)
    @NotBlank(message = "Имя обязательно")
    private String firstName;

    /**
     * Фамилия пользователя.
     * Поле обязательно для заполнения.
     */
    @Column(length = 255)
    @NotBlank(message = "Фамилия обязательна")
    private String lastName;

    /**
     * Номер телефона пользователя.
     * Поле обязательно для заполнения.
     */
    @Column(length = 20)
    @NotBlank(message = "Телефон обязателен")
    private String phone;

    /**
     * Роль пользователя (например, USER, ADMIN).
     * Поле обязательно для заполнения.
     */
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Роль обязательна")
    private RoleDto roleDto;

    /**
     * Ссылка на аватар пользователя.
     * Поле обязательно для заполнения.
     */
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "image_id")
    @NotNull(message = "Изображение обязательно")
    private Image image;

    /**
     * Пароль пользователя.
     * Поле обязательно для заполнения.
     * Должен храниться в зашифрованном виде.
     */
    @Column(nullable = false)
    @NotNull(message = "Пароль обязателен")
    private String password;

}