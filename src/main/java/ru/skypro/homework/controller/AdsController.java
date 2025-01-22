package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ExtendedAdDto;
import ru.skypro.homework.service.AdsService;

import java.io.IOException;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
@Tag(name = "Объявления")
public class AdsController {

    private final AdsService adsService;
    private static final Logger logger = LoggerFactory.getLogger(AdsController.class);
    public AdsController(AdsService adsService)
    {
        this.adsService = adsService;
    }

    @GetMapping
    @Operation(summary = "Получение всех объявлений")
    public Ads getAllAds() {

        Ads ads=adsService.getAllAds();
            logger.info("Получены все обьявления");
        return ads;
    }
    /**
     * Добавление нового объявления.
     *
     * @param ad    Данные объявления для добавления.
     * @param image Изображение объявления.
     * @return Объект AdDto, представляющий добавленное объявление.
     * @throws IOException Если произошла ошибка при обработке изображения.
     */

    @PostMapping(consumes = "multipart/form-data")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Добавление объявления")
    public Ads addAd(@RequestParam("image") MultipartFile image,
                     @RequestPart("properties") CreateOrUpdateAdDto ad) throws IOException {
            logger.info("Добавление объявления");
            Ads addAd=adsService.addAd(image, ad);
            logger.info("Обьявление успешно добавлено");

        return addAd;
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
    public void removeAd(@PathVariable Integer id, Authentication authentication) {
        adsService.removeAd(id, authentication);
    }

    @GetMapping("{id}")
    @Operation(summary = "Получение информации об объявлении")
    public ExtendedAdDto getAdById(@PathVariable Integer id) {
        logger.info("Получение информации по объявлению: ",id);
        ExtendedAdDto extendedAdDto= adsService.getAdById(id);
        logger.info("Получена информация по объявлению: ",id);
        return extendedAdDto;

    }

    @PatchMapping("{id}")
    @PreAuthorize("hasRole('ADMIN') or @adsServiceImpl.getAdById(#id).user.email.equals(authentication.name)")
    @Operation(summary = "Обновление информации об объявлении")
    public Ads updateAdById(@PathVariable Integer id,
                            @RequestBody CreateOrUpdateAdDto ad,
                            Authentication authentication) {
        logger.info("Обновление информации по объявлению: ",id);
        Ads createOrUpdateAdDto= adsService.updateAdById(id, ad, authentication);
        logger.info("Обновлена информация по объявлению: ",id);
        return adsService.updateAdById(id, ad, authentication);
    }

    @PatchMapping("{id}/image")
    @PreAuthorize("hasRole('ADMIN') or @adsServiceImpl.getAdById(#id).user.email.equals(authentication.name)")
    @Operation(summary = "Обновление картинки объявления")
    public String updateImageAd(@PathVariable Integer id,
                                @RequestParam("image") MultipartFile image,
                                Authentication authentication) {
        return adsService.updateImageAd(id, image, authentication);
    }
}