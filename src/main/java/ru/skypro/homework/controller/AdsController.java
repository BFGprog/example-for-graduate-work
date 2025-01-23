package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ExtendedAdDto;
import ru.skypro.homework.service.AdsService;

import java.io.IOException;


@CrossOrigin(value = "http://localhost:3000")
@RestController
@Slf4j
@RequestMapping("/ads")
@Tag(name = "Объявления")
public class AdsController {

    private final AdsService adsService;
    private static final Logger logger = LoggerFactory.getLogger(AdsController.class);
    private MultipartFile image;
    private CreateOrUpdateAdDto ad;

    public AdsController(AdsService adsService) {
        this.adsService = adsService;
    }

    @GetMapping
    @Operation(summary = "Получение всех объявлений")
    public Ads getAllAds() {
        return adsService.getAllAds();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Добавление объявления")
    public void addAd(@RequestParam("image") MultipartFile image,
                      @RequestPart("properties") CreateOrUpdateAdDto ad) throws IOException {
        log.info("---- addAd {}", ad);
        adsService.addAds(image, ad);
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Получение объявлений авторизованного пользователя")
    public Ads getUserAds(Authentication authentication) {
        return adsService.getUserAds(authentication);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN') or @adsServiceImpl.getAdById(#id).user.email.equals(authentication.name)")
    @Operation(summary = "Удаление объявления")
    public void removeAd(@PathVariable Integer id) {
        adsService.removeAd(id);
    }

    @GetMapping("{id}")
    @Operation(summary = "Получение информации об объявлении")
    public ExtendedAdDto getAdById(@PathVariable Integer id) {
        return adsService.getAdById(id);
    }

    @PatchMapping("{id}")
    @PreAuthorize("hasRole('ADMIN') or @adsServiceImpl.getAdById(#id).user.email.equals(authentication.name)")
    @Operation(summary = "Обновление информации об объявлении")
    public void updateAdById(@PathVariable Integer id,
                            @RequestBody CreateOrUpdateAdDto ad,
                            Authentication authentication) {
        adsService.updateAdById(id, ad, authentication);
    }

    @PatchMapping("{id}/image")
    @PreAuthorize("hasRole('ADMIN') or @adsServiceImpl.getAdById(#id).user.email.equals(authentication.name)")
    @Operation(summary = "Обновление картинки объявления")
    public void updateImageAd(@PathVariable Integer id,
                                @RequestParam("image") MultipartFile image,
                                Authentication authentication) throws IOException {
        adsService.updateImageAd(id, image, authentication);
    }
}