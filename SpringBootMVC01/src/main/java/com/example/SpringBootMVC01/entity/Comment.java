package com.example.SpringBootMVC01.entity;

import com.example.SpringBootMVC01.dto.CommentDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Comment {

    // 댓글 일련번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 댓글 작성자
    @Column
    private String nickname;

    // 댓글 내용
    @Column
    private String body;

    // OneToMany가 아닌 ManyToOne을 사용하는 이유 ? 객체-테이블의 세계 -> "참조키(article_id)는 항상 Many에 존재한다."
    // Many : Comment, One : Article // FK : article_id
    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    public static Comment toEntity(CommentDTO commentDTO, Article article) {
        return new Comment(null, commentDTO.getNickname(), commentDTO.getBody(), article);
    }
//      target{"id" : 1, "body" : "기존내용", "nickname" : "KIM", "articleId" : 1 }
//      CommetnDTO{"id" : 1, "body" : "수정할 내용", "nickname" : "KIM", "articleId" : 1 }

    public void patch(CommentDTO commentDTO) {

        if(commentDTO.getBody() != null) {
            this.body = commentDTO.getBody();
        }

        if(commentDTO.getNickname() != null) {
            this.nickname = commentDTO.getNickname();
        }
    }
}
