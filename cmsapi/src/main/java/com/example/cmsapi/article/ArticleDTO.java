package com.example.cmsapi.article;

import jakarta.validation.constraints.NotEmpty;

public class ArticleDTO {

    @NotEmpty(message = "Title cannot be blank or null")
    private String title;

    @NotEmpty(message = "Content cannot be blank or null")
    private String content;

    @NotEmpty(message = "Image cannot be blank or null")
    private String image;

    public ArticleDTO(String title, String content, String image) {
        this.title = title;
        this.content = content;
        this.image = image;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
