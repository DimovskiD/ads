package com.finki.ads.service;

import com.finki.ads.model.Ad;
import com.finki.ads.model.AdType;

import java.util.List;

public interface AdService {

    List<Ad> getAllAds();

    Ad getAdById(int id);

    Ad updateAd(int id, Ad ad);

    Ad saveAd(Ad ad);

    int delete(Ad ad);

    List<Ad> getAdsByImportance(int importance);

    List<Ad> getActiveAds(boolean areActive);

    Ad selectRandomAdByType(String adType);


}
