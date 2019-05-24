package com.finki.ads.web.rest;

import com.finki.ads.model.Ad;
import com.finki.ads.model.AdBuilder;
import com.finki.ads.model.Student;
import com.finki.ads.model.StudentRequest;
import com.finki.ads.persistence.AdRepository.AdRepository;
import com.finki.ads.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.text.ParseException;
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
    public void delete(@PathVariable int id) {
        Ad a = adService.getAdById(id);
        if (a==null)
            new ResponseEntity("\"This ad does not exist", HttpStatus.NOT_FOUND);
        else adService.delete(a);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/{id}")
    public ResponseEntity patchStudent(@PathVariable String id, @RequestBody Ad ad) {
        int iId;
        try {
            iId = Integer.parseInt(id);
        } catch (NumberFormatException a) {
            return new ResponseEntity("\"Invalid id\"", HttpStatus.NOT_FOUND);
        }


        if (adService.getAdById(iId)==null)
            return new ResponseEntity("\"This ad does not exist\"", HttpStatus.NOT_FOUND);
        Ad a = new AdBuilder()
                .setId(iId)
                .setName(ad.getName())
                .setActive(ad.isActive())
                .setCategory(ad.getCategory())
                .setDuration(ad.getDuration())
                .setFileName(ad.getFileName())
                .setImportance(ad.getImportance())
                .setType(ad.getType())
                .build();
        adService.updateAd(iId,ad);
        return new ResponseEntity(a, HttpStatus.CREATED);
    }

}
