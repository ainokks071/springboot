package com.example.SpringBootMVC01.service;

import com.example.SpringBootMVC01.dto.ArticleDTO;
import com.example.SpringBootMVC01.entity.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// 해당 테스트 클래스는 스프링부트와 연동되어 테스트된다.
@SpringBootTest
class ArticleServiceTest {

    @Autowired
    private ArticleService articleService;

    @Test
    void getList() {
//      1. 예상 결과
        Article a = new Article(22L, "가가가가", "1111");
        Article b = new Article(25L, "카카카카", "7777");
        List<Article> expected = Arrays.asList(a, b);
//      2. 실제 코드
        List<Article> articles = articleService.getList();
//      3. 예상과 실제 비교
        assertEquals(articles.toString(), expected.toString());
    }

//    존재하는 id, 내용 모두 일치한 경우
    @Test
    void getArticle_success1() {
//        1. 예상
        Long id = 22L;
        Article expected = new Article(id, "가가가가", "1111");
//        2. 실제
        Article article = articleService.getArticle(id);
//        3. 비교
        assertEquals(expected.toString(), article.toString());
    }

//    존재하지 않는 id 입력한 경우
    @Test
    void getArticle_fail1() {
//        1. 예상
        Long id = 100L;
        Article expected = null;
//        2. 실제
        Article article = articleService.getArticle(id);
//        toString() 사용하면 -> Nullpointer Exception 발생
//        3. 비교
        assertEquals(expected, article);
    }

//    @Test + @Transactional : 테스트 종료 후 롤백 ! but Id는 증가한다..?!
    @Transactional
    @Test
    void create_success1() {
//        1. 예상
        String title = "하하하하";
        String content = "하하하하123";
        ArticleDTO articleDTO = new ArticleDTO(null, title, content);
        Article expected = new Article(43L, title, content);
//        2. 실제
        Article article = articleService.create(articleDTO);
//        3. 비교
        assertEquals(expected.toString(), article.toString());
    }


//    id가 포함된 dto 입력된 경우
    @Transactional
    @Test
    void create_fail1() {
//        1. 예상
        Long id = 28L;
        String title = "하하하하";
        String content = "하하하하123";
        ArticleDTO articleDTO = new ArticleDTO(id, title, content);

        Article expected = null;

//        2. 실제
        Article article = articleService.create(articleDTO); // article == null : DTO에 id 입력되어있으면, null 반환!

//        3. 비교
        assertEquals(expected, article);
    }
}