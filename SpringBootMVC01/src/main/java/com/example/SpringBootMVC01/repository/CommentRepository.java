package com.example.SpringBootMVC01.repository;

import com.example.SpringBootMVC01.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

//  JPA, @Query, 메서드의 파라미터와 쿼리변수 연결  " :articleId "

//    방법1. @Query 어노테이션
//    방법2. orm.xml

//  특정 게시물에 달린 모든 댓글 조회, :articleId = Long articleId 일치시킬 것 !
    @Query(value = "SELECT * FROM comment WHERE article_id = :articleId", nativeQuery = true)
    List<Comment> findAllByArticleId(@Param("articleId") Long articleId);

//    @Param 붙이지 않으면 에러 발생 !
//    Caused by: java.lang.IllegalStateException: For queries with named parameters you need to provide names for method parameters;
//    Use @Param for query method parameters, or when on Java 8+ use the javac flag -parameters



    //  특정 닉네임의 모든 댓글 조회
//  orm.xml 쿼리 작성 !
    List<Comment> findAllByNickname(@Param("nickname") String nickname);
}
