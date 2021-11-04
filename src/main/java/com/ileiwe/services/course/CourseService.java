package com.ileiwe.services.course;

import com.ileiwe.data.model.Course;
import com.ileiwe.data.model.dto.CourseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CourseService {

    Course saveCourse(CourseDto courseDto);

    Course findById(Long id);
    Course updateCourse(CourseDto courseUpdateDto, Long courseId);

    void deleteCourse(Long courseId);

    List<Course> getCoursesByTitle(String title);

    List<Course> findAll();

}
