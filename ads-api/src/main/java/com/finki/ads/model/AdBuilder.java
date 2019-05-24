package com.finki.ads.model;

public class AdBuilder {

    private Ad ad;

    public AdBuilder(){
        ad = new Ad();
    }

    public AdBuilder setId(int id) {
        ad.setId(id);
        return this;
    }

    public AdBuilder setName (String name) {
        ad.setName(name);
        return this;
    }

    public AdBuilder setActive (boolean active) {
        ad.setActive(active);
        return this;
    }


    public AdBuilder setType (AdType type) {
        ad.setType(type);
        return this;
    }


    public AdBuilder setDuration (int duration) {
        ad.setDuration(duration);
        return this;
    }


    public AdBuilder setFileName (String fileName) {
        ad.setFileName(fileName);
        return this;
    }


    public AdBuilder setCategory (short category) {
        ad.setCategory(category);
        return this;
    }

    public AdBuilder setImportance (int importance) {
        ad.setImportance(importance);
        return this;
    }

    public Ad build() {
        return this.ad;
    }
}
