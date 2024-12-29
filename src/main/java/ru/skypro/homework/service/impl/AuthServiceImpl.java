package ru.skypro.homework.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.RegisterDto;
import ru.skypro.homework.dto.RoleDto;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.security.UserAuthenticationService;
import ru.skypro.homework.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserAuthenticationService userAuthenticationService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;

    public AuthServiceImpl(UserAuthenticationService userAuthenticationService, UserRepository userRepository, UserMapper userMapper, PasswordEncoder encoder) {
        this.userAuthenticationService = userAuthenticationService;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.encoder = encoder;
    }

    @Override
    public boolean login(String username, String password) {
        try {
            // Пытаемся загрузить пользователя
            UserDetails userDetails = userAuthenticationService.loadUserByUsername(username);
            // Проверяем пароль
            return encoder.matches(password, userDetails.getPassword());
        } catch (UsernameNotFoundException e) {
            // Если пользователь не найден, регистрируем его
            return registerNewUser(username, password);
        }
    }

    @Override
    public boolean register(RegisterDto registerDto) {
        if (userRepository.findByEmail(registerDto.getUsername()) != null) {
            return false; // Пользователь уже существует
        }
        // Преобразуем RegisterDto в User
        User registerUser = userMapper.toRegisterUser(registerDto);
        // Кодируем пароль перед сохранением
        registerUser.setPassword(encoder.encode(registerDto.getPassword()));
        userRepository.save(registerUser);
        return true;
    }

    private boolean registerNewUser(String username, String password) {
        // Проверяем, существует ли пользователь с таким email
        if (userRepository.findByEmail(username) != null) {
            return false; // Пользователь уже существует
        }
        // Создаём нового пользователя с минимальными данными
        RegisterDto registerDto = new RegisterDto();
        registerDto.setUsername(username);
        registerDto.setPassword(password);
        registerDto.setFirstName("DefaultFirstName"); // Установите значение по умолчанию
        registerDto.setLastName("DefaultLastName"); // Установите значение по умолчанию
        registerDto.setPhone("+70000000000"); // Установите значение по умолчанию
        registerDto.setRoleDto(RoleDto.USER); // Установите роль по умолчанию

        // Преобразуем RegisterDto в User
        User newUser = userMapper.toRegisterUser(registerDto);
        // Кодируем пароль перед сохранением
        newUser.setPassword(encoder.encode(password));
        userRepository.save(newUser);
        return true;
    }
}