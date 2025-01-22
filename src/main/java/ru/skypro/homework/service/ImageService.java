package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Image;

import java.io.IOException;

public interface ImageService {
    /**
     * Загружает изображение и сохраняет его в файловой системе.
     *
     * @param image объект MultipartFile, представляющий изображение.
     * @param ads объект Ads, к которому принадлежит изображение..
     * @throws IOException Если произошла ошибка при сохранении файла.
     */
    void saveImage(MultipartFile image, Ad ads) throws IOException;

    /**
     * Получает изображение по его ID.
     *
     * @param imageId ID изображения.
     * @return Бинарные данные изображения.
     * @throws IOException Если произошла ошибка при чтении файла.
     */

}