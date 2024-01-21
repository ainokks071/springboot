package com.example.SpringBootMVC01.dto;

import com.example.SpringBootMVC01.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Data
public class ArticleDTO {

    private String title;
    private String content;


    public Article toEntity() {
        return new Article(null, title, content);
    }








//    @AllArgsConstructor
//    public ArticleDTO(String title, String content) {
//        this.title = title;
//        this.content = content;
//    }

//    @ToString
//    @Override
//    public String toString() {
//        return "ArticleDTO{" +
//                "title='" + title + '\'' +
//                ", content='" + content + '\'' +
//                '}';
//    }

}
