package com.ileiwe.data.repository;

import com.ileiwe.data.model.Authority;
import com.ileiwe.data.model.LearningParty;
import com.ileiwe.data.model.Role;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import static com.ileiwe.data.model.Role.ROLE_STUDENT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
//@Sql("/db/insert.sql")
@Transactional
class LearningPartyRepositoryTest {

    @Autowired
    private LearningPartyRepository learningPartyRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
//        learningPartyRepository.deleteAll();
    }


    @Test
//    @Rollback(value = false)
    void testToCreateLearningParty(){
        LearningParty learningUser = new LearningParty("dammy@gmail.com", "Password123", new Authority(ROLE_STUDENT));
        learningPartyRepository.save(learningUser);
        assertThat(learningUser.getId()).isNotNull();
        assertThat(learningUser.getEmail()).isEqualTo("dammy@gmail.com");
        assertThat(learningUser.getAuthorities().get(0).getAuthority()).isEqualTo(ROLE_STUDENT);
        log.info("Learning partners in db --> {}", learningPartyRepository.findAll());

        log.info("After saving ---> {}", learningUser);
    }

    @Test
//    @Transactional
//    @Rollback(value=false)
    void testToCreateLearningPartyWithDuplicateEmail(){
        LearningParty learningUser = new LearningParty ("gozie@yahoo.com",
                "gozie123",
                new Authority ( ROLE_STUDENT) );
        LearningParty learningUser1 = new LearningParty ("gozie@yahoo.com",
                "gozie123",
                new Authority ( ROLE_STUDENT) );
        log.info ( "Before saving --> {}", learningUser );
        learningPartyRepository.save ( learningUser );
        assertThrows ( DataIntegrityViolationException.class, ()->learningPartyRepository.save ( learningUser1 ) ) ;
        log.info ( "After Saving --> {}", learningUser );

    }

    @Test
    void testToForNullValue(){

        LearningParty learningUser1 = new LearningParty (null,
                null,
                new Authority ( ROLE_STUDENT) );
        log.info ( "Before saving --> {}", learningUser1 );
        assertThrows ( ConstraintViolationException.class, ()->learningPartyRepository.save ( learningUser1 ) ) ;
        log.info ( "After Saving --> {}", learningUser1 );

    }

    @Test
//    @Rollback(value=false)
//    @Transactional
    void testToSaveLearningPartyWithEmptyStringValue(){

        LearningParty learningUser1 = new LearningParty ("",
                "",
                new Authority ( ROLE_STUDENT) );
        assertThrows ( ConstraintViolationException.class, ()-> learningPartyRepository.save ( learningUser1 )) ;

    }

    @Test
    void testToSaveLearningPartyWithBlankStringValue(){

        LearningParty learningUser1 = new LearningParty (" ",
                "  ",
                new Authority ( ROLE_STUDENT) );
        assertThrows ( ConstraintViolationException.class, ()-> learningPartyRepository.save ( learningUser1 )) ;

    }

    @Test
    @Rollback(value=false)
    void testToFindLearningPartyByUsernameTest(){
        LearningParty learningUser = new LearningParty("dami@gmail.com", "Password123", new Authority(ROLE_STUDENT));
        learningPartyRepository.save(learningUser);

        LearningParty learningParty = learningPartyRepository.findByEmail("dami@gmail.com");
        assertThat(learningParty).isNotNull();
        assertThat(learningParty.getEmail()).isEqualTo("dami@gmail.com");

        log.info("Learning party object --> {}", learningParty);
    }


}