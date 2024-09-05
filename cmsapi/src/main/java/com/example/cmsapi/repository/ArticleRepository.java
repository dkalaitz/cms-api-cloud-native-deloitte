package com.example.cmsapi.repository;

import com.example.cmsapi.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
