package ru.skypro.homework.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.service.ImageService;

import java.io.IOException;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@Slf4j
@RequestMapping("/images")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

//    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<Long> uploadImage(@RequestParam MultipartFile file) throws IOException {
//        log.info("ImageController uploadImage");
//        Image image = imageService.uploadImage(file);
//        return ResponseEntity.ok(image.getId());
//    }

    @GetMapping("/{imageId}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long imageId) throws IOException {
        Image image = imageService.getImage(imageId);
//        byte[] imageBytes = imageService.getImageData(imageId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(image.getMediaType()));
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(image.getData());
//        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image.getData());
//        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}