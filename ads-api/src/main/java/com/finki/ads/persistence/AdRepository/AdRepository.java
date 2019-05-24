package com.finki.ads.persistence.AdRepository;

import com.finki.ads.model.Ad;

public interface AdRepository  {
    Ad update(int id, Ad ad);
}
