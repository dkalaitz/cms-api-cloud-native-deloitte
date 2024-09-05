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

    @GetMapping
    public ResponseEntity<List<Article>> retrieveAllArticles(){
        return ResponseEntity.ok(articleService.retrieveAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> retrieveArticleById(@PathVariable Long id){
        return ResponseEntity.ok(articleService.retrieveById(id));
    }

    @PostMapping
    public ResponseEntity<?> createArticle(@Valid @RequestBody ArticleDTO articleDTO) {
        articleService.create(articleDTO);
        // Return a ResponseEntity with HTTP status 201 Created
        return new ResponseEntity<>("Article successfully created.",HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateArticle(@PathVariable Long id, @Valid @RequestBody ArticleDTO updatedArticleDetails){
        articleService.update(id, updatedArticleDetails);
        return new ResponseEntity<>("Article successfully updated.", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteArticle(@PathVariable Long id){
        articleService.delete(id);
        return new ResponseEntity<>("Successfully deleted article.", HttpStatus.OK);
    }
}
