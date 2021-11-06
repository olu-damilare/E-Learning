package com.ileiwe.services.course;

import com.ileiwe.data.model.Course;
import com.ileiwe.data.model.dto.CourseDetailsDto;
import com.ileiwe.data.model.dto.CourseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CourseService {

    Course saveCourse(CourseDto courseDto, MultipartFile courseImage) throws IOException;

    Course findById(Long id);
    Course updateCourse(CourseDto courseUpdateDto, Long courseId);

    void deleteCourse(Long courseId);

    List<Course> getCoursesByTitle(String title);

    List<Course> findAll();

    CourseDetailsDto getCourseById(Long courseId);
}
