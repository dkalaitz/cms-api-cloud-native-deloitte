package com.example.cmsapi.service;

import com.example.cmsapi.exception.ImageExceptions;
import com.example.cmsapi.model.Image;
import com.example.cmsapi.repository.ImageRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    // Retrieve an image based on ID
    public Image retrieve(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new ImageExceptions.ImageNotFoundException("Image not found for ID: " + id));
    }

    // Retrieve an image based on image path
    public Image retrieveByImagePath(String image_path){
        return imageRepository.findByImagePath(image_path)
                .orElseThrow(() -> new ImageExceptions.ImageNotFoundException("Image not found for path: " + image_path));
    }

    // Upload an image
    @Transactional
    public void upload(Image image) {

        // Check if an image with the same image path exists
        if (imageRepository.existsByImagePath(image.getImagePath())){
            throw new ImageExceptions.ImageExistsException("Image already exists.");
        }

        // Save the image to the repository
        imageRepository.save(image);
    }
}
