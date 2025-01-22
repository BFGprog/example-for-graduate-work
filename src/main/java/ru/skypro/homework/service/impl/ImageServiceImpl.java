package ru.skypro.homework.service.impl;
/**
 * Сервис для управления изображениями объявлений.
 * <p>
 * Этот класс предоставляет методы для сохранения, получения, обновления и удаления изображений,
 * связанных с объявлениями.
 * </p>
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.service.ImageService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {
    private static final Logger logger = LoggerFactory.getLogger(ImageService.class);
    private final ImageRepository imageRepository;
    private final Path path; // Директория для сохранения изображений

    public ImageServiceImpl(ImageRepository imageRepository,
                            @Value("${application.image-dir-name}") String avatarsDirName) {
        this.imageRepository = imageRepository;
        this.path = Path.of(avatarsDirName); // Инициализация пути к директории
    }


    @Override
    public void saveImage(MultipartFile image, Ad ads) throws IOException {
        Image imageForDb = createImageEntity(image, ads);
        Path getFilePathFromDb = Paths.get(imageForDb.getFilePath());
        Files.write(getFilePathFromDb, image.getBytes());
        imageRepository.save(imageForDb);
        logger.info("Изображение сохранено в объявление: {}", ads.getPk());
    }

    private Image createImageEntity(MultipartFile image, Ad ads) {
        String extension = StringUtils.getFilenameExtension(image.getOriginalFilename());
        Path avatarsPath = path.resolve(UUID.randomUUID() + "." + extension);

        Image imageForDb = new Image();
        imageForDb.setAd(ads);
        imageForDb.setPathForEndpoint("/image/images/");
        imageForDb.setFilePath(avatarsPath.toString());
        imageForDb.setMediaType(image.getContentType());
        return imageForDb;
    }
}