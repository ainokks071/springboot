package com.example.SpringBootMVC01.service;

import com.example.SpringBootMVC01.dto.CommentDTO;
import com.example.SpringBootMVC01.entity.Article;
import com.example.SpringBootMVC01.entity.Comment;
import com.example.SpringBootMVC01.repository.ArticleRepository;
import com.example.SpringBootMVC01.repository.CommentRepository;
import org.hibernate.annotations.NaturalId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ArticleRepository articleRepository;

    public List<CommentDTO> getComments(Long articleId) {
//      List<Comment> -> List<CommentDTO>

//        방법 1 : for문
//        - 댓글 목록 조회
//        List<Comment> comments = commentRepository.findAllByArticleId(articleId);
//        - 엔티티 -> DTO로 변환
//        List<CommentDTO> dtos = new ArrayList<CommentDTO>();
//        for(int i = 0; i < comments.size(); i++) {
//            dtos.add(comments.get(i).toDTO());
//        }
//        return dtos

//        방법 2 : stream
        return commentRepository.findAllByArticleId(articleId)
                .stream()
//                comment(id, nickname, body, article)
                .map(comment -> CommentDTO.toDTO(comment))
                .collect(Collectors.toList());
    }

//   2. 댓글 생성 POST, http://localhost:8080/api/articles/{articleId}}/comments + { "body" : "가가가가", "nickname" : KIM, "articleId" : 2 }
    // 500에러
    // 예외발생 1. JSON 데이터 입력 시, comment id 입력한 경우.
    // 예외발생 2. 존재하지 않는 articleId
    // 예외발생 3. url의 articleId != json의 articleId
    @Transactional //중간에 문제 발생하면 자동 rollback
    public CommentDTO create(CommentDTO commentDTO, Long articleId) {

        // 예외발생 1. JSON 데이터 입력 시, comment id 입력한 경우.
        if(commentDTO.getId() != null) {
            throw new IllegalArgumentException("댓글 생성 실패! id는 입력하면 안됩니다.");
        }

        // 예외발생 2. 존재하지 않는 article
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("댓글 생성 실패! 대상 게시글이 없습니다."));

        // 예외발생 3. url의 articleId != json의 articleId
        if(commentDTO.getArticleId() != articleId) {
            throw new IllegalArgumentException("댓글 생성 실패! url의 articleId와 JSON의 articleId가 일치하지 않습니다.");
        }

        // CommentDTO -> Comment 변환 : save 하기 위해 !
        Comment comment = Comment.toEntity(commentDTO, article);
        // DB에 댓글 저장
        Comment saved = commentRepository.save(comment);
        // Comment -> CommentDTO 변환하여 리턴
        return CommentDTO.toDTO(saved);
    }

//  commentId = 1 / CommetnDTO{ "body" : "수정내용", "nickname" : KIM, "articleId" : 1 }
//  3. 댓글 수정 : Patch, http://localhost:8080/api/comments/{commentId} + CommetnDTO{"id" : 1, "body" : "수정내용", "nickname" : KIM, "articleId" : 1 }
    @Transactional
    public CommentDTO update(CommentDTO commentDTO, Long commentId) {

        Comment target = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다.")
        );

        if(commentDTO.getId() != commentId) {
            throw new IllegalArgumentException("url의 id와 json의 id 불일치");
        }
//      target{"id" : 1, "body" : "기존내용", "nickname" : "KIM", "articleId" : 1 }
//      CommetnDTO{"id" : 1, "body" : "수정할 내용", "nickname" : "KIM", "articleId" : 1 }
        target.patch(commentDTO);

        Comment updated = commentRepository.save(target);

//        public static CommentDTO toDTO(Comment comment) {
//            return new CommentDTO(comment.getId(), comment.getBody(), comment.getNickname(), comment.getArticle().getId());
//        }
        return CommentDTO.toDTO(updated);
    }


    @Transactional
    public CommentDTO delete(Long commentId) {

        Comment target = commentRepository.findById(commentId).orElseThrow(
                ()->new IllegalArgumentException("삭제할 댓글이 없습니다.")
        );

        commentRepository.delete(target);

        return CommentDTO.toDTO(target);
    }
}
