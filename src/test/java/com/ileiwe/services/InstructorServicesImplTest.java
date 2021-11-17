package com.ileiwe.services;


import com.ileiwe.data.model.Authority;
import com.ileiwe.data.model.Course;
import com.ileiwe.data.model.Instructor;
import com.ileiwe.data.model.LearningParty;
import com.ileiwe.data.model.dto.CourseCreateDto;
import com.ileiwe.data.repository.InstructorRepository;
import com.ileiwe.services.instructor.InstructorService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

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

    LearningParty user;
    Instructor instructor;


    @BeforeEach
    void setUp(){
        user = new LearningParty("trainer@ileiwe.com", "Password123", new Authority(ROLE_INSTRUCTOR));

       instructor =
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
    void testThatInstructorCanCreateCourse(){


        Instructor savedInstructor = instructorRepository.findById(instructor.getId()).orElse(null);
        assertThat(savedInstructor).isNotNull();

        CourseCreateDto course = CourseCreateDto.builder()
                .title("How to program in Java")
                .instructorUsername(savedInstructor.getLearningParty().getEmail())
                .description("lorem ipsum")
                .language("English")
                .duration("28 hours of recorded video")
                .build();


        Course savedCourse = null;
        try {
            savedCourse = instructorService.createCourse(course, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        log.info("saved course is --> {}", savedCourse);

        assertThat(savedCourse.getId()).isNotNull();


        log.info("Instructor after creating course --> {}", savedInstructor);

        assertThat(savedInstructor.getCourses().get(0).getTitle()).isEqualTo(course.getTitle());
        assertThat(course.getInstructorUsername()).isEqualTo(instructor.getLearningParty().getEmail());

    }


    @Test
    void testToUpdateCourse(){

        Instructor savedInstructor = instructorRepository.findById(instructor.getId()).orElse(null);
        assertThat(savedInstructor).isNotNull();

        CourseCreateDto course = CourseCreateDto.builder()
                .title("How to program in Java")
                .instructorUsername(savedInstructor.getLearningParty().getEmail())
                .description("lorem ipsum")
                .language("English")
                .duration("28 hours of recorded video")
                .build();


        Course savedCourse = null;
        try {
            savedCourse = instructorService.createCourse(course, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        log.info("saved course is --> {}", savedCourse);

        assertThat(savedCourse.getId()).isNotNull();

        assertThat(savedCourse.getDuration()).isEqualTo(course.getDuration());

        CourseCreateDto courseUpdateDto = CourseCreateDto.builder()
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

    @Test
    void testToPublishCourse(){

        Instructor savedInstructor = instructorRepository.findById(instructor.getId()).orElse(null);
        assertThat(savedInstructor).isNotNull();

        CourseCreateDto course = CourseCreateDto.builder()
                .title("How to program in Java")
                .instructorUsername(savedInstructor.getLearningParty().getEmail())
                .description("lorem ipsum")
                .language("English")
                .duration("28 hours of recorded video")
                .build();


        Course savedCourse = null;
        try {
            savedCourse = instructorService.createCourse(course, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        log.info("saved course is --> {}", savedCourse);

        assertThat(savedCourse.getId()).isNotNull();

        assertThat(savedCourse.isPublished()).isEqualTo(false);

        CourseCreateDto courseUpdateDto = CourseCreateDto.builder()
                .isPublished(true)
                .build();

        log.info("Instructor before publishing course --> {}", instructor);

        Course updatedCourse = instructorService.updateCourse(courseUpdateDto, savedCourse.getId(), null);
//        savedInstructor = instructorRepository.findById(instructor.getId()).get();

        log.info("Instructor after publishing course --> {}", instructor);
        assertThat(savedInstructor.getCourses().get(0).isPublished()).isEqualTo(true);
        assertThat(updatedCourse.isPublished()).isEqualTo(true);


    }

    @Test
    void testToRemoveCourse(){

        Instructor savedInstructor = instructorRepository.findById(instructor.getId()).orElse(null);
        assertThat(savedInstructor).isNotNull();

        CourseCreateDto course = CourseCreateDto.builder()
                .title("How to program in Java")
                .instructorUsername(savedInstructor.getLearningParty().getEmail())
                .description("lorem ipsum")
                .language("English")
                .duration("28 hours of recorded video")
                .build();


        Course savedCourse = null;
        try {
            savedCourse = instructorService.createCourse(course, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        log.info("saved course is --> {}", savedCourse);

        assertThat(savedCourse.getId()).isNotNull();
        assertThat(savedInstructor.getCourses().size()).isGreaterThan(0);

        instructorService.deleteCourse(instructor.getLearningParty().getEmail(), savedCourse.getId());

//        assertThat(savedCourse).isNull();
        assertThat(savedInstructor.getCourses().size()).isEqualTo(0);


    }


    @Test
    void testToGetCoursesByTitle(){

        Instructor savedInstructor = instructorRepository.findById(instructor.getId()).orElse(null);
        assertThat(savedInstructor).isNotNull();

        CourseCreateDto course = CourseCreateDto.builder()
                .title("How to program in Java")
                .instructorUsername(savedInstructor.getLearningParty().getEmail())
                .description("lorem ipsum")
                .language("English")
                .duration("28 hours of recorded video")
                .build();

        CourseCreateDto secondCourse = CourseCreateDto.builder()
                .title("How to program in Python")
                .instructorUsername(savedInstructor.getLearningParty().getEmail())
                .description("lorem ipsum")
                .language("English")
                .duration("4 days of recorded video")
                .build();


        assertThat(savedInstructor.getCourses()).isNull();

        try {
            instructorService.createCourse(course, null);
            instructorService.createCourse(secondCourse, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Course> courses = instructorService.getCourses("How to ");
        assertThat(courses.size()).isEqualTo(2);

        for(Course course1: courses){
            assertThat(course1.getTitle()).contains("How to ");
        }


        assertThat(savedInstructor.getCourses().size()).isGreaterThan(0);


    }









}
