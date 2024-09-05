package com.example.cmsapi;

import com.example.cmsapi.article.Article;
import com.example.cmsapi.article.ArticleRepository;
import com.example.cmsapi.image.Image;
import com.example.cmsapi.image.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitialSetup implements CommandLineRunner {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public void run(String... args) throws Exception {

        // Check if database is already initialized
        if (articleRepository.count() != 0){
            return;
        }

        // Create image entities
        Image image1 = new Image("https://example.com/image1.jpg");
        Image image2 = new Image("https://example.com/image2.jpg");
        Image image3 = new Image("https://example.com/image3.jpg");

        // Create article entities
        Article article1 = new Article(
                "Introduction to Spring Boot",
                "Spring Boot makes it easy to create stand-alone, production-grade Spring-based Applications.",
                image1
        );

        Article article2 = new Article(
                "REST API with Spring Boot",
                "Learn how to create a REST API using Spring Boot.",
                image2
        );

        Article article3 = new Article(
                "Deploying Spring Boot Applications",
                "This article covers the best practices for deploying Spring Boot applications.",
                image3
        );


        // Save images
        imageRepository.save(image1);
        imageRepository.save(image2);
        imageRepository.save(image3);

        // Save articles
        articleRepository.save(article1);
        articleRepository.save(article2);
        articleRepository.save(article3);
    };
}
