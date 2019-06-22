package com.finki.ads.persistence.AdRepository;

import com.finki.ads.model.Ad;
import com.finki.ads.model.AdType;
import com.finki.ads.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.awt.print.Pageable;
import java.util.List;

public interface AdRepositoryImpl extends JpaRepository<Ad,Long>, AdRepository {

    Ad findAdById(int id);


    @Override
    default Ad update(int id, Ad ad) {
        updateAd(id, ad.getName(), ad.isActive(), ad.getDuration(), ad.getType(), ad.getFileName(), ad.getCategory(), ad.getImportance(), ad.getWhenToShow());
        return findAdById(ad.getId());
    }

    List<Ad> findAdsByImportance(int importance);
    List<Ad> findAdByActive(boolean active);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update Ad a set a.name=:name, a.active = :active, a.duration =:duration, a.type =:adtype, " +
            "a.fileName=:fileName, a.category = :category, a.importance = :importance, a.whenToShow = :whenToShow where a.id=:id")
    int updateAd(@Param("id") int id, @Param("name") String name,
                 @Param("active") boolean active, @Param("duration") int duration,
                 @Param("adtype") AdType type,
                 @Param("fileName") String fileName,
                 @Param("category") Category category,
                 @Param("importance") int importance,
                 @Param("whenToShow") int whenToShow);

    @Query("select a from Ad a where a.type = :adType and a.active = true ORDER BY function('RAND')")
    List<Ad> selectRandomAdByType(@Param("adType") AdType adType);

    int deleteAdById(int id);

}
