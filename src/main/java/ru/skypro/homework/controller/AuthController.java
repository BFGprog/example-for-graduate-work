package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.skypro.homework.dto.LoginDto;
import ru.skypro.homework.dto.RegisterDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.service.AuthService;

import javax.validation.Valid;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@Tag(name = "Авторизация и регистрация пользователя")
public class AuthController {

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    private final AuthService authService;

    @PostMapping("/users")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDto userDto) {
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    @Operation(summary = "Авторизация пользователя")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto) {
        if (authService.login(loginDto.getUsername(), loginDto.getPassword())) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неверный логин или пароль");
        }
    }

    @PostMapping("/register")
    @Operation(summary = "Регистрация пользователя")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDto registerDto) {
        if (authService.register(registerDto)) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Пользователь с таким email уже существует");
        }
    }
}