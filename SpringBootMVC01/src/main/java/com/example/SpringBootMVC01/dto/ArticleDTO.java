package com.example.SpringBootMVC01.dto;

import com.example.SpringBootMVC01.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Data
public class ArticleDTO {
    private Long id;
    private String title;
    private String content;


//    DTO를 Entity로 변환해주는 메서드
//    게시글 작성 시, id = null !
    public Article toEntity() {
        return new Article(this.id, this.title, this.content);
    }







//    생성자
//    @AllArgsConstructor
//    public ArticleDTO(Long id, String title, String content) {
//        this.id = id;
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
