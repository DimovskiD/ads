package com.finki.ads.service.impl;

import com.finki.ads.model.Ad;
import com.finki.ads.persistence.AdRepository.AdRepositoryImpl;
import com.finki.ads.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Component
public class AdServiceImpl implements AdService {

    @Autowired
    AdRepositoryImpl repository;

    @Override
    public List<Ad> getAllAds() {
        return repository.findAll();
    }

    @Override
    public Ad getAdById(int id) {
        return repository.findAdById(id);
    }

    @Override
    public Ad updateAd(int id, Ad ad) {
        return repository.update(id,ad);
    }

    @Override
    public void delete(Ad ad) {
        repository.delete(ad);
    }

    @Override
    public List<Ad> getAdsByImportance(int importance) {
        return repository.findAdsByImportance(importance);
    }

    @Override
    public Ad saveAd(Ad ad) {
        return repository.save(ad);
    }

}
