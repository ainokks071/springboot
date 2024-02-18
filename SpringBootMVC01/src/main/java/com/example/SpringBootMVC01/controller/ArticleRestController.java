package com.example.SpringBootMVC01.controller;

import com.example.SpringBootMVC01.dto.ArticleDTO;
import com.example.SpringBootMVC01.entity.Article;
import com.example.SpringBootMVC01.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// RestController : REST API용 컨트롤러, 페이지가 아닌, 데이터(JSON)를 반환!
@RestController
public class ArticleRestController {

    @Autowired
    private ArticleRepository articleRepository;

    //    1. 모든 게시물 조회 : GET, http://localhost:8080/api/articles
    @GetMapping("api/articles")
    public List<Article> getList() {

        List<Article> list = articleRepository.findAll();

//      일반 컨트롤러와의 차이 : 반환타입이 뷰페이지가 아닌, 객체,데이터(json) !
        return list;
    }

    //    2. 특정 게시물 조회 : GET
    @GetMapping("api/articles/{id}")
    public Article getArticle(@PathVariable(name = "id") Long id) {
        Article article = articleRepository.findById(id).orElse(null);
        return article;
    }

//    3. 게시물 등록 : POST
//    html form이 아닌, json데이터 전송되므로 @RequestBody 어노테이션 필수 !
    @PostMapping("api/articles")
    public Article create(@RequestBody ArticleDTO articleDTO) {

        Article article = articleDTO.toEntity();
        article = articleRepository.save(article);

//      ResponseBody : article객체 담아 반환
        return article;
    }

//  4. 게시물 수정 : PATCH
//    에러 상황분석
//    1) 애초에 없는 자원 요청 -> null
//     ex) localhost:8080/api/articles/999999
//    2) 요청 url은 존재하는 자원이지만, 요청json 데이터와 id 불일치
//     ex) url :localhost:8080/api/articles/1
//         json : id:2, title:"aa", content:"bb"

//    3) PATCH는 부분 수정 가능해야한다.
//    요청 json에서 content만 수정 요청한 경우 -> title은 null이 되어버리는 문제 ! -> patch메서드 작성 !
    @PatchMapping("api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable(name = "id") Long id, @RequestBody ArticleDTO articleDTO) {

//      클라이언트에 의해 수정된 게시물 : id:1, title:ddd, content:sss
        Article article = articleDTO.toEntity();

//      수정할 게시물 가져오기
//      id:1, title:sss, content:ddd
        Article target = articleRepository.findById(id).orElse(null);




//      없다면 ? 일치하지 않는다면 ?
//      잘못된 요청처리 : 400
        if(target == null || id != article.getId()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

//      옳게 요청 -> 수정하기

        target.patch(article);

        Article updated = articleRepository.save(target);
        return ResponseEntity.status(HttpStatus.OK).body(updated);

    }


    @DeleteMapping("api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable(name = "id") Long id) {

//        삭제할 대상 찾기
        Article target = articleRepository.findById(id).orElse(null);

//        잘못된 요청 처리
        if(target == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); //body(null)대신 build() 사용 가능.
        }

//        대상 삭제 
//        target == null이 되면 안된다.
        articleRepository.delete(target);

//        ResponseEntity를 사용함으로써, 상태코드의 종류와 응답 바디에 담을 것들을 커스터마이징할 수 있다!
        return ResponseEntity.status(HttpStatus.OK).body(target);
    }
}
