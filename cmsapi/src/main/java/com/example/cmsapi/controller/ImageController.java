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

    // Upload an image
    @PostMapping
    public ResponseEntity<?> uploadImage(@Valid @RequestBody Image image) {

        // Call service method to handle the image upload
        imageService.upload(image);

        // Return a success message with HTTP 200 OK status
        return new ResponseEntity<>("Image successfully uploaded.", HttpStatus.OK);
    }

    // Retrieve an image by its ID
    @GetMapping("/{id}")
    public ResponseEntity<Image> retrieveImage(@PathVariable Long id) {

        // Call service method to get the image by ID
        Image image = imageService.retrieve(id);

        // Return the retrieved image with HTTP 200 OK status
        return new ResponseEntity<>(image, HttpStatus.OK);
    }
}
