package com.finki.ads.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;
public interface StorageService {

    void init();

    void store(MultipartFile file);

    void storeImageFile(MultipartFile file);

    void storeVideoFile(MultipartFile file);

    Stream<Path> loadAll();

    Path load(String filename);

    Resource loadAsResource(String filename);

    String getPathForFilename(String filename);


    void deleteAll();

}