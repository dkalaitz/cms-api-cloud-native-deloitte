package com.example.cmsapi.controller;

import com.example.cmsapi.model.Image;
import com.example.cmsapi.service.ImageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping
    public ResponseEntity<?> uploadImage(@Valid @RequestBody Image image){
        imageService.upload(image);
        return new ResponseEntity<>("Image successfully uploaded.", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Image> retrieveImage(@PathVariable Long id){
        return new ResponseEntity<>(imageService.retrieve(id), HttpStatus.OK);
    }
}
