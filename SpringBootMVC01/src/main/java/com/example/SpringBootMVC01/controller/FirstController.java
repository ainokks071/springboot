package com.example.SpringBootMVC01.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstController {

    //localhost:8080/hello
    @GetMapping("/hello")
    public String hello(Model model) {
        //model : 객체바인딩용 객체
        //form데이터를 받는 객체 : dto
        model.addAttribute("username", "kks");
        return "hello"; //hello.mustache
    }

    @GetMapping("/bye")
    public String bye(Model model) {
        model.addAttribute("username", "kks");
        return "bye"; //bye.mustache
    }
}
