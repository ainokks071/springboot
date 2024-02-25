package com.example.SpringBootMVC01.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nickname;

    @Column
    private String body;

//    OneToMany가 아니라 ManyToOne을 사용하는 이유 ?
//    객체 - 테이블의 세계
//    "참조키(article_id)는 항상 Many에 존재한다."

    @ManyToOne //Many : Comment, One : Article
    @JoinColumn(name = "article_id") // PK : article_id
    private Article article;
}
