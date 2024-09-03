package com.example.cms_api.images;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @GetMapping("/home")
    public String homeImage(){
        return "Hello Image";
    }

    @PostMapping
    public void uploadImage(@RequestBody Image image){
        imageService.upload(image);
    }

    @GetMapping("/{id}")
    public Image retrieveImage(@PathVariable Long id){
        return imageService.retrieve(id);
    }
}
