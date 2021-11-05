package com.ileiwe.services.student;


import com.ileiwe.data.model.Student;
import com.ileiwe.data.model.dto.StudentPartyDto;
import org.springframework.stereotype.Service;

@Service
public interface StudentService {

    Student registerStudent(StudentPartyDto studentPartyDto);

    Student findStudentById(Long studentId);

    Student findStudentByUsername(String username);

    void enroll(Long studentId, Long courseId);

    void unEnroll(Long studentId, Long courseId);
}
