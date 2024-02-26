package com.example.SpringBootMVC01.dto;

import com.example.SpringBootMVC01.entity.Article;
import com.example.SpringBootMVC01.entity.Comment;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class CommentDTO {

    private Long id;

    private String body;

    private String nickname;

//    @JsonProperty("article_Id")
    private Long articleId;

    public static CommentDTO toDTO(Comment comment) {
        return new CommentDTO(comment.getId(), comment.getBody(), comment.getNickname(), comment.getArticle().getId());
    }

}
