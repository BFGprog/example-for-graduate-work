package ru.skypro.homework.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.service.ImageService;

import java.io.IOException;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/images")
public class ImageController {
    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<Long> uploadImage(@RequestParam MultipartFile file) throws IOException {
        Image image = imageService.uploadImage(file);
        return ResponseEntity.ok(image.getId());
    }

    @GetMapping("/{imageId}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long imageId) throws IOException {
        byte[] imageBytes = imageService.getImage(imageId);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
    }
}