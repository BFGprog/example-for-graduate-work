package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.model.Image;

import java.io.IOException;

public interface ImageService {

    public Image uploadImage(MultipartFile file) throws IOException;

}
