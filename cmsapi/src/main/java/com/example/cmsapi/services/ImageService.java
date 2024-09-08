package com.example.cmsapi.services;

import com.example.cmsapi.errors.exceptions.ImageExceptions;
import com.example.cmsapi.model.Image;
import com.example.cmsapi.repositories.ImageRepository;
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
                .orElseThrow(() -> new ImageExceptions.ImageNotFoundException("The image with the specified ID does not exist."));
    }

    // Retrieve an image based on image path
    public Image retrieveByImagePath(String image_path){
        return imageRepository.findByImagePath(image_path)
                .orElseThrow(() -> new ImageExceptions.ImageNotFoundException("The image with the specified path does not exist."));
    }

    // Upload an image
    @Transactional
    public Image upload(Image image) {

        // Check if an image with the same image path exists
        if (imageRepository.existsByImagePath(image.getImagePath())){
            throw new ImageExceptions.ImageExistsException("Image already exists.");
        }

        // Save the image to the repository and return it
        return imageRepository.save(image);
    }
}
