package com.example.cmsapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "articles")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long id;

    @NotEmpty(message = "Title cannot be blank or null")
    private String title;

    @Column(columnDefinition = "TEXT")
    @NotEmpty(message = "Content cannot be blank or null")
    private String content;

    @ManyToOne
    @JoinColumn(name = "image_id")
    private Image image;

    public Article(){};

    public Article(String title, String content, Image image) {
        this.title = title;
        this.content = content;
        this.image = image;
    }

    public void setAllDetails(String title, String content, Image image){
        this.title = title;
        this.content = content;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

}
