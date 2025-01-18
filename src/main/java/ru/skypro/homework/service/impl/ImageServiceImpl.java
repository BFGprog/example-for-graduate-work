package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.service.ImageService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageServiceImpl  implements ImageService{

    private final ImageRepository imageRepository;
    private final String uploadDir = "uploads/"; // Директория для сохранения изображений

    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public Image uploadImage(MultipartFile file) throws IOException {
        // Создаем директорию, если она не существует
//        Path uploadPath = Paths.get(uploadDir);
//        if (!Files.exists(uploadPath)) {
//            Files.createDirectories(uploadPath);
//        }

        try (InputStream is = file.getInputStream();
             ByteArrayOutputStream baos = new ByteArrayOutputStream();
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(baos, 1024);
        ) {
            bis.transferTo(bos);

            Image saveImage = new Image();
            saveImage.setData(baos.toByteArray());
            saveImage.setMediaType(file.getContentType());

            return imageRepository.save(saveImage);
        }
    }

    @Override
    public Image getImage(Long imageId) throws IOException {
        return imageRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Изображение не найдено"));

//        Path filePath = Paths.get((String) image.getId());
    }

    @Override
    public byte[] getImageData(Long imageId) throws IOException {
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Изображение не найдено"));

//        Path filePath = Paths.get((String) image.getId());
        return image.getData();
    }
}