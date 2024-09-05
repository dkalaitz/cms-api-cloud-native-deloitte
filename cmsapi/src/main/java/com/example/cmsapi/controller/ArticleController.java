package com.example.cmsapi.controller;

import com.example.cmsapi.model.Article;
import com.example.cmsapi.dto.ArticleDTO;
import com.example.cmsapi.service.ArticleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    // Retrieve all articles
    @GetMapping
    public ResponseEntity<List<Article>> retrieveAllArticles() {
        // Call the service to get all articles and return with HTTP 200 OK status
        return ResponseEntity.ok(articleService.retrieveAll());
    }

    // Retrieve a specific article by its ID
    @GetMapping("/{id}")
    public ResponseEntity<?> retrieveArticleById(@PathVariable Long id) {
        // Call the service to get the article by ID and return with HTTP 200 OK status
        return ResponseEntity.ok(articleService.retrieveById(id));
    }

    // Create a new article
    @PostMapping
    public ResponseEntity<?> createArticle(@Valid @RequestBody ArticleDTO articleDTO) {

        // Call the service to create a new article
        articleService.create(articleDTO);

        // Return a message with HTTP 201 Created status
        return new ResponseEntity<>("Article successfully created.", HttpStatus.CREATED);
    }

    // Update an existing article
    @PutMapping("/{id}")
    public ResponseEntity<?> updateArticle(@PathVariable Long id, @Valid @RequestBody ArticleDTO updatedArticleDetails) {

        // Call the service to update the article with the given ID
        articleService.update(id, updatedArticleDetails);

        // Return a message with HTTP 200 OK status
        return new ResponseEntity<>("Article successfully updated.", HttpStatus.OK);
    }

    // Delete an article by its ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteArticle(@PathVariable Long id) {

        // Call the service to delete the article with the given ID
        articleService.delete(id);

        // Return a message with HTTP 200 OK status
        return new ResponseEntity<>("Successfully deleted article.", HttpStatus.OK);
    }
}