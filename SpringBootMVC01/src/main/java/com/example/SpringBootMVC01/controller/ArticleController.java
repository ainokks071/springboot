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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller //@Controller : 스프링 컨테이너/메모리에 객체 로드된다.
@Slf4j //롬복 -> 로깅 log.info() 사용 : System.out.println() -> log.info(문자열!)
public class ArticleController {

//    controller에서 repository사용 : controller는 repository에 의존성이 있다.
//    의존성 주입(DI) : 스프링부트가 미리 만들어 놓은 객체를 가져다가 자동 연결! new연산자 없이, 객체 접근 가능 !!
    @Autowired
    private ArticleRepository articleRepository;

//    localhost:8080/articles/form 요청
    @GetMapping("articles/form")
    public String form() {
        return "articles/form";
    } // articles/form.mustache로 포워딩

//    게시글 등록 기능
    @PostMapping("articles/create")
    public String create(ArticleDTO articleDTO) {
//        log.info(articleDTO.toString()); //가가가가, 나나나나
//      ** 파라미터 자동 수집
//      DTO가 form데이터 받는다(@AllArgsConstructor 생성자로 초기화) : new ArticleDTO(가가가가, 나나나나);

//      ** JPA
//      1. form데이터로 초기화된 DTO -> Entity로 변환 : JPA에서, Entity는 DB가 인식 가능한 객체(=> 테이블!!)
        Article article = articleDTO.toEntity();
//        log.info(article.toString()); //null, 가가가가, 나나나나

//      2. Repository : DB에 Entity를 '저장, 조회, 수정, 삭제'하게 해주는 일꾼 객체
//        ArticleRepository는 CrudRepository를 상속받는다.(모든 메서드 사용 가능)
//        Entity save(Entity entity) : 매개변수, 반환타입 확인해볼 것.
        Article savedArticle = articleRepository.save(article);
//        log.info(savedArticle.toString()); //id값 자동으로 넣어준다. -> 1, 가가가가, 나나나나

//        게시글 등록 직후, 게시글 상세페이지로 이동
       return "redirect:/articles/" + savedArticle.getId();
    }

//    특정 게시물 조회 기능
//    @PathVariable : get방식으로 url로 넘어온 전달값 받기
    @GetMapping("articles/{id}")
    public String show(@PathVariable Long id, Model model) {
//        log.info("id : " + id); //문자열로 만들어라.

//      Optional<T> findById(ID id);
//      Optional의 메서드 : public T orElse(T other) {
//      Optional<Article> article = articleRepository.findById(id); -> articleEntity에 기본생성자 있어야함!!!
//      Article article = articleRepository.findById(id).orElse(null); -> articleEntity에 기본생성자 있어야함!!!

//      아래의 두 줄을 한 줄로 :  Article article = articleRepository.findById(id).orElse(new Article(1L,"null","null"));
        Optional<Article> optionalArticle = articleRepository.findById(id);
        Article article = optionalArticle.orElse(new Article(1L,"null","null"));

//        log.info(article.toString());
        model.addAttribute("article", article);
        return "articles/content"; // content.mustache : 페이지 응답 != Data 응답
    }

    @GetMapping("articles/list")
    public String getList(Model model) {

//      방법1 : Iterable<Article> articles = articleRepository.findAll();
//      방법2 : List<Article> articles = (List<Article>)articleRepository.findAll();

//      방법3 : 메서드 오버라이딩 -> findAll()가 ArrayList<Article>를 반환하도록 !
        List<Article> articles = articleRepository.findAll();
        model.addAttribute("articles", articles);

        return "articles/list";

    }


    @GetMapping("articles/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Article article = articleRepository.findById(id).orElse(new Article(1L, "null", "null"));
        model.addAttribute(article);
        return "articles/edit";
    }


    @PostMapping("articles/edit")
    public String edit(ArticleDTO articleDTO) {

//      ArticleDTO의 id필드 추가 !
        Article article = articleDTO.toEntity();

//        Hibernate 절차 따라하기

//      1.  기존의 게시물 조회 : select쿼리
        Article target = articleRepository.findById(article.getId()).orElse(null);

//      2. null이 아니면, 수정 : update쿼리
        if(target != null) {
            articleRepository.save(article);
        }

        return "redirect:/articles/" + article.getId();
    }

    @GetMapping("articles/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes rattr) {

//        1. 삭제할 대상 조회, 없으면 null 반환
        Article target = articleRepository.findById(id).orElse(null);

//        2. 대상 존재하면, 삭제하기
        if(target != null) {
            articleRepository.delete(target);
            rattr.addFlashAttribute("msg", "삭제가 완료되었습니다.");
        }

//        3. 목록으로 리다이렉트
        return "redirect:/articles/list";
    }
}
