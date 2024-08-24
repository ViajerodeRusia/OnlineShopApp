package ru.skypro.homework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.skypro.homework.controller.dto.AdDto;
import ru.skypro.homework.db.entity.Ad;
import ru.skypro.homework.db.repository.AdRepository;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.service.AdService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервис для управления объявлениями.
 */
@Service
public class AdServiceImpl implements AdService {

    private final AdRepository adRepository;
    private final AdMapper adMapper;

    @Autowired
    public AdServiceImpl(AdRepository adRepository, AdMapper adMapper) {
        this.adRepository = adRepository;
        this.adMapper = adMapper;
    }

    /**
     * Метод создает новый объект объявления.
     *
     * @param adDto             Объект объявления для создания.
     * @return AdDto            Созданный объект объявления.
     */
    @Override
    @PreAuthorize("hasRole('USER')")
    public AdDto createAd(AdDto adDto) {
         return new AdDto();
//        Ad ad = adMapper.toEntity(adDto);
//        Ad savedAd = adRepository.save(ad);
//        return adMapper.toDto(savedAd);
    }

    /**
     * Метод ищет объявление по его идентификатору.
     *
     * @param id         Идентификатор объявления для поиска.
     *
     * @return AdDto     Найденный объект объявления
     */
    @Override
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public AdDto getAdById(Long id) {
        return adRepository.findById(id)
                .map(adMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Ad not found"));
    }

    /**
     * Метод возвращает все объявления.
     *
     * @return List<AdDto>     Список всех объявлений
     */
    @Override
    public List<AdDto> getAllAds() {
        return adRepository.findAll().stream()
                .map(adMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Метод обновляет объявление пользователя по его идентификатору.
     *
     * @param id         Идентификатор объявления для обновления.
     * @param adDto      Объект для обновления
     *
     * @return AdDto     Обновленный объект объявления
     */
    @Override
    @PreAuthorize("hasRole('ADMIN') or @userServiceImpl.hasPermission(@adServiceImpl.getAdById(#id))")
    public AdDto updateAd(Long id, AdDto adDto) {
        Ad ad = adRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ad not found"));
        adMapper.updateEntityFromDto(adDto, ad);
        Ad updatedAd = adRepository.save(ad);
        return adMapper.toDto(updatedAd);
    }

    /**
     * Метод удаляет объявление пользователя по его идентификатору.
     *
     * @param id         Идентификатор объявления для удаления.
     */
    @Override
    @PreAuthorize("hasRole('ADMIN') or @userServiceImpl.hasPermission(@adServiceImpl.getAdById(#id))")
    public void deleteAd(Long id) {
        adRepository.deleteById(id);
    }
}
