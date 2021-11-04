package com.ileiwe.services;


import com.ileiwe.data.model.Authority;
import com.ileiwe.data.model.Course;
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


       Course savedCourse = instructorService.createCourse(course, null);

        log.info("saved course is --> {}", savedCourse);

        assertThat(savedCourse.getId()).isNotNull();


        log.info("Instructor after creating course --> {}", savedInstructor);

        assertThat(savedInstructor.getCourses().get(0).getTitle()).isEqualTo(course.getTitle());
        assertThat(course.getInstructorUsername()).isEqualTo(instructor.getLearningParty().getEmail());

    }


    @Test
    void testToUpdateCourse(){
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


        Course savedCourse = instructorService.createCourse(course, null);

        log.info("saved course is --> {}", savedCourse);

        assertThat(savedCourse.getId()).isNotNull();

        assertThat(savedCourse.getDuration()).isEqualTo(course.getDuration());

        CourseDto courseUpdateDto = CourseDto.builder()
                                            .duration("4 weeks of recorded video")
                                            .build();

        log.info("Instructor before updating course --> {}", instructor);
        Course updatedCourse = instructorService.updateCourse(courseUpdateDto, savedCourse.getId(), null);
        savedInstructor = instructorRepository.findById(instructor.getId()).get();

        log.info("Instructor after updating course --> {}", instructor);
        assertThat(savedInstructor.getCourses().get(0).getDuration()).isEqualTo(courseUpdateDto.getDuration());
        assertThat(updatedCourse.getId()).isEqualTo(savedInstructor.getCourses().get(0).getId());
        assertThat(updatedCourse.getDuration()).isEqualTo(savedInstructor.getCourses().get(0).getDuration());


    }
}
