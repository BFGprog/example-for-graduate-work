package ru.skypro.homework.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.UserRepository;

/**
 * Сервис для управления контекстом пользователя.
 * <p>
 * Этот класс предоставляет методы для получения текущего аутентифицированного пользователя
 * и его данных из базы данных.
 * </p>
 */
@Service
public class UserContextService {
    private static final Logger logger = LoggerFactory.getLogger(UserContextService.class);
    private final UserRepository usersRepository;

    public UserContextService(UserRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    /**
     * Получает текущего аутентифицированного пользователя.
     *
     * @return объект UserDetails текущего пользователя.
     */
    public UserDetails getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Извлечен текущий пользователь: {}", authentication.getName());
        return (UserDetails) authentication.getPrincipal();
    }

    /**
     * Получает текущего аутентифицированного пользователя из базы данных.
     *
     * @return объект Users текущего пользователя.
     */
    public User getCurrentUserFromDb() {
        String username = getCurrentUser().getUsername();
        logger.info("Fetching user from database with email: {}", username);

        return usersRepository.findByEmail(username)
                .orElseThrow(() -> {
                    logger.error("User not found with email: {}", username);
                    return new UserNotFoundException("User not found with email: " + username);
                });
    }
}
