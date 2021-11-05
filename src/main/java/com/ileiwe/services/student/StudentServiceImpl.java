package com.ileiwe.services.student;

import com.ileiwe.data.model.Authority;
import com.ileiwe.data.model.Instructor;
import com.ileiwe.data.model.LearningParty;
import com.ileiwe.data.model.Student;
import com.ileiwe.data.model.dto.StudentPartyDto;
import com.ileiwe.data.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.ileiwe.data.model.Role.ROLE_STUDENT;

@Service
public class StudentServiceImpl implements StudentService{

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    StudentRepository studentRepository;


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

        return studentRepository.save(student);

    }
}
