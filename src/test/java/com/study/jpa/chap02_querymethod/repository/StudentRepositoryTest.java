package com.study.jpa.chap02_querymethod.repository;

import com.study.jpa.chap02_querymethod.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class StudentRepositoryTest {

    @Autowired
    StudentRepository studentRepository;


    @BeforeEach
    void insertData() {
        Student s1 = Student.builder()
                .name("쿠로미")
                .city("청양군")
                .major("경제학")
                .build();

        Student s2 = Student.builder()
                .name("춘식이")
                .city("서울시")
                .major("컴퓨터공학")
                .build();

        Student s3 = Student.builder()
                .name("어피치")
                .city("제주도")
                .major("화학공학")
                .build();

        studentRepository.save(s1);
        studentRepository.save(s2);
        studentRepository.save(s3);
    }


    @Test
    @DisplayName("테스트로 하나 삽입한다")
    void insertTest() {
        //given
        Student student = Student.builder()
                .name("말똥이")
                .city("춘천시")
                .major("한국미술")
                .build();
        //when
        studentRepository.save(student);
        //then
    }



    @Test
    @DisplayName("이름이 춘식이인 학생의 정보를 단일조회한다.")
    void findByNameTest() {
        //given
        String name = "춘식이";
        //when
        List<Student> studentList = studentRepository.findByName(name);
        //then
        assertEquals(1, studentList.size());

        System.out.println("studentList.get(0) = " + studentList.get(0));


    }



    @Test
    @DisplayName("전공에 공학이 포함된 학생정보를 조회한다.")
    void findByContainingTest() {
        //given
        String majorKeyword = "공학";
        //when
        List<Student> studentList = studentRepository.findByMajorContaining(majorKeyword);
        //then
        assertEquals(2, studentList.size());

        System.out.println("\n\n\n\n");
        studentList.forEach(System.out::println);
        System.out.println("\n\n\n\n");
    }



    @Test
    @DisplayName("네이티브 SQL로 이름으로 조회하기")
    void nativeSQLTest() {
        //given
        String name = "쿠로미";
        //when
        Student student = studentRepository.findByNameWithSQL(name);
        //then
        assertNotNull(student);
        assertEquals("청양군", student.getCity());
    }



    @Test
    @DisplayName("JPQL메서드를 사용한 도시이름으로 학생조회")
    void findByCityWithJPQL() {
        //given
        String city = "청양군";
        //when
        Student student = studentRepository.getByCityWithJPQL(city);
        //then
        assertEquals("쿠로미", student.getName());
    }



    @Test
    @DisplayName("JPQL을 이용한 이름으로 검색하기")
    void searchNameTest() {
        //given
        String name = "춘";
        //when
        List<Student> students = studentRepository.searchByNameWithJPQL(name);
        //then
        assertEquals(1, students.size());

        System.out.println("students.get(0) = " + students.get(0));

    }



    @Test
    @DisplayName("JPQL로 삭제하기")
    void deleteWithJPQLTest() {
        //given
        String name = "어피치";
        String city = "제주도";
        //when
        studentRepository.deleteByNameWithJPQL(name, city);

        //then
        List<Student> studentList = studentRepository.findByName(name);
        assertEquals(0, studentList.size());
    }


}