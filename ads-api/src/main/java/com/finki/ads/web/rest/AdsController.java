package com.finki.ads.web.rest;

import com.finki.ads.model.Ad;
import com.finki.ads.model.AdBuilder;
import com.finki.ads.model.AdType;
import com.finki.ads.model.exceptions.AdNotFoundException;
import com.finki.ads.model.exceptions.NegativeImportanceException;
import com.finki.ads.model.exceptions.TypeNotFoundException;
import com.finki.ads.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/ads", produces = MediaType.APPLICATION_JSON)
public class AdsController {

    private final AdService adService;

    @Autowired
    public AdsController(AdService adService) {
        this.adService = adService;
    }


    @GetMapping
    public List<Ad> getAds() {
        return adService.getAllAds();
    }

    @GetMapping("/{id}")
    public Ad getAdById(@PathVariable("id") int id) {
        return adService.getAdById(id);
    }

    @GetMapping("/importance/{importance}")
    public List<Ad> getAdsByImportance(@PathVariable("importance") int importance)  {
        return adService.getAdsByImportance(importance);
    }

    @GetMapping("/type/{adType}")
    public Ad getRandomAdByType(@PathVariable("adType") String adType) {
        return adService.selectRandomAdByType(adType);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity addNew(@RequestBody Ad ad, HttpServletResponse response)  {
        Ad a = new AdBuilder()
            .setName(ad.getName())
            .setActive(ad.isActive())
            .setCategory(ad.getCategory())
            .setDuration(ad.getDuration())
            .setFileName(ad.getFileName())
            .setImportance(ad.getImportance())
            .setType(ad.getType())
                .build();
        adService.saveAd(a);
        response.setHeader("Location","/students/" + a.getId());
        return new ResponseEntity(a, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public int delete(@PathVariable int id) {
        Ad a = adService.getAdById(id);
        return adService.delete(a);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/{id}")
    public ResponseEntity patch(@PathVariable int id, @RequestBody Ad ad) {

        Ad a = new AdBuilder()
                .setId(id)
                .setName(ad.getName())
                .setActive(ad.isActive())
                .setCategory(ad.getCategory())
                .setDuration(ad.getDuration())
                .setFileName(ad.getFileName())
                .setImportance(ad.getImportance())
                .setType(ad.getType())
                .build();
        adService.updateAd(id,ad);
        return new ResponseEntity(a, HttpStatus.CREATED);
    }

    @ExceptionHandler(AdNotFoundException.class)
    public ResponseEntity<?> handleAdNotFound(AdNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
    @ExceptionHandler(NegativeImportanceException.class)
    public ResponseEntity<?> handleNegativeImportance(NegativeImportanceException exc) {
        return ResponseEntity.badRequest().body(exc.getMessage());
    }
    @ExceptionHandler(TypeNotFoundException.class)
    public ResponseEntity<?> handleAdNotFound(TypeNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }



}
