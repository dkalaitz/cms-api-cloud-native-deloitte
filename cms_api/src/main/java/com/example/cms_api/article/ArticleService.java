package com.example.cms_api.article;

import com.example.cms_api.exceptions.ArticleExceptions;
import com.example.cms_api.image.Image;
import com.example.cms_api.image.ImageService;
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
    public Article create(ArticleDTO articleDTO) {
        Image articleImage = imageService.retrieveByImagePath(articleDTO.getImage());
        return articleRepository.save(new Article(articleDTO.getTitle(),
                articleDTO.getContent(),
                articleImage));
    }

    @Transactional
    public Article update(Long id, ArticleDTO updatedArticle) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleExceptions.ArticleNotFoundException("Article not found for ID: " + id));
        Image updatedImage = imageService.retrieveByImagePath(updatedArticle.getImage());
        article.setAllDetails(updatedArticle.getTitle(),
                updatedArticle.getContent(),
                updatedImage);
        return articleRepository.save(article);
    }

    @Transactional
    public void delete(Long id) {
        if (!articleRepository.existsById(id)){
            throw new ArticleExceptions.ArticleNotFoundException("Article not found for ID: " + id);
        }
        articleRepository.deleteById(id);
    }
}
