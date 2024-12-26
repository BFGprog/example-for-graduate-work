package ru.skypro.homework.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UsersService;

import java.io.IOException;

@Service
public class UsersServiceImpl implements UsersService {
    ImageService imageService;
    UserRepository userRepository;
    UserMapper userMapper;

    public UsersServiceImpl(ImageService imageService, UserRepository userRepository, UserMapper userMapper) {
        this.imageService = imageService;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    private String objectAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ((UserDetails) authentication.getPrincipal()).getUsername();
    }

    @Override
    public String setPassword(NewPasswordDto newPasswordDto) {

        String username = objectAuthentication();
        User user = userRepository.findByEmail(username);
        user.setPassword(newPasswordDto.getNewPassword());
        userRepository.save(user);
        return null;
    }

    @Override
    public UserDto getUser() {

        String username = objectAuthentication();
        User user = userRepository.findByEmail(username);
        UserDto userDTO = userMapper.toDto(user);
        return userDTO;
    }

    @Override
    public String updateUser() {
        return null;
    }

    @Override
    public String uploadImage(MultipartFile file) throws IOException {

        String username = objectAuthentication();
        User user = userRepository.findByEmail(username);
        user.setImage(imageService.uploadImage(file));
        userRepository.save(user);
        return null;
    }
}
