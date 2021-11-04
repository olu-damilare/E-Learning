package com.ileiwe.services.instructor;


import com.ileiwe.data.model.Course;
import com.ileiwe.data.model.Instructor;
import com.ileiwe.data.model.dto.CourseDto;
import com.ileiwe.data.model.dto.InstructorPartyDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface InstructorService {

    Instructor saveInstructor(InstructorPartyDto instructor);
    Course createCourse(CourseDto courseDto, MultipartFile courseImage);

    Course updateCourse(CourseDto courseUpdateDto, Long courseId, MultipartFile courseImage);

    Instructor findByUsername(String username);

    void deleteCourse(String instructorUsername, Long courseId);

    List<Course> getCourses(String title);
}
