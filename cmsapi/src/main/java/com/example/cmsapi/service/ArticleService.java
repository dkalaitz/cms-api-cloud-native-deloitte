package com.example.cmsapi.service;

import com.example.cmsapi.model.Article;
import com.example.cmsapi.dto.ArticleDTO;
import com.example.cmsapi.repository.ArticleRepository;
import com.example.cmsapi.exception.ArticleExceptions;
import com.example.cmsapi.model.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ImageService imageService;

    // Retrieve All Articles
    public List<Article> retrieveAll() {
        return articleRepository.findAll();
    }

    // Retrieve article based on ID
    public Article retrieveById(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new ArticleExceptions.ArticleNotFoundException("Article not found for ID: " + id));
    }

    // Create an Article
    @Transactional
    public void create(ArticleDTO articleDTO) {

        // Retrieve the image based on the image path from the DTO
        Image articleImage = imageService.retrieveByImagePath(articleDTO.getImage());

        // Save the new article to the repository
        articleRepository.save(new Article(articleDTO.getTitle(),
                articleDTO.getContent(),
                articleImage));
    }

    // Update an existing article by its ID
    @Transactional
    public void update(Long id, ArticleDTO updatedArticle) {

        // Retrieve the article by ID or throw an exception if not found
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleExceptions.ArticleNotFoundException("Article not found for ID: " + id));

        // Retrieve the updated image based on the image path from the DTO
        Image updatedImage = imageService.retrieveByImagePath(updatedArticle.getImage());

        // Update the article's details with the new data
        article.setAllDetails(updatedArticle.getTitle(),
                updatedArticle.getContent(),
                updatedImage);

        // Save the updated article to the repository
        articleRepository.save(article);
    }

    // Delete article based on ID
    @Transactional
    public void delete(Long id) {

        // Check if article already exists
        if (!articleRepository.existsById(id)){
            throw new ArticleExceptions.ArticleNotFoundException("Article not found for ID: " + id);
        }

        // Delete Article from the repository
        articleRepository.deleteById(id);
    }
}
