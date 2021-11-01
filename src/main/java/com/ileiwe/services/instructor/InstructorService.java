package com.ileiwe.services.instructor;


import com.ileiwe.data.model.Instructor;
import com.ileiwe.data.model.dto.InstructorPartyDto;

public interface InstructorService {

    Instructor saveInstructor(InstructorPartyDto instructor);

}
