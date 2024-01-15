package com.study.jpa.chap01.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter @Getter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "tbl_product")
public class Product {

    // 엔터티 클래스는 DB 테이블의 컬럼과 1대1로 매칭되는 필드를 선언
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prod_id")
    private long id;  // PK

    @Column(name = "prod_nm", length = 30, nullable = false)
    private String name; // 상품명

    @Builder.Default
    private int price = 1000; // 상품가격

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category; // 상품 카테고리

    @CreationTimestamp  // INSERT시 현재 로컬서버시간 자동저장
    @Column(updatable = false)  // 수정불가
    private LocalDateTime createdDate; // 상품등록시간

    @UpdateTimestamp  // UPDATE문 실행시 자동으로 수정시간 변경
    private LocalDateTime updatedDate; // 상품수정시간

    @Transient // DB컬럼은 생성되지 않습니다.
    private String nickName;



    public enum Category {
        FOOD, FASHION, ELECTRONIC
    }
}