package com.study.jpa.chap01.repository;

import com.study.jpa.chap01.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static com.study.jpa.chap01.entity.Product.Category.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback  // 테스트 진행 후 롤백
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @BeforeEach  // 각 단위 테스트를 돌리기전에 항상 실행할 코드
    void insertBeforeTest() {
        Product p1 = Product.builder()
                .name("아이폰")
                .category(ELECTRONIC)
                .price(2000000)
                .build();
        Product p2 = Product.builder()
                .name("탕수육")
                .category(FOOD)
                .price(20000)
                .build();
        Product p3 = Product.builder()
                .name("구두")
                .category(FASHION)
                .price(300000)
                .build();
        Product p4 = Product.builder()
                .name("주먹밥")
                .category(FOOD)
                .price(1500)
                .build();

        productRepository.save(p1);
        productRepository.save(p2);
        productRepository.save(p3);
        productRepository.save(p4);
    }


    @Test
    @DisplayName("상품을 데이터베이스에 저장한다")
    void saveTest() {
        //given
        Product product = Product.builder()
                .name("정장")
                .price(120000)
                .category(FASHION)
                .build();
        //when
        Product saved = productRepository.save(product);
        //then
        assertNotNull(saved);
    }


    @Test
    @DisplayName("1번 상품을 삭제한다.")
    void deleteTest() {
        //given
        long id = 1L;
        //when
        productRepository.deleteById(id);

        //then
        Optional<Product> product = productRepository.findById(id);

    }



    @Test
    @DisplayName("3번 상품을 단일조회한다")
    void findOneTest() {
        //given
        long id = 3L;
        //when
        Optional<Product> product = productRepository.findById(id);
        //then
        System.out.println("product = " + product);

        // null체크를 간소화하기 위한 Optional타입
        // ifPresent는 null이 아니면 람다의 코드 진행, null이면 무시
        product.ifPresent(p -> {
            assertEquals("구두", p.getName());
            assertNotNull(p);
        });

        // product가 null이면 새로운 new Product를 반환하고
        // null이 아니면 Optional안에서 꺼내서 반환
        Product ppp = product.orElse(new Product());

        // null이면 예외를 발생, null이 아니면 Optional에서 꺼내서 반환
        Product pppp = product.orElseThrow();


    }


    @Test
    @DisplayName("2번 상품의 이름과 카테고리를 수정한다")
    void modifyTest() {
        //given
        long id = 2L;
        String newName = "짜장면";
        Product.Category newCategory = FOOD;
        //when
        /*
            jpa에서는 수정메서드를 따로 제공하지 않습니다.
            단일 조회를 수행한 후 setter를 호출해서 값을 변경하고
            다시 save를 하면 UPDATE문이 나갑니다.
         */
        Product product = productRepository.findById(id).get();

        product.setName(newName);
        product.setCategory(newCategory);

        productRepository.save(product);

        //then
    }



    @Test
    @DisplayName("상품을 전체조회하면 상품의 총 개수가 4개여야 한다")
    void findAllTest() {
        //given

        //when
        List<Product> productList = productRepository.findAll();
        //then
        System.out.println("\n\n\n");
        productList.forEach(System.out::println);
        System.out.println("\n\n\n");

        assertEquals(4, productList.size());
    }


}