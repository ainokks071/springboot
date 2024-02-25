package com.example.SpringBootMVC01.service;

import com.example.SpringBootMVC01.dto.ArticleDTO;
import com.example.SpringBootMVC01.entity.Article;
import com.example.SpringBootMVC01.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

// 서비스 계층의 업무 처리는 주로 트랜잭션 단위로 구성
// 트랜잭션 : 모두 성공되어야하는 일련의 과정
// 롤백 : 트랜잭션 단위에서 처리 실패할 경우, 처음으로 되돌아감
@Slf4j
@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

//  1. 게시물 전체 목록 조회기능
    public List<Article> getList() {
        List<Article> articleList = articleRepository.findAll();
        log.info("articleList : " + articleList);
        return articleList;
    }

//  2. 특정 게시물 조회기능
    public Article getArticle(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

//  3. 게시물 등록기능
    public Article create(ArticleDTO articleDTO) {
//      POST요청인데, 기존에 DB에 존재하던 Article 수정되는 것 방지 !
//      JSON { "id" : 1, ... } 이런식으로 id까지 입력된 경우 -> post요청이더라도, 생성이 아니라 기존 데이터 수정되어버린다!
        if(articleDTO.getId() != null) {
            return null; // 에러 처리
        }
        return articleRepository.save(articleDTO.toEntity());
    }

//  4. 특정 게시물 수정기능
    public Article update(Long id, ArticleDTO articleDTO) {
        // 1. 클라이언트가 요청한, 수정용 Entity 생성하기.
        Article article = articleDTO.toEntity();
        // log.info("id : {}, article : {} ", id, article.toString());
        // 2. DB에서 대상 Entity(수정할 게시물) 가져오기
        Article target = articleRepository.findById(id).orElse(null);
//      3. 잘못된 요청 처리
//      - 대상이 없는 경우 : ex) localhost:8080/api/articles/999999
//      - id가 다른 경우 : ex) localhost:8080/api/articles/1, {"id" : 5, "title" : "가가가가"} 이런 식..
//      - id를 입력하지 않은 경우 : ex) localhost:8080/api/articles/1, {"title" : "가가가가"} 이런 식..
        if(target == null || id != article.getId()) {
            return null;
        }
//      4. patch하기 : title이나 content만 작성하여 json으로 요청한 경우 -> null값 되는 것 방지.
        target.patch(article);

//      5. 옳게 요청한 경우 -> 수정하기.
        return articleRepository.save(target);
    }

//  5. 특정 게시물 삭제기능
    public Article delete(Long id) {
        // 삭제할 대상 찾기
        Article target = articleRepository.findById(id).orElse(null);
        // 잘못된 요청 처리 : 삭제할 대상이 없으면,
        if(target == null) {
            return null;
        }
        // 삭제
        articleRepository.delete(target);
        return target;
    }



    //여러 게시물 한번에 등록기능
    //해당 메서드를 트랜잭션으로 묶는다 : 일련의 과정이 모두 성공해야 성공! 실패하면(예외 발생 시) 롤백 처리! -> 즉, 실패하면 save 안됨!
    //롤백 : 이 메서드가 수행되기 이전 상태로.
    @Transactional // 트랜잭션 : 주로 Service계층에서 사용.
    public List<Article> createArticles(List<ArticleDTO> dtos) {
        // 나중에, stream문법 다시보기 !

        // 1. List<ArticleDTO> -> List<Article>
        List<Article> articleList = new ArrayList<>();
        for(int i = 0; i < dtos.size(); i++) {
            articleList.add(dtos.get(i).toEntity());
        }

        // 2. Article Entity DB에 save !
        for(int i = 0; i < articleList.size(); i++) {
            articleRepository.save(articleList.get(i));
        }

        // 강제로 예외 발생(트랜잭션 실패) -> 일련의 과정이 완벽하게 수행되지 못함 -> @Transactional에 의해 자동으로 Rollback된다.
        // 즉, 위의 과정이 모두 취소되고, 메서드 실행 이전 상태로 돌아간다.
        articleRepository.findById(-1L).orElseThrow(
                () -> new IllegalArgumentException("등록 실패 !")
        );

        return articleList;
    }

}
