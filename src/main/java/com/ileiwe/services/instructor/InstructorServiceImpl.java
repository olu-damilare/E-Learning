package com.ileiwe.services.instructor;


import com.ileiwe.data.model.Authority;
import com.ileiwe.data.model.Instructor;
import com.ileiwe.data.model.LearningParty;
import com.ileiwe.data.model.Role;
import com.ileiwe.data.model.dto.InstructorPartyDto;
import com.ileiwe.data.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.ileiwe.data.model.Role.ROLE_INSTRUCTOR;

@Service
public class InstructorServiceImpl implements InstructorService{

    @Autowired
    InstructorRepository instructorRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Instructor saveInstructor(InstructorPartyDto instructorPartyDto) {
        if(instructorPartyDto == null){
            throw new IllegalArgumentException("Instructor cannot be null");
        }
        LearningParty learningParty = new LearningParty(instructorPartyDto.getEmail(), passwordEncoder.encode(instructorPartyDto.getPassword()), new Authority(ROLE_INSTRUCTOR));
        Instructor instructor = Instructor.builder()
                                        .firstName(instructorPartyDto.getFirstName())
                                        .lastName(instructorPartyDto.getLastName())
                                        .learningParty(learningParty)
                                        .build();



       return instructorRepository.save(instructor);

    }
}
