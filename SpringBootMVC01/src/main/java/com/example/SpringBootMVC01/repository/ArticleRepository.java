package com.example.SpringBootMVC01.repository;

import com.example.SpringBootMVC01.entity.Article;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

    //JPA에서 제공하는 CrudRepository Interface
    //@Repository : 스프링 컨테이너/메모리에 객체 로드된다. -> Controller에서 객체 사용!(DI, Autowired)
    //인터페이스는 인터페이스끼리 상속 가능.
@Repository                                     //<관리 대상 엔티티, 그 엔티티의 대표값(@ID)의 타입>
public interface ArticleRepository extends CrudRepository<Article, Long> {
    // 인터페이스지만, 스프링부트가 내부적으로 구현
    // CRUD 메서드 모두 상속받아 사용 가능

    //  Iterable<Article> findAll(); 메서드를 오버라이딩? -> 제네릭스 개념 아닐까? 선언부 다르니까? ArrayList, Iterable은 상속관계니까?
    //  ArrayList -> List -> Collection -> Iterable
    //  게시물 전체 목록 조회기능
    @Override
    ArrayList<Article> findAll();

}