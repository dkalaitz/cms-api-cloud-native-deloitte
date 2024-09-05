package com.example.cms_api.image;

import com.example.cms_api.exceptions.ImageExceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public void upload(Image image) {
        if (imageRepository.existsByImagePath(image.getImagePath())){
            throw new ImageExceptions.ImageExistsException();
        }
        imageRepository.save(image);
    }

    public Image retrieve(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new ImageExceptions.ImageNotFoundException("Image not found for ID: " + id));
    }

    public Image retrieveByImagePath(String image_path){
        return imageRepository.findByImagePath(image_path)
                .orElseThrow(() -> new ImageExceptions.ImageNotFoundException("Image not found for path: " + image_path));
    }
}
