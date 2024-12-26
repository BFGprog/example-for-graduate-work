package ru.skypro.homework.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.RegisterDto;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.security.UserAuthenticationService;
import ru.skypro.homework.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    private UserAuthenticationService userAuthenticationService;
    private UserRepository userRepository;
    private final UserMapper userMapper;

    private final PasswordEncoder encoder;

    public AuthServiceImpl(UserAuthenticationService userAuthenticationService, UserRepository userRepository, UserMapper userMapper, PasswordEncoder encoder) {
        this.userAuthenticationService = userAuthenticationService;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.encoder = encoder;
    }

    @Override
    public boolean login(String userName, String password) {
        if (userRepository.findByEmail(userName) == null) {
            return false;
        }
        UserDetails userDetails = userAuthenticationService.loadUserByUsername(userName);
        return encoder.matches(password, userDetails.getPassword());
    }

    @Override
    public boolean register(RegisterDto registerDto) {
        if (userRepository.findByEmail(registerDto.getUsername()) == null) {
            return false;
        }
        User registerUser = userMapper.toRegisterUser(registerDto);
        userRepository.save(registerUser);
        return true;
    }

}
