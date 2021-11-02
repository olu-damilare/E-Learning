package com.ileiwe.data.services;


import com.ileiwe.data.model.Authority;
import com.ileiwe.data.model.Instructor;
import com.ileiwe.data.model.LearningParty;
import com.ileiwe.data.model.dto.CourseDto;
import com.ileiwe.data.repository.InstructorRepository;
import com.ileiwe.services.instructor.InstructorService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static com.ileiwe.data.model.Role.ROLE_INSTRUCTOR;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
@Transactional
public class InstructorServicesImplTest {

    @Autowired
    InstructorRepository instructorRepository;

    @Autowired
    InstructorService instructorService;

    @Test
    void testThatInstructorCanCreateCourse(){

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

        Instructor savedInstructor = instructorRepository.findById(instructor.getId()).orElse(null);
        assertThat(savedInstructor).isNotNull();

        CourseDto course = CourseDto.builder()
                .title("How to program in Java")
                .instructorUsername(savedInstructor.getLearningParty().getEmail())
                .description("lorem ipsum")
                .language("English")
                .duration("28 hours of recorded video")
                .build();


        instructorService.createCourse(course, null);


        log.info("Instructor after creating course --> {}", savedInstructor);

        assertThat(savedInstructor.getCourses().get(0).getTitle()).isEqualTo(course.getTitle());
        assertThat(course.getInstructorUsername()).isEqualTo(instructor.getLearningParty().getEmail());

    }
}
