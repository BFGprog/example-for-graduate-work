package ru.skypro.homework.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UsersService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UsersServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String setPassword(NewPasswordDto newPasswordDto) {
        // Получаем текущего аутентифицированного пользователя
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        // Находим пользователя по email
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return "Пользователь не найден";
        }

        User user = userOptional.get();

        // Проверяем, совпадает ли текущий пароль
        if (!passwordEncoder.matches(newPasswordDto.getCurrentPassword(), user.getPassword())) {
            return "Текущий пароль неверен";
        }

        // Устанавливаем новый пароль
        user.setPassword(passwordEncoder.encode(newPasswordDto.getNewPassword()));
        userRepository.save(user);

        return "Пароль успешно изменен";
    }

    @Override
    public UserDto getUser() {
        // Получаем текущего аутентифицированного пользователя
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        // Находим пользователя по email
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("Пользователь не найден");
        }

        User user = userOptional.get();

        // Преобразуем User в UserDto
        return new UserDto(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhone(),
                user.getRole(),
                user.getImage() != null ? user.getImage().getPath().toString() : null
        );
    }

    @Override
    public String updateUser(UserDto userDto) {
        // Получаем текущего аутентифицированного пользователя
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        // Находим пользователя по email
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return "Пользователь не найден";
        }

        User user = userOptional.get();

        // Обновляем данные пользователя
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPhone(userDto.getPhone());

        userRepository.save(user);

        return "Данные пользователя успешно обновлены";
    }

    @Override
    public String uploadImage(MultipartFile file) throws IOException {
        // Получаем текущего аутентифицированного пользователя
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        // Находим пользователя по email
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return "Пользователь не найден";
        }

        User user = userOptional.get();

        // Сохраняем файл на сервере
        String uploadDir = "uploads/";
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Удаляем старое изображение, если оно есть
        if (user.getImage() != null && user.getImage().getPath() != null) {
            Path oldFilePath = Paths.get(user.getImage().getPath());
            if (Files.exists(oldFilePath)) {
                Files.delete(oldFilePath);
            }
        }

        // Сохраняем новое изображение
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath);

        // Обновляем ссылку на изображение у пользователя
        if (user.getImage() == null) {
            user.setImage(new Image());
        }
        user.getImage().setPath(filePath.toString());
        userRepository.save(user);

        return "Изображение успешно загружено";
    }
}