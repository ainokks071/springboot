package com.example.SpringBootMVC01.controller;

import com.example.SpringBootMVC01.dto.ArticleDTO;
import com.example.SpringBootMVC01.entity.ArticleEntity;
import com.example.SpringBootMVC01.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {

    //DI : 스프링부트가 미리 만들어 놓은 객체를 가져다가 자동 연결! new 필요 x
    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping("articles/new")
    public String insert() {
        return "articles/new";
    }

    @PostMapping("articles/new")//파라메터 자동 수집
    public String insert(ArticleDTO articleDTO, Model model) {

        //articleDTO는 form데이터에 의해 초기화 완료 !
        System.out.println(articleDTO);
        //model.addAttribute("","");

        //*** JPA
        //1. DTO -> Entity로 변환 : Entity는 DB가 인식 가능한 객체
        ArticleEntity articleEntity = articleDTO.toEntity();
        System.out.println(articleEntity);

        //2. Repository : DB에 Entity를 저장하게 하는 객체, 일꾼
        ArticleEntity savedArticle = articleRepository.save(articleEntity); //id값 자동으로 넣어주네.
        System.out.println(articleEntity);

        return "";
    }

}
