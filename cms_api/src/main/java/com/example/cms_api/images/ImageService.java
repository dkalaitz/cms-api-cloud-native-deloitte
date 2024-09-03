package com.example.cms_api.images;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public void upload(Image image) {
        imageRepository.save(image);
    }

    public Image retrieve(Long id) {
        return imageRepository.findById(id).orElseThrow();
    }
}
