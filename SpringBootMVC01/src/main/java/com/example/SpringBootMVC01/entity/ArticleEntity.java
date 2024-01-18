package com.example.SpringBootMVC01.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity //DB가 엔티티를 인식 가능 !!
public class ArticleEntity {


    @Id //PK, 대표값
    @GeneratedValue //자동 증가 : 1, 2, 3, ...
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    //생성자
    public ArticleEntity(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    @Override
    public String toString() {
        return "ArticleEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
