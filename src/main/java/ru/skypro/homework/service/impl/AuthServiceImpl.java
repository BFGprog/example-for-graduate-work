package ru.skypro.homework.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
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

        if (userRepository.findByEmail(registerDto.getUsername()).isPresent()) {
            logger.warn("Registration attempt failed: User with email {} already exists", registerDto.getUsername());
            throw new IllegalArgumentException(registerDto.getUsername() + " Mail is already registered");
            }
        User users = userMapper.toUsers(registerDto);
        users.setPassword(encoder.encode(registerDto.getPassword()));
        userRepository.save(users);
        return true;
    }

}
