package com.ileiwe.services.instructor;


import com.ileiwe.data.model.Course;
import com.ileiwe.data.model.Instructor;
import com.ileiwe.data.model.dto.CourseDto;
import com.ileiwe.data.model.dto.InstructorPartyDto;
import org.springframework.web.multipart.MultipartFile;

public interface InstructorService {

    Instructor saveInstructor(InstructorPartyDto instructor);
    Course createCourse(CourseDto courseDto, MultipartFile courseImage);

}
