package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ExtendedAdDto;
import ru.skypro.homework.exception.AdNotFoundException;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.security.UserAuthenticationService;
import ru.skypro.homework.service.AdsService;
import ru.skypro.homework.service.ImageService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdsServiceImpl implements AdsService {

    private final AdRepository adRepository;
    private final UserRepository userRepository;
    private final AdMapper adMapper;
    private final ImageService imageService;
    private final ImageRepository imagesRepository;
    private final UserAuthenticationService userAuthenticationService;


    @Override
    public Ads getAllAds() {
        log.info("Получение всех объявлений");
        List<Ad> ads = adRepository.findAll();
        return new Ads(ads.size(), ads.stream()
                .map(this::mapToAdDto)
                .collect(Collectors.toList()));
    }

    @Override
    public Ads addAd(MultipartFile image, CreateOrUpdateAdDto adDto) throws IOException {
        if (adDto == null) {
            throw new IllegalArgumentException("Данные объявления или аутентификации не могут быть null");
        }
        log.info("Добавление нового объявления");
        User user = userAuthenticationService.getCurrentUserFromDB();
        Ad ad=adMapper.dtoToAd(adDto);
        ad.setUser(user);
        ad.setImage("Картинка");
        Ad adsFromDb = adRepository.save(ad);

        imageService.saveImage(image, ad);
        AdDto adDtoFromDb = adMapper.adToDto(adsFromDb,image);


        return new Ads(1, List.of(mapToAdDto(ad)));
    }

    @Override
    public Ads getUserAds(Authentication authentication) {
        log.info("Получение объявлений авторизованного пользователя");
        List<Ad> userAds = adRepository.findByUserEmail(authentication.getName());
        return new Ads(userAds.size(), userAds.stream()
                .map(this::mapToAdDto)
                .collect(Collectors.toList()));
    }
    /**
     * Удаляет объявление по его идентификатору.
     *
     * @param id идентификатор объявления для удаления.
     * @throws IOException если произошла ошибка при удалении изображения.
     */
    @Override
    public void removeAd(Integer id, Authentication authentication) {
        log.info("Удаление объявления с ID: {}", id);
        Ad ad = adRepository.findById(id)
                .orElseThrow(() -> new AdNotFoundException("Объявление не найдено"));
        if (!ad.getUser().getEmail().equals(authentication.getName())) {
            throw new RuntimeException("Нет прав на удаление объявления");
        }
        adRepository.deleteById(id);
    }

    @Override
    public ExtendedAdDto getAdById(Integer id) {
        log.info("Получение объявления с ID: {}", id);
        Ad ad = adRepository.findById(id)
                .orElseThrow(() -> new AdNotFoundException("Объявление не найдено"));
        return mapToExtendedAdDto(ad);
    }

    @Override
    public Ads updateAdById(Integer id, CreateOrUpdateAdDto ad, Authentication authentication) {
        log.info("Обновление объявления с ID: {}", id);
        Ad existingAd = adRepository.findById(id)
                .orElseThrow(() -> new AdNotFoundException("Объявление не найдено"));
        if (!existingAd.getUser().getEmail().equals(authentication.getName())) {
            throw new RuntimeException("Нет прав на обновление объявления");
        }
        existingAd.setTitle(ad.getTitle());
        existingAd.setPrice(ad.getPrice());
        adRepository.save(existingAd);
        return new Ads(1, List.of(mapToAdDto(existingAd)));
    }

    @Override
    public String updateImageAd(Integer id, MultipartFile image, Authentication authentication) {
        log.info("Обновление картинки объявления с ID: {}", id);
        Ad existingAd = adRepository.findById(id)
                .orElseThrow(() -> new AdNotFoundException("Объявление не найдено"));
        if (!existingAd.getUser().getEmail().equals(authentication.getName())) {
            throw new RuntimeException("Нет прав на обновление картинки");
        }
        existingAd.setImage(saveImage(image));
        adRepository.save(existingAd);
        return "Картинка обновлена";
    }

    private Ads.AdDto mapToAdDto(Ad ad) {
        return new Ads.AdDto(
                ad.getPk(),
                ad.getTitle(),
                ad.getPrice(),
                ad.getImage(),
                ad.getUser().getId()
        );
    }

    private ExtendedAdDto mapToExtendedAdDto(Ad ad) {
        if (ad == null) {
            throw new IllegalArgumentException("Объявление не может быть пу");
        }

        User user = ad.getUser();
        if (user == null) {
            throw new IllegalArgumentException("Пользователь в объявлении не может быть null");
        }

        return new ExtendedAdDto(
                ad.getPk(),
                ad.getTitle(),
                ad.getDescription(),
                ad.getPrice(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhone(),
                ad.getImage()
        );
    }

    private String saveImage(MultipartFile image) {
        if (image == null || image.isEmpty()) {
            throw new IllegalArgumentException("Изображение не может быть пустым");
        }
        // Логика сохранения изображения
        return "https://example.com/image.jpg";
    }
}