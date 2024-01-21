package com.example.SpringBootMVC01.controller;

import com.example.SpringBootMVC01.dto.ArticleDTO;
import com.example.SpringBootMVC01.entity.Article;
import com.example.SpringBootMVC01.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Controller //@Controller : 스프링 컨테이너/메모리에 객체 로드된다.
@Slf4j //로깅 log.info() 사용 : System.out.println() -> log.info(문자열!)
public class ArticleController {

//    DI : 스프링부트가 미리 만들어 놓은 객체를 가져다가 자동 연결! new연산자 없이, 객체 접근 가능 !!
    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping("articles/new")
    public String insert() {
        return "articles/new";
    } // articles/new.mustache로 포워딩

//    게시글 등록 기능
    @PostMapping("articles/create")
    public String insert(ArticleDTO articleDTO, Model model) {
//        파라메터 자동 수집 : DTO가 form데이터 받는다 !!
//        articleDTO = new ArticleDTO("title", "content");

//        articleDTO는 form데이터에 의해 초기화 완료 !
        log.info(articleDTO.toString());
//        System.out.println(articleDTO);

//        *** JPA
//        1. DTO -> Entity로 변환 : Entity는 DB가 인식 가능한 객체(= 테이블!!)
        Article article = articleDTO.toEntity();
        log.info(article.toString());
//        System.out.println(article);

//        2. Repository : DB에 Entity를 '저장, 조회, 수정, 삭제'하게 해주는 일꾼 객체
        Article savedArticle = articleRepository.save(article); //id값 자동으로 넣어주네.
        log.info(savedArticle.toString());
//        System.out.println(savedArticle);

//        redirect 할 것.
        return "redirect:/articles/" + savedArticle.getId();
    }

//    특정 게시물 조회 기능
//    @PathVariable : get방식으로 url로 넘어온 전달값 받기
//    Model : 객체 바인딩용 객체
    @GetMapping("articles/{id}")
    public String show(@PathVariable Long id, Model model) {
        log.info("id : " + id); //문자열로 만들어라.

//      Optional<Article> article = articleRepository.findById(id); //-> articleEntity에 기본생성자 있어야함!!!
//        Article article = articleRepository.findById(id).orElse(null); // -> articleEntity에 기본생성자 있어야함!!!
        Article article = articleRepository.findById(id).orElse(new Article(1L,"null","null"));

        log.info(article.toString());

        model.addAttribute("article", article);

        return "articles/show";
    }

    @GetMapping("articles/list")
    public String getList(Model model) {

        List<Article> articles = articleRepository.findAll();

        model.addAttribute("articles", articles);

        return "articles/list";
    }


}
