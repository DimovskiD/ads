package com.finki.ads.persistence.AdRepository;

import com.finki.ads.model.Ad;
import com.finki.ads.model.AdType;

import java.util.List;

public interface AdRepository  {
    Ad update(int id, Ad ad);

    List<Ad> selectRandomAdByType(AdType adType);

}
