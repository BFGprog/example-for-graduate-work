package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ExtendedAdDto;
import ru.skypro.homework.service.AdsService;

@RestController
@RequestMapping("/ads")
@Tag(name = "Обьявления")
public class AdsController {
    @Autowired
    private AdsService adsService;

    @GetMapping
    @Operation(summary = "Получение всех объявлений")
    public Ads getAllAds() {
        return adsService.getAllAds();

    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Добавление объявления")
    public Ads addAd(@RequestParam("image") MultipartFile image,
                     @RequestBody CreateOrUpdateAdDto ad,
                     Authentication authentication) {
        return adsService.addAds(image, ad, authentication);
    }

    @GetMapping("{id}")
    @Operation(summary = "Получение информации об объявлении")
    public ExtendedAdDto getAdById(@PathVariable Integer id) {
        return adsService.getAdById(id);

    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN') or @adServiceImpl.findAdById(id).author.email.equals(authentication.name)")
    @Operation(summary = "Удаление объявления")
    public String removeAd(@PathVariable Integer id) {
        adsService.removeAd(id);
        return "Объявление удалено";

    }

    @PatchMapping("{id}")
    @PreAuthorize("hasRole('ADMIN') or adServiceImpl.findAdById(id).author.email.equals(authentication.name)")
    @Operation(summary = "Обновление информации об объявлении")
    public String updateAdById(@PathVariable Integer id,
                               @RequestBody CreateOrUpdateAdDto ad) {
        adsService.updateAdById(id, ad);
        return "Объявление обновлено";

    }
    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Получение объявлений авторизованного пользователя")
    public Ads getUserAds() {
        return adsService.getUserAds();

    }
    @PatchMapping("{id}/image")
    @PreAuthorize("hasRole('ADMIN') or adServiceImpl.findAdById(id).author.email.equals(authentication.name)")
    @Operation(summary = "Обновление картинки объявления")
    public String updateImageAd(@PathVariable Integer id,
                               @RequestBody CreateOrUpdateAdDto ad) {
        adsService.updateImageAd(id, ad);
        return "Картинка обновлена";

    }

}
