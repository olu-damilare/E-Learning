package com.ileiwe.services.instructor;


import com.ileiwe.data.model.Course;
import com.ileiwe.data.model.Instructor;
import com.ileiwe.data.model.dto.CourseCreateDto;
import com.ileiwe.data.model.dto.InstructorDetailsDto;
import com.ileiwe.data.model.dto.InstructorPartyDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface InstructorService {

    Instructor saveInstructor(InstructorPartyDto instructor);
    Course createCourse(CourseCreateDto courseDto, MultipartFile courseImage) throws IOException;

    Course updateCourse(CourseCreateDto courseUpdateDto, Long courseId, MultipartFile courseImage);

    InstructorDetailsDto findByUsername(String username);

    void deleteCourse(String instructorUsername, Long courseId);

    List<Course> getCourses(String title);

    List<Course> getInstructorCourses(String instructorUsername);

    Instructor enableInstructor(Long instructorId);

    List<InstructorDetailsDto> getAllInstructors();
}
