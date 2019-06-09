package com.finki.ads.service.impl;

import com.finki.ads.model.Ad;
import com.finki.ads.model.AdType;
import com.finki.ads.model.exceptions.AdNotFoundException;
import com.finki.ads.model.exceptions.NegativeImportanceException;
import com.finki.ads.model.exceptions.TypeNotFoundException;
import com.finki.ads.persistence.AdRepository.AdRepositoryImpl;
import com.finki.ads.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
        if (repository.findAdById(id)==null) throw new AdNotFoundException("Ad not found");
        return repository.findAdById(id);
    }

    @Override
    public Ad updateAd(int id, Ad ad) {
        if (repository.findAdById(ad.getId())==null) throw new AdNotFoundException("Ad not found");

        return repository.update(id,ad);
    }

    @Override
    @Transactional
    public int delete(Ad ad) {
        if (repository.findAdById(ad.getId())==null) throw new AdNotFoundException("Ad not found");
        return repository.deleteAdById(ad.getId());
    }

    @Override
    public List<Ad> getAdsByImportance(int importance) {

        if (importance<0) throw new NegativeImportanceException("Importance must be a positive number");
        return repository.findAdsByImportance(importance);
    }

    @Override
    public List<Ad> getActiveAds(boolean areActive) {
        return repository.findAdByActive(areActive);
    }

    @Override
    public Ad selectRandomAdByType(String adType) {
        if (repository.findAll().size() <1 ) {
            throw new AdNotFoundException("No ads found");
        }
        AdType a = null;
        for (AdType value: AdType.values()
                ) {
            if (value.ordinal() == Integer.parseInt(adType)) {
                a = value;
                break;
            }
        }
        if (a == null) throw new TypeNotFoundException("Ad Type not found");
        else if (repository.selectRandomAdByType(a).size() < 1) throw new AdNotFoundException("No ads of type " + a.toString() + " found");
        return repository.selectRandomAdByType(a).get(0);
    }

    @Override
    public Ad saveAd(Ad ad) {
        return repository.save(ad);
    }

}
