package com.ileiwe.services.course;

import com.ileiwe.data.model.Course;
import com.ileiwe.data.model.Instructor;
import com.ileiwe.data.model.dto.CourseDto;
import com.ileiwe.data.repository.CourseRepository;
import com.ileiwe.services.instructor.InstructorService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class CourseServicesImpl implements CourseService{

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    InstructorService instructorService;

    @Override
    public Course saveCourse(CourseDto courseDto) {

        if(courseDto == null){
            throw new IllegalArgumentException("Course cannot be null");
        }

        Instructor instructor = instructorService.findByUsername(courseDto.getInstructorUsername());
        log.info("found instructor --> {}", instructor);

        Course course = new Course();
        modelMapper.map(courseDto, course);
        log.info("course before saving --> {}", courseDto);

        course.setInstructor(instructor);
        instructor.addCourse(course);

        course = courseRepository.save(course);

        log.info("course after saving --> {}", course);


        log.info("instructor after saving course --> {}", instructor);


        return courseRepository.save(course);
    }

    @Override
    public Course findById(Long id) {
        return courseRepository.findById(id).orElse(null);
    }

    @Override
    public Course updateCourse(CourseDto courseUpdateDto, Long courseId) {
        if(courseUpdateDto == null){
            throw new IllegalArgumentException("Please provide a field to update");
        }
        Course course = courseRepository.findById(courseId).orElse(null);
        if(course == null){
            throw new IllegalArgumentException("Invalid course id");
        }

        modelMapper.map(courseUpdateDto, course);
        if(courseUpdateDto.isPublished()){
            course.setDatePublished(LocalDate.now());
        }
        log.info("fetched course --> {}", course);

        List<Course> courses =  course.getInstructor().getCourses();
        for (int i = 0; i < courses.size(); i++) {
            if(courses.get(i).getId().equals(course.getId())){
                courses.set(i, course);
                break;
            }
        }
        return courseRepository.save(course);
    }

    @Override
    public void deleteCourse(Long courseId) {
        courseRepository.deleteById(courseId);
    }

    @Override
    public List<Course> getCoursesByTitle(String title) {
        return courseRepository.findAll().stream()
                .filter(course -> {
                    String savedCourseTitle = course.getTitle().toLowerCase();
                    String searchTitle = title.toLowerCase();
                    return course.isPublished() && savedCourseTitle.contains(searchTitle);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Course> findAll() {
        return courseRepository.findAll().stream()
                .filter(Course::isPublished)
                .collect(Collectors.toList());
    }
}
