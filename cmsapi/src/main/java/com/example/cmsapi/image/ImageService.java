package com.example.cmsapi.image;

import com.example.cmsapi.exceptions.ImageExceptions;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public Image retrieve(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new ImageExceptions.ImageNotFoundException("Image not found for ID: " + id));
    }

    public Image retrieveByImagePath(String image_path){
        return imageRepository.findByImagePath(image_path)
                .orElseThrow(() -> new ImageExceptions.ImageNotFoundException("Image not found for path: " + image_path));
    }

    @Transactional
    public void upload(Image image) {
        if (imageRepository.existsByImagePath(image.getImagePath())){
            throw new ImageExceptions.ImageExistsException();
        }
        imageRepository.save(image);
    }
}
