package com.example.SpringBootMVC01.controller;

import com.example.SpringBootMVC01.dto.CommentDTO;
import com.example.SpringBootMVC01.entity.Comment;
import com.example.SpringBootMVC01.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentRestController {

    @Autowired
    private CommentService commentService;

//    1. 특정 게시글(articleId)에 달린, 모든 댓글 목록 조회

//    ex) articleId = 1번인 게시글에 달린 모든 댓글 목록 : JOIN

//    Comment로 응답한다면?
//    Comment [{"id": 1, "nickname": "KIM", "body": "댓글1", "article":{"id": 1, "title": "가가가가", "content": "1111"}},
//             {"id": 2, "nickname": "PARK", "body": "댓글2", "article":{"id": 1, "title": "가가가가", "content": "1111"}}]

//    CommentDTO로 응답한다면?
//    CommentDTO [{"id": 1, "nickname": "KIM", "body": "댓글1", "articleId": 1},
//                {"id": 2, "nickname": "PARK", "body": "댓글2", "articleId": 1}

//  GET방식
//  http://localhost:8080/api/articles/1/comments : 1번 게시글에 달린 모든 댓글 조회.
    @GetMapping("api/articles/{articleId}/comments")
    public ResponseEntity<List<CommentDTO>> getComments(@PathVariable(name = "articleId") Long articleId) {
        List<CommentDTO> comments = commentService.getComments(articleId);
        return (!comments.isEmpty()) ?
                ResponseEntity.status(HttpStatus.OK).body(comments) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(comments);
    }



//    2. 댓글 생성 POST : ex) 2번 게시글에 댓글 작성
//    http://localhost:8080/api/articles/{articleId}}/comments + JSON { "body" : "가가가가", "nickname" : KIM, "articleId" : 2 }
//    CommentDTO : id(자동생성), body, nickname, articleId
    @PostMapping("api/articles/{articleId}/comments")
    public ResponseEntity<CommentDTO> create(@RequestBody CommentDTO commentDTO,
                                             @PathVariable(name = "articleId") Long articleId) {
//      서비스에 위임, 절차 : 1. 해당 article 조회, 유무확인. 2. DTO -> Entity 변환, DB에 save. 3. Entity -> DTO로 응답
        CommentDTO created = commentService.create(commentDTO, articleId);

        return ResponseEntity.status(HttpStatus.OK).body(created);
    }


//    3. 댓글 수정 : Patch, http://localhost:8080/api/comments/{commentId} + CommetnDTO{"id" : 1, "body" : "수정내용", "nickname" : KIM, "articleId" : 1 }
    @PatchMapping("api/comments/{commentId}")
    public ResponseEntity<CommentDTO> update(@PathVariable(name = "commentId") Long commentId,
                                             @RequestBody CommentDTO commentDTO) {
//      해당 comment 조회, DTO -> Entity, save, Entity -> DTO
        CommentDTO updated = commentService.update(commentDTO, commentId);

        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }


//    4. 댓글 삭제 http://localhost:8080/api/comments/{commentId}
    @DeleteMapping("api/comments/{commentId}")
    public ResponseEntity<CommentDTO> delete(@PathVariable(name = "commentId") Long commentId) {

        CommentDTO deleted = commentService.delete(commentId);

        return ResponseEntity.status(HttpStatus.OK).body(deleted);
    }
}
