package com.example.SpringBootMVC01.repository;

import com.example.SpringBootMVC01.entity.ArticleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//인터페이스는 인터페이스끼리 상속 가능.
@Repository                                     //<관리 대상 엔티티, 대표값(@ID)의 타입>
public interface ArticleRepository extends CrudRepository<ArticleEntity, Long> {

    //인터페이스지만, 스프링부트가 내부적으로 구현
    //CRUD 메서드 모두 상속 받았다 !! -> 사용 가능 !!

}
