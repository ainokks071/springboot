package com.example.SpringBootMVC01.repository;

import com.example.SpringBootMVC01.entity.Article;
import com.example.SpringBootMVC01.entity.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//@DataJpaTest -> 리파지터리 테스트 : JPA와 연동한 테스트
//@AutoConfigureTestDatabase 없으면 에러발생!
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Test
    @DisplayName("특정 articleId(1번 게시글)에 달린 모든 댓글 조회")
    void findAllByArticleId() {

        {
    //        예상
            Long articleId = 1L;
            Article article = new Article(1L, "가가가가", "1111");
            Comment a = new Comment(1L, "KIM", "댓글1", article);
            Comment b = new Comment(2L, "PARK", "댓글2", article);
            List<Comment> expected = Arrays.asList(a, b);
    //        실제
            List<Comment> comments = commentRepository.findAllByArticleId(articleId);
    //        비교
            assertEquals(expected.toString(), comments.toString(), "비교");
        }

    }

    @Test
    @DisplayName("특정 nickname(KIM)이 작성한 모든 댓글 조회")
    void findAllByNickname() {
        //        예상
        String nickname = "KIM";

        Article article1 = new Article(1L, "가가가가", "1111");
        Comment a = new Comment(1L, "KIM", "댓글1", article1);

        Article article2 = new Article(2L, "나나나나", "2222");
        Comment b = new Comment(4L, "KIM", "댓글2", article2);

        List<Comment> expected = Arrays.asList(a, b);

        //        실제
        List<Comment> comments = commentRepository.findAllByNickname(nickname);


        //        비교
        assertEquals(expected.toString(), comments.toString(), "비교");

    }
}