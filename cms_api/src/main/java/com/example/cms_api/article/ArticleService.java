package com.example.cms_api.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public void create(Article article) {
        articleRepository.save(article);
    }

    public List<Article> retrieveAll() {
        return articleRepository.findAll();
    }

    public Article retrieveById(Long id) {
        return articleRepository.findById(id).orElseThrow();
    }

    @Transactional
    public void update(Long id, Article updatedArticle) {
        Article article = articleRepository.findById(id).orElseThrow();
        article.setAllDetails(updatedArticle.getTitle(),
                updatedArticle.getContent(),
                updatedArticle.getImage());
        articleRepository.save(article);
    }

    @Transactional
    public void delete(Long id) {
        articleRepository.deleteById(id);
    }
}
