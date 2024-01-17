package com.study.jpa.chap03_pagination.repository;

import com.study.jpa.chap02_querymethod.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class StudentPageRepositoryTest {

    @Autowired
    StudentPageRepository studentPageRepository;

    @BeforeEach
    void bulkInsert() {
        // 학생 147명을 저장
        for (int i = 1; i <= 147; i++) {
            Student s = Student.builder()
                    .name("김딱팍" + i)
                    .city("도시시" + i)
                    .major("전공공공" + i)
                    .build();

            studentPageRepository.save(s);
        }
    }


    @Test
    @DisplayName("기본적인 페이징 테스트")
    void basicPageTest() {
        //given
        int pageNo = 1;
        int amount = 10;

        // 페이지 정보 객체 생성 (Pageable)
        // 여기서는 페이지 번호가 zero-based임 : 1페이지는 0이다
        Pageable pageInfo = PageRequest.of(
                pageNo - 1,
                amount,
                // Sort.by("name").descending()  // 엔터티 필드명을 써야함

                Sort.by(
                        Sort.Order.desc("name"),
                        Sort.Order.asc("city")
                )
        );

        //when
        Page<Student> studentPage = studentPageRepository.findAll(pageInfo);

        // 실질적인 조회데이터 꺼내기
        List<Student> studentList = studentPage.getContent();

        // 총 페이지 수
        int totalPages = studentPage.getTotalPages();

        // 총 학생 수
        long count = studentPage.getTotalElements();

        //then
        System.out.println("\n\n\n");
        studentList.forEach(System.out::println);
        System.out.println("\n\n\n");
        System.out.println("totalPages = " + totalPages);
        System.out.println("count = " + count);

    }

    @Test
    @DisplayName("이름검색 + 페이징")
    void testSearchAndPagination() {
        //given
        int pageNo = 1;
        int size = 10;
        Pageable pageInfo = PageRequest.of(pageNo - 1, size);
        //when
        Page<Student> students
                = studentPageRepository.findByNameContaining("3", pageInfo);

        //then
        System.out.println("\n\n\n");
        students.getContent().forEach(System.out::println);
        System.out.println("\n\n\n");
    }


}