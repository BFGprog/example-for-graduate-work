package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.skypro.homework.dto.LoginDto;
import ru.skypro.homework.dto.RegisterDto;
import ru.skypro.homework.service.AuthService;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@Tag(name = "Авторизация и регистрация пользователя")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Авторизация пользователя")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        log.info("Attempting to login user: {}", loginDto.getUsername());
        if (authService.login(loginDto.getUsername(), loginDto.getPassword())) {
            log.info("User {} successfully logged in", loginDto.getUsername());
            return ResponseEntity.ok().build();
        } else {
            log.warn("Failed login attempt for user: {}", loginDto.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неверный логин или пароль");
        }
    }

    @PostMapping("/register")
    @Operation(summary = "Регистрация пользователя")
    public ResponseEntity<?> register(@RequestBody RegisterDto registerDto) {
        log.info("Attempting to register user: {}", registerDto.getUsername());
        if (authService.register(registerDto)) {
            log.info("User {} successfully registered", registerDto.getUsername());
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            log.warn("Failed registration attempt for user: {}", registerDto.getUsername());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Пользователь с таким email уже существует");
        }
    }
}