package com.ileiwe.services.instructor;


import com.ileiwe.data.model.*;
import com.ileiwe.data.model.dto.CourseDto;
import com.ileiwe.data.model.dto.InstructorPartyDto;
import com.ileiwe.data.repository.InstructorRepository;
import com.ileiwe.services.course.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

import static com.ileiwe.data.model.Role.ROLE_INSTRUCTOR;

@Service
@Slf4j
public class InstructorServiceImpl implements InstructorService{

    @Autowired
    InstructorRepository instructorRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    CourseService courseService;

    @Override
    public Instructor saveInstructor(InstructorPartyDto instructorPartyDto) {
        if(instructorPartyDto == null){
            throw new IllegalArgumentException("Instructor cannot be null");
        }
        LearningParty learningParty = new LearningParty(instructorPartyDto.getEmail(), passwordEncoder.encode(instructorPartyDto.getPassword()), new Authority(ROLE_INSTRUCTOR));
        Instructor instructor = Instructor.builder()
                                        .firstName(instructorPartyDto.getFirstName())
                                        .lastName(instructorPartyDto.getLastName())
                                        .learningParty(learningParty)
                                        .build();



       return instructorRepository.save(instructor);

    }

    @Override
    @Transactional
    public Course createCourse(CourseDto courseDto, MultipartFile courseImage) {
        if(courseDto == null){
            throw new IllegalArgumentException("Course cannot be null");
        }

//        Instructor instructor = instructorRepository.findByLearningParty_Email(courseDto.getInstructorUsername());
//
////        log.info("found instructor --> {}", instructor);
//        Course course = new Course();
//        modelMapper.map(courseDto, course);
//        log.info("course before saving --> {}", courseDto);
//
//        course.setInstructor(instructor);
//        instructor.addCourse(course);
//
//       course = courseService.saveCourse(courseDto);
//
//        log.info("course after saving --> {}", course);
//
//
//        log.info("instructor after saving course --> {}", instructor);

        return courseService.saveCourse(courseDto);
    }

    @Override
    public Course updateCourse(CourseDto courseUpdateDto, Long courseId, MultipartFile courseImage) {
       return courseService.updateCourse(courseUpdateDto, courseId);
    }

    public Instructor findByUsername(String username){
        if(username == null){
            throw new IllegalArgumentException("username cannot be null");
        }

        return instructorRepository.findByLearningParty_Email(username);

    }

    @Override
    public void deleteCourse(String instructorUsername, Long courseId) {
        Instructor instructor = instructorRepository.findByLearningParty_Email(
                                instructorUsername);
        if(instructor == null){
            throw  new IllegalArgumentException("Invalid instructor id");

        }

        Course course = courseService.findById(courseId);

        instructor.removeCourse(course.getId());
        courseService.deleteCourse(course.getId());

        instructorRepository.save(instructor);
    }

    @Override
    public List<Course> getCourses(String title) {
        return courseService.getCoursesByTitle(title);
    }

    @Override
    public List<Course> getInstructorCourses(String instructorUsername) {
        return instructorRepository.findByLearningParty_Email(instructorUsername)
                .getCourses().stream()
                .filter(Course::isPublished)
                .collect(Collectors.toList());
    }


}
