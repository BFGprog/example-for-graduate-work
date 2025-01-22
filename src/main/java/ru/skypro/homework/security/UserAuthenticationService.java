package ru.skypro.homework.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.UserRepository;

import java.util.Optional;

/**
 * Сервис для аутентификации пользователей.
 * Реализует интерфейс UserDetailsService для интеграции с Spring Security.
 */
@Slf4j
@Service
@RequiredArgsConstructor


public class UserAuthenticationService implements UserDetailsService{
    private static final Logger logger = LoggerFactory.getLogger(UserAuthenticationService.class);
    private final UserRepository userRepository;

    /**
     * Получает данные о текущем пользователе.
     * @return объект UserDetails, содержащий данные пользователя
     * @throws UsernameNotFoundException если пользователь не найден
     */

    public UserDetails getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return (UserDetails) authentication.getPrincipal();
    }

    public User getCurrentUserFromDB() {
        String username=getCurrentUser().getUsername();
        logger.info("Fetching user from database with email: {}", username);
        return userRepository.findByEmail(username)
                .orElseThrow(() -> {
                    logger.error("User not found with email: {}", username);
                    return new UserNotFoundException("User not found with email: " + username);
                });
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Попытка аутентификации пользователя: {}", username);

        Optional<User> user = userRepository.findByEmail(username);
        return user.map(UserSecurity::new).orElseThrow(()->new UsernameNotFoundException("Invalid Username"));
}}