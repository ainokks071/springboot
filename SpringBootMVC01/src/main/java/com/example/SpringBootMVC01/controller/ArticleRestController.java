package com.example.SpringBootMVC01.controller;

import com.example.SpringBootMVC01.dto.ArticleDTO;
import com.example.SpringBootMVC01.entity.Article;
import com.example.SpringBootMVC01.repository.ArticleRepository;
import com.example.SpringBootMVC01.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// ** RestController : REST API용 컨트롤러, View페이지가 아닌, 데이터(JSON)를 반환!
// - 일반 컨트롤러 + @ResponseBody(생략 가능 !), @RequestBody(클라이언트의 요청 시, JSON 데이터 포함)는 생략 불가능
// - 객체를 ResponseEntity로 감싸서 반환 -> 클라이언트가 예상하는 HttpStatusCode(상태코드) 설정 가능
@RestController
@Slf4j
public class ArticleRestController {

    // DI : 의존성 주입
    @Autowired
    private ArticleService articleService;

    // 1. 모든 게시물 조회 : GET, http://localhost:8080/api/articles
    @GetMapping("api/articles")
    public List<Article> getList() {
        return articleService.getList();
    //  일반 컨트롤러와의 차이 : 반환타입이 View페이지가 아닌, 객체,데이터(json) 반환 !
    }

    // 2. 특정 게시물 조회 : GET
    @GetMapping("api/articles/{id}")
    public Article getArticle(@PathVariable(name = "id") Long id) {
        return articleService.getArticle(id);
    }

//    3. 게시물 등록 : POST
//    html form이 아닌, json데이터 전송 -> @RequestBody에 JSON데이터 실려서 전송된다 -> @RequestBody 어노테이션 필수 !
    @PostMapping("api/articles")
    public ResponseEntity<Article> create(@RequestBody ArticleDTO articleDTO) {
        Article created = articleService.create(articleDTO);
//      @ResponseBody : article객체를 JSON형태로 담아 반환
        return (created != null) ?
                ResponseEntity.status(HttpStatus.OK).body(created) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

//  4. 게시물 수정 : PATCH
//    에러 상황분석
//    1) 애초에 없는 자원 요청 : ex) localhost:8080/api/articles/999999
//    2) 요청 url은 존재하는 자원이지만, 요청json 데이터와 id 불일치
//     ex) url :localhost:8080/api/articles/1
//         json : id:2, title:"aa", content:"bb"


//    ResponseEntity : 적절한 상태코드 반환을 위한 객체
//    3) PATCH는 부분 수정 가능해야한다.
//    요청 json에서 content만 수정 요청한 경우 -> title은 null이 되어버리는 문제 ! -> patch메서드 작성 !
    @PatchMapping("api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable(name = "id") Long id,
                                          @RequestBody ArticleDTO articleDTO) {

        Article updated = articleService.update(id, articleDTO);

        return (updated != null) ?
                ResponseEntity.status(HttpStatus.OK).body(updated) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable(name = "id") Long id) {

        Article deleted = articleService.delete(id);

        // ResponseEntity를 사용함으로써, 상태코드의 종류와 응답 바디에 담을 것들을 커스터마이징할 수 있다!
        return (deleted != null) ?
                ResponseEntity.status(HttpStatus.OK).body(deleted) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("api/transaction-test")
    public ResponseEntity<List<Article>> transactionTest(@RequestBody List<ArticleDTO> dtos) {

        List<Article> articleList = articleService.createArticles(dtos);

        return (articleList != null) ?
                ResponseEntity.status(HttpStatus.OK).body(articleList) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
