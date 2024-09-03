package com.example.cms_api.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/home")
    public String home(){
        return "Hello World";
    }

    @PostMapping
    public void createArticle(@RequestBody Article article){
        articleService.create(article);
    }

    @GetMapping
    public List<Article> retrieveAllArticles(){
        return articleService.retrieveAll();
    }

    @GetMapping("/{id}")
    public Article retrieveArticleById(@PathVariable Long id){
        return articleService.retrieveById(id);
    }

    @PutMapping("/{id}")
    public void updateArticle(@PathVariable Long id, @RequestBody Article updatedArticle){
        articleService.update(id, updatedArticle);
    }

    @DeleteMapping("/{id}")
    public void deleteArticle(@PathVariable Long id){
        articleService.delete(id);
    }
}
