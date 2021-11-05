package com.ileiwe.services;


import com.ileiwe.data.model.Course;
import com.ileiwe.data.model.Student;
import com.ileiwe.services.course.CourseService;
import com.ileiwe.services.student.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
@Sql("/db/insert.sql")
@Transactional
public class StudentServiceImplTests {

    @Autowired
    StudentService studentService;

    @Autowired
    CourseService courseService;

    @Test
    void testThatStudentCanEnrollForCourse(){
        Student student = studentService.findStudentById(110L);
        assertThat(student).isNotNull();
        assertThat(student.getCourses().size()).isEqualTo(0);
        log.info("Student before enrolling for course --> {}", student);

        Course course = courseService.findById(120L);
        assertThat(course).isNotNull();
        assertThat(course.getStudents().size()).isEqualTo(0);
        log.info("Course before student enrollment --> {}", course);

        studentService.enroll(student.getId(), course.getId());
        log.info("Student after enrolling for course --> {}", student);
        log.info("Course after student enrollment --> {}", course);

        assertThat(student.getCourses()).isNotNull();
        assertThat(student.getCourses().get(0).getId()).isEqualTo(course.getId());

        assertThat(course.getStudents()).isNotNull();
        assertThat(course.getStudents().get(0).getId()).isEqualTo(student.getId());




    }
}
