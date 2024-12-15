package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UsersService;

import java.io.IOException;

@Service
public class UsersServiceImpl implements UsersService {
    ImageService imageService;

    public UsersServiceImpl(ImageService imageService) {
        this.imageService = imageService;
    }

    @Override
    public String setPassword(NewPasswordDto newPasswordDto) {
        return null;
    }

    @Override
    public UserDto getUser() {
        return null;
    }

    @Override
    public String updateUser() {
        return null;
    }

    @Override
    public String uploadImage(MultipartFile file) throws IOException {
        imageService.uploadImage(file);
        return null;
    }
}
