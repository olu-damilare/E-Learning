package com.ileiwe.services.student;

import com.ileiwe.data.model.*;
import com.ileiwe.data.model.dto.StudentPartyDto;
import com.ileiwe.data.repository.StudentRepository;
import com.ileiwe.services.course.CourseService;
import com.ileiwe.services.mail.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.ileiwe.data.model.Role.ROLE_STUDENT;

@Service
public class StudentServiceImpl implements StudentService{

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    CourseService courseService;

    @Autowired
    EmailService emailService;


    @Override
    public Student registerStudent(StudentPartyDto studentPartyDto) {
        if(studentPartyDto == null){
            throw new IllegalArgumentException("Student cannot be null");
        }
        if(studentPartyDto.getDayOfBirth() == null){
            throw new IllegalArgumentException("Please provide a valid day of birth");
        }
        if(studentPartyDto.getMonthOfBirth() == null){
            throw new IllegalArgumentException("Please provide a valid month of birth");
        }

        if(studentPartyDto.getYearOfBirth() == null){
            throw new IllegalArgumentException("Please provide a valid year of birth");
        }

        LearningParty learningParty = new LearningParty(studentPartyDto.getEmail(), passwordEncoder.encode(studentPartyDto.getPassword()), new Authority(ROLE_STUDENT));
        Student student = Student.builder()
                .firstName(studentPartyDto.getFirstName())
                .lastName(studentPartyDto.getLastName())
                .dob(LocalDate.of(studentPartyDto.getYearOfBirth(), studentPartyDto.getMonthOfBirth(), studentPartyDto.getDayOfBirth()))
                .gender(studentPartyDto.getGender())
                .learningParty(learningParty)
                .build();

        emailService.sendMail(studentPartyDto);

        return studentRepository.save(student);

    }

    @Override
    public Student findStudentById(Long studentId) {
        return studentRepository.findById(studentId).orElseThrow(() -> new IllegalArgumentException("Invalid student id"));
    }

    @Override
    public void enroll(Long studentId, Long courseId) {
        Student student = findStudentById(studentId);
        if(!student.getLearningParty().isEnabled()){
            throw new IllegalArgumentException("This user account has not been activated.");
        }

        Course course = courseService.findById(courseId);

        student.addCourse(course);
        course.addStudent(student);

        studentRepository.save(student);
    }

    @Override
    public void unEnroll(Long studentId, Long courseId) {
        Student student = findStudentById(studentId);
        if(!student.getLearningParty().isEnabled()){
            throw new IllegalArgumentException("This user account has not been activated.");
        }

        Course course = courseService.findById(courseId);

        student.getCourses().removeIf(course1 -> course1.getId().equals(courseId));
        course.getStudents().removeIf(student1 -> student1.getId().equals(studentId));

        studentRepository.save(student);
    }

    @Override
    public List<Course> findAllCoursesForStudent(String username) {
        Student student = studentRepository.findByLearningParty_Email(username);
        if(student == null){
            throw new IllegalArgumentException("Invalid student username");
        }
        if(!student.getLearningParty().isEnabled()){
            throw new IllegalArgumentException("This user account has not been activated.");
        }
        return student.getCourses();
    }

    @Override
    public Student enableStudent(String username) {
        Student student = studentRepository.findByLearningParty_Email(username);

        if(student == null){
            throw new IllegalArgumentException("Invalid username");
        }

        student.getLearningParty().setEnabled(true);

        return studentRepository.save(student);
    }

    @Override
    public Student findStudentByUsername(String username) {
        return studentRepository.findByLearningParty_Email(username);
    }
}
