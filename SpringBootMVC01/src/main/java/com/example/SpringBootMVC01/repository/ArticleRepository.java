package com.example.SpringBootMVC01.repository;

import com.example.SpringBootMVC01.entity.Article;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

//@Repository : 스프링 컨테이너/메모리에 객체 로드된다. -> Controller에서 객체 사용!(DI, Autowired)
//인터페이스는 인터페이스끼리 상속 가능.
@Repository                                     //<관리 대상 엔티티, 대표값(@ID)의 타입>
public interface ArticleRepository extends CrudRepository<Article, Long> {

    //인터페이스지만, 스프링부트가 내부적으로 구현
    //CRUD 메서드 모두 상속 받았다 !! -> 사용 가능 !!


//    @Override
//    Iterable<Article> findAll();
    @Override
    ArrayList<Article> findAll(); //ArrayList -> List -> Collection -> Iterable
}
