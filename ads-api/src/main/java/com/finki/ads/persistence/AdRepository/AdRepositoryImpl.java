package com.finki.ads.persistence.AdRepository;

import com.finki.ads.model.Ad;
import com.finki.ads.model.AdType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface AdRepositoryImpl extends JpaRepository<Ad,Long>, AdRepository {

    Ad findAdById(int id);


    @Override
    default Ad update(int id, Ad ad) {
        updateAd(id, ad.getName(), ad.isActive(), ad.getDuration(), ad.getType(), ad.getFileName(), ad.getCategory(), ad.getImportance());
        return findAdById(ad.getId());
    }

    List<Ad> findAdsByImportance(int importance);
    List<Ad> findAdByActive(boolean active);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update Ad a set a.name=:name, a.active = :active, a.duration =:duration, a.type =:adtype, " +
            "a.fileName=:fileName, a.category = :category, a.importance = :importance where a.id=:id")
    int updateAd(@Param("id") int id, @Param("name") String name,
                 @Param("active") boolean active, @Param("duration") int duration,
                 @Param("adtype") AdType type,
                 @Param("fileName") String fileName,
                 @Param("category") short category,
                 @Param("importance") int importance);

    int deleteAdById(int id);

}
