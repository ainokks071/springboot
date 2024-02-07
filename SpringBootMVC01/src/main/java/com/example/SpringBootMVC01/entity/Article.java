package com.example.SpringBootMVC01.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity //DB가 엔티티 객체(= 테이블)를 인식 가능하게 함 !!
@AllArgsConstructor
@NoArgsConstructor //기본생성자 없으면, findByID()를 통해 조회할 때 에러 -> 내부적으로 article.title = title; 하는 것 같다.(필드주입)
@ToString
@Getter
public class Article {

    //처음에 테이블이 없어도, 서버 실행 시 entity를 보고 "테이블 생성"해준다.

    @Id //PK, 대표값
    @GeneratedValue //자동 증가 : 1, 2, 3, ...
    private Long id;

    @Column
    private String title;

    @Column
    private String content;



    //생성자
//    public Article(Long id, String title, String content) {
//        this.id = id;
//        this.title = title;
//        this.content = content;
//    }

//    @Override
//    public String toString() {
//        return "ArticleEntity{" +
//                "id=" + id +
//                ", title='" + title + '\'' +
//                ", content='" + content + '\'' +
//                '}';
//    }
}
