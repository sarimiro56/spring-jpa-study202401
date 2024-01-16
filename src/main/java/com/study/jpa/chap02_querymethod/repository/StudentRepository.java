package com.study.jpa.chap02_querymethod.repository;

import com.study.jpa.chap02_querymethod.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, String> {

    // 쿼리메서드: 메서드 이름에 특별한 규칙을 적용하면 SQL이 규칙에 맞게 자동생성
    List<Student> findByName(String name);
    List<Student> findByCity(String city);


    List<Student> findByMajorContaining(String major); //  % ? %
    List<Student> findByMajorStartingWith(String major); //  ? %
    List<Student> findByMajorEndingWith(String major); //   % ?


    //   WHERE city = ? AND major = ?
    List<Student> findByCityAndMajor(String city, String major);


    // findByAgeLessThanEqual(int age)  =>   WHERE age <= ?

    // native sql 사용하기
    @Query(value = "SELECT * FROM tbl_student WHERE stu_name = :nm", nativeQuery = true)
    Student findByNameWithSQL(@Param("nm") String name);


    // JPQL
    // select 별칭 from 엔터티클래스명 as 별칭
    // where 별칭.필드명=?

    // native-sql: SELECT * FROM tbl_student
    //             WHERE stu_name = ?

    // jpql:  SELECT st FROM Student AS st
    //        WHERE st.name = ?


    // 도시명으로 학생을 조회
    @Query("SELECT s FROM Student s WHERE s.city = ?1")
//    @Query("SELECT s FROM Student s WHERE s.city = :cc")
    Student getByCityWithJPQL(String city);


    // 이름으로 학생 검색하기
    @Query("SELECT stu FROM Student stu WHERE stu.name LIKE %:nm%")
    List<Student> searchByNameWithJPQL(@Param("nm") String name);


    // JPQL로 삽입, 수정, 삭제 쿼리 쓰는법
    @Modifying   // SELECT가 아닌경우 무조건 붙이기
    @Query("DELETE FROM Student s WHERE s.name = ?1 AND s.city = ?2")
    void deleteByNameWithJPQL(String name, String city);

}