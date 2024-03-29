package com.finki.ads.web.rest;

import java.io.IOException;
import java.util.stream.Collectors;

import com.finki.ads.model.exceptions.InvalidAdFormatException;
import com.finki.ads.model.exceptions.StorageFileNotFoundException;
import com.finki.ads.service.StorageService;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@CrossOrigin(origins = "http://localhost:4200")
public class FileUploadController {

    private final StorageService storageService;

    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/")
    public String listUploadedFiles(Model model) throws IOException {

        model.addAttribute("files", storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                        "serveFile", path.getFileName().toString()).build().toString())
                .collect(Collectors.toList()));

        return "upload_form";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping("/files/name/{filename:.+}")
    @ResponseBody
    public ResponseEntity<String> getPathForFileName(@PathVariable String filename) {

        String file = storageService.getPathForFilename(filename);
        return ResponseEntity.ok().body(file);
    }

    @PostMapping("/files/img")
    public String handleImageUpload(@RequestParam("file") MultipartFile file,
                                    RedirectAttributes redirectAttributes) {

        storageService.storeImageFile(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }

    @PostMapping("/files/video")
    public String handleVideoUpload(@RequestParam("file") MultipartFile file,
                                    RedirectAttributes redirectAttributes) {

        storageService.storeVideoFile(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(InvalidAdFormatException.class)
    public ResponseEntity<?> handleInvalidFileFormat(InvalidAdFormatException exc) {
        return ResponseEntity.badRequest().body(exc.getMessage());
    }

    @ExceptionHandler(FileUploadBase.SizeLimitExceededException.class)
    public ResponseEntity handleMultipartException(FileUploadBase.SizeLimitExceededException exc) {
        return ResponseEntity.badRequest().body("File size limit exceeded.");
    }


}