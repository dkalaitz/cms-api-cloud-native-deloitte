package com.example.cmsapi.config;

import com.example.cmsapi.model.Article;
import com.example.cmsapi.repositories.ArticleRepository;
import com.example.cmsapi.model.Image;
import com.example.cmsapi.repositories.ImageRepository;
import com.example.cmsapi.enumeration.Role;
import com.example.cmsapi.repositories.UserRepository;
import com.example.cmsapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DatabaseInitialSetup implements CommandLineRunner {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        // Initialize the database with default data
        initializeDatabase();
    }

    private void initializeDatabase(){
        initializeImages();
        initializeArticles();
        initializeUsers();
    }

    private void initializeImages(){
        // Check if there are no images in the repository
        if (imageRepository.count() == 0){
            // Create and save default image entities
            Image image1 = new Image("https://example.com/image1.jpg");
            Image image2 = new Image("https://example.com/image2.jpg");
            Image image3 = new Image("https://example.com/image3.jpg");

            imageRepository.saveAll(List.of(image1, image2, image3));
        }
    }

    private void initializeArticles(){

        // Check if there are no articles in the repository
        if (articleRepository.count() == 0){

            // Retrieve all images to associate with articles
            List<Image> images = imageRepository.findAll();

            // Check if there are enough images to create articles
            if (images.size() < 3) {
                throw new IllegalStateException("Not enough images to create articles.");
            }

            // Create and save default article entities
            Article article1 = new Article(
                    "Introduction to Spring Boot",
                    "Spring Boot makes it easy to create stand-alone, production-grade Spring-based Applications.",
                    images.getFirst()
            );

            Article article2 = new Article(
                    "REST API with Spring Boot",
                    "Learn how to create a REST API using Spring Boot.",
                    images.get(1)
            );

            Article article3 = new Article(
                    "Deploying Spring Boot Applications",
                    "This article covers the best practices for deploying Spring Boot applications.",
                    images.getLast()
            );

            articleRepository.saveAll(List.of(article1, article2, article3));
        }
    }

    private void initializeUsers(){
        // Check if there are no users in the repository
        if (userRepository.count() == 0){
            // Register default admin and guest users
            userService.registerUser("admin", "password", Role.ADMIN);
            userService.registerUser("guest", "password", Role.GUEST); // Register GUEST user for test reasons
        }
    }

}
