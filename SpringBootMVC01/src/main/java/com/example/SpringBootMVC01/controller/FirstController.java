package com.example.SpringBootMVC01.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstController {

//    MVC의 각 역할, 실행 흐름 !
//    클라이언트의 요청 : localhost:8080/hello -> Controller -> Model 데이터 담아 -> View로 응답
    @GetMapping("/hello")
    public String hello(Model model) {
        //model : 객체바인딩용 객체
        //form데이터를 받는 객체 : dto
        model.addAttribute("username", "김경수");
        return "hello"; //hello.mustache
    }

//    localhost:8080/bye
    @GetMapping("/bye")
    public String bye(Model model) {
        model.addAttribute("username", "kks");
        return "bye"; //bye.mustache
    }
}
