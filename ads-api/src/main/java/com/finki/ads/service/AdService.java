package com.finki.ads.service;

import com.finki.ads.model.Ad;

import java.util.List;

public interface AdService {

    List<Ad> getAllAds();

    Ad getAdById(int id);

    Ad updateAd(int id, Ad ad);

    Ad saveAd(Ad ad);

    void delete(Ad ad);

    List<Ad> getAdsByImportance(int importance);
}
