package com.example.cmsapi.article;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        Article createdArticle = articleService.create(articleDTO);
        // Return a ResponseEntity with the created article and HTTP status 201 Created
        return new ResponseEntity<>(createdArticle,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateArticle(@PathVariable Long id, @Valid @RequestBody ArticleDTO updatedArticleDetails){
        Article updatedArticle = articleService.update(id, updatedArticleDetails);
        return new ResponseEntity<>(updatedArticle, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteArticle(@PathVariable Long id){
        articleService.delete(id);
        return new ResponseEntity<>("Successfully deleted article.", HttpStatus.OK);
    }
}
