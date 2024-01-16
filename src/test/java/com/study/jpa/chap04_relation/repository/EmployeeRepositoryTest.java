package com.study.jpa.chap04_relation.repository;

import com.study.jpa.chap04_relation.entity.Department;
import com.study.jpa.chap04_relation.entity.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class EmployeeRepositoryTest {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DepartmentRepository departmentRepository;


    // @BeforeEach
    void bulkInsert() {

        Department d1 = Department.builder()
                .name("영업부")
                .build();
        Department d2 = Department.builder()
                .name("개발부")
                .build();

        departmentRepository.save(d1);
        departmentRepository.save(d2);

        Employee e1 = Employee.builder()
                .name("라이옹")
                .department(d1)
                .build();
        Employee e2 = Employee.builder()
                .name("어피치")
                .department(d1)
                .build();
        Employee e3 = Employee.builder()
                .name("프로도")
                .department(d2)
                .build();
        Employee e4 = Employee.builder()
                .name("네오")
                .department(d2)
                .build();

        employeeRepository.save(e1);
        employeeRepository.save(e2);
        employeeRepository.save(e3);
        employeeRepository.save(e4);
    }


    @Test
    @DisplayName("특정 사원의 정보를 조회한다")
    void findOneTest() {
        //given
        Long id = 2L;
        //when
        Employee employee = employeeRepository.findById(id)
                // orElseThrow : find결과가 null일 경우 예외가 발생하도록 예외처리
                .orElseThrow(
                        () -> new RuntimeException("사원이 조회되지 않았습니다!")
                );
        //then

        /*
            select쿼리가 안나갔는데 조회가 된 이유는
            영속성 컨텍스트라는 개념이 JPA에 존재하는데
            INSERT 직후 SELECT를 하면 두개의 쿼리가 하나의 트랜잭션으로 연결되어
            INSERT한 내용을 메모리에서 불러오기 때문에 DB를 조회하지 않고 성능최적화를 함
         */

        System.out.println("\n\n\n");
//        System.out.println("employee = " + employee.toString());
        System.out.println(employee.getName());
        System.out.println("\n\n\n");
    }


}