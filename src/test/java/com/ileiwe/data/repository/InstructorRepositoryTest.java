package com.ileiwe.data.repository;

import com.ileiwe.data.model.Authority;
import com.ileiwe.data.model.Instructor;
import com.ileiwe.data.model.LearningParty;
import com.ileiwe.data.model.Role;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static com.ileiwe.data.model.Role.ROLE_INSTRUCTOR;
import static org.assertj.core.api.Assertions.assertThat;


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
}
