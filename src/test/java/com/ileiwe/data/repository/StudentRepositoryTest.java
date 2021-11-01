package com.ileiwe.data.repository;

import com.ileiwe.data.model.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import javax.validation.ConstraintViolationException;

import java.time.LocalDate;

import static com.ileiwe.data.model.Role.ROLE_STUDENT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@Slf4j
@Sql(scripts = {"/db/insert.sql"})
public class StudentRepositoryTest {

    @Autowired
    StudentRepository studentRepository;

//    @AfterEach
//    void tearDown(){
//        studentRepository.deleteAll();
//    }

    @Test
    void testToSaveStudentAsLearningParty(){
        LearningParty user = new LearningParty("learner@ileiwe.com", "Password123", new Authority(ROLE_STUDENT));

        Student student =
                Student.builder()
                        .firstName("John")
                        .lastName("Doe")
                        .learningParty(user)
                        .build();

        studentRepository.save(student);
        assertThat(student.getId()).isNotNull();
        assertThat(student.getLearningParty().getId()).isNotNull();

        log.info("student after saving to db --> {}", student);
    }

    @Test
    void testToInvalidateNullFirstNameAndLastNameForStudent(){
        LearningParty user = new LearningParty ("trainer@mail.com",
                "trainer123",
                new Authority ( ROLE_STUDENT) );

        Student student = Student.builder ()
                .firstName ( null )
                .lastName ( null )
                .learningParty ( user )
                .build ();
        log.info ( "student before saving --> {}", student );
        assertThrows ( ConstraintViolationException.class, ()-> studentRepository.save ( student )) ;
        log.info ( "student After saving --> {}", student );

    }

    @Test
    void testToInvalidateEmptyFirstNameAndLastNameForStudent(){
        LearningParty user = new LearningParty ("learner@gmail.com",
                "trainer123",
                new Authority ( Role.ROLE_STUDENT) );

        Student student = Student.builder ()
                .firstName ( "" )
                .lastName ( "" )
                .learningParty ( user )
                .build ();
        log.info ( "Student before saving --> {}", student );
        assertThrows ( ConstraintViolationException.class, ()-> studentRepository.save (student )) ;
        log.info ( "Student After saving --> {}", student );

    }

    @Test
    void testToInvalidateBlankFirstNameAndLastNameForstudent(){
        LearningParty user = new LearningParty ("trainer@mail.com",
                "trainer123",
                new Authority ( Role.ROLE_STUDENT) );

        Student student = Student.builder ()
                .firstName ( "  " )
                .lastName ( " " )
                .learningParty ( user )
                .build ();
        log.info ( "Student before saving --> {}", student );
        assertThrows ( ConstraintViolationException.class, ()-> studentRepository.save ( student )) ;
        log.info ( "Student After saving --> {}", student );

    }

    @Test
    void testToUpdateStudentTableAfterCreate(){
        LearningParty user = new LearningParty("trainer@ileiwe.com", "Password123", new Authority(ROLE_STUDENT));

        Student student = Student.builder()
                .firstName ( "John")
                .lastName("Doe")
                .learningParty(user)
                .build();

        log.info("Before saving --> {}", student);

        studentRepository.save(student);
        assertThat(student.getId()).isNotNull();
        assertThat(student.getLearningParty().getId()).isNotNull();

        log.info("After saving --> {}", student);

        Student savedStudent = studentRepository.findById(student.getId()).orElse(null);

        assertThat(savedStudent).isNotNull();
        assertThat(savedStudent.getDob()).isNull();

        savedStudent.setGender(Gender.MALE);
        savedStudent.setDob(LocalDate.of(2000, 9, 15));

        studentRepository.save(savedStudent);
        assertThat(savedStudent.getDob()).isNotNull();
        assertThat(savedStudent.getGender()).isNotNull();


    }


}
