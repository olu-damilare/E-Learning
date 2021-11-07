package com.ileiwe.services.student;


import com.ileiwe.data.model.Course;
import com.ileiwe.data.model.Student;
import com.ileiwe.data.model.dto.StudentPartyDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentService {

    Student registerStudent(StudentPartyDto studentPartyDto);

    Student findStudentById(Long studentId);

    Student findStudentByUsername(String username);

    void enroll(Long studentId, Long courseId);

    void unEnroll(Long studentId, Long courseId);

    List<Course> findAllCoursesForStudent(String username);

    Student enableStudent(String username);
}
