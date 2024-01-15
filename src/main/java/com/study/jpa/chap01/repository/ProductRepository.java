package com.study.jpa.chap01.repository;

import com.study.jpa.chap01.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository를 상속받고 제네릭은 첫번째는 엔터티클래스, 두번째는 PK타입
public interface ProductRepository extends JpaRepository<Product, Long> {

}