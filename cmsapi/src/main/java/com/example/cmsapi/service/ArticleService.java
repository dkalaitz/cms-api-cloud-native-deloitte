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

    public List<Article> retrieveAll() {
        return articleRepository.findAll();
    }

    public Article retrieveById(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new ArticleExceptions.ArticleNotFoundException("Article not found for ID: " + id));
    }

    @Transactional
    public void create(ArticleDTO articleDTO) {
        Image articleImage = imageService.retrieveByImagePath(articleDTO.getImage());
        articleRepository.save(new Article(articleDTO.getTitle(),
                articleDTO.getContent(),
                articleImage));
    }

    @Transactional
    public void update(Long id, ArticleDTO updatedArticle) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleExceptions.ArticleNotFoundException("Article not found for ID: " + id));
        Image updatedImage = imageService.retrieveByImagePath(updatedArticle.getImage());
        article.setAllDetails(updatedArticle.getTitle(),
                updatedArticle.getContent(),
                updatedImage);
        articleRepository.save(article);
    }

    @Transactional
    public void delete(Long id) {
        if (!articleRepository.existsById(id)){
            throw new ArticleExceptions.ArticleNotFoundException("Article not found for ID: " + id);
        }
        articleRepository.deleteById(id);
    }
}
