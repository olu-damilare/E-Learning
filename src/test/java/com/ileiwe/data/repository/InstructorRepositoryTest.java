package com.ileiwe.data.repository;

import com.ileiwe.data.model.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;

import static com.ileiwe.data.model.Role.ROLE_INSTRUCTOR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
//@Sql(scripts = {"/db/insert.sql"})
@Slf4j
@Transactional
public class InstructorRepositoryTest {

    @Autowired
    InstructorRepository instructorRepository;


    @Test
    void testToSaveInstructorAsLearningParty(){
        LearningParty user = new LearningParty("trainer@ileiwe.com", "Password123", new Authority(ROLE_INSTRUCTOR));

        Instructor instructor =
                Instructor.builder()
                        .firstName("John")
                        .lastName("Doe")
                        .learningParty(user)
                        .build();

        instructorRepository.save(instructor);
        assertThat(instructor.getId()).isNotNull();
        assertThat(instructor.getLearningParty().getId()).isNotNull();

        log.info("Instructor after saving to db --> {}", instructor);
    }

    @Test
    void testToInvalidateNullFirstNameAndLastNameForInstructor(){
        LearningParty user = new LearningParty ("trainer@mail.com",
                "trainer123",
                new Authority ( Role.ROLE_INSTRUCTOR) );

        Instructor instructor = Instructor.builder ()
                .firstName ( null )
                .lastName ( null )
                .learningParty ( user )
                .build ();
        log.info ( "Instructor before saving --> {}", instructor );
        assertThrows ( ConstraintViolationException.class, ()-> instructorRepository.save ( instructor )) ;
        log.info ( "Instructor After saving --> {}", instructor );

    }

    @Test
    void testToInvalidateEmptyFirstNameAndLastNameForInstructor(){
        LearningParty user = new LearningParty ("trainer@mail.com",
                "trainer123",
                new Authority ( Role.ROLE_INSTRUCTOR) );

        Instructor instructor = Instructor.builder ()
                .firstName ( "" )
                .lastName ( "" )
                .learningParty ( user )
                .build ();
        log.info ( "Instructor before saving --> {}", instructor );
        assertThrows ( ConstraintViolationException.class, ()-> instructorRepository.save ( instructor )) ;
        log.info ( "Instructor After saving --> {}", instructor );

    }

    @Test
    void testToInvalidateBlankFirstNameAndLastNameForInstructor(){
        LearningParty user = new LearningParty ("trainer@mail.com",
                "trainer123",
                new Authority ( Role.ROLE_INSTRUCTOR) );

        Instructor instructor = Instructor.builder ()
                .firstName ( "  " )
                .lastName ( " " )
                .learningParty ( user )
                .build ();
        log.info ( "Instructor before saving --> {}", instructor );
        assertThrows ( ConstraintViolationException.class, ()-> instructorRepository.save ( instructor )) ;
        log.info ( "Instructor After saving --> {}", instructor );

    }

    @Test
    void testToUpdateInstructorTableAfterCreate(){
        LearningParty user = new LearningParty("trainer@ileiwe.com", "Password123", new Authority(ROLE_INSTRUCTOR));

        Instructor instructor = Instructor.builder()
                .firstName ( "John")
                .lastName("Doe")
                .learningParty(user)
                .build();

        log.info("Before saving --> {}", instructor);

        instructorRepository.save(instructor);
        assertThat(instructor.getId()).isNotNull();
        assertThat(instructor.getLearningParty().getId()).isNotNull();

        log.info("After saving --> {}", instructor);

        Instructor savedInstructor = instructorRepository.findById(instructor.getId()).orElse(null);

        assertThat(savedInstructor).isNotNull();
        assertThat(savedInstructor.getBio()).isNull();

        savedInstructor.setBio("I am a programmer");
        savedInstructor.setGender(Gender.MALE);

        instructorRepository.save(savedInstructor);
        assertThat(savedInstructor.getBio()).isNotNull();
        assertThat(savedInstructor.getGender()).isNotNull();


    }



}
