package com.example.cmsapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long id;

    @JsonProperty("image")
    @Column(name = "image_path")
    @NotEmpty(message = "Image cannot be blank or null")
    private String imagePath;

    @OneToMany(mappedBy = "image")
    @JsonIgnore
    private List<Article> articles = new ArrayList<>();

    public Image() {
    }

    public Image(String imagePath) {
        this.imagePath = imagePath;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
