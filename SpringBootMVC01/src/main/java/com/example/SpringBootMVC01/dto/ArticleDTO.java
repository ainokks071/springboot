package com.example.SpringBootMVC01.dto;

import com.example.SpringBootMVC01.entity.ArticleEntity;

public class ArticleDTO {

    private String title;
    private String content;

    public ArticleDTO(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public ArticleEntity toEntity() {
        return new ArticleEntity(null, title, content);
    }

    @Override
    public String toString() {
        return "ArticleDTO{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

}
