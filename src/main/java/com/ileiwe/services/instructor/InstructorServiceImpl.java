package com.ileiwe.services.instructor;


import com.ileiwe.data.model.*;
import com.ileiwe.data.model.dto.CourseCreateDto;
import com.ileiwe.data.model.dto.InstructorDetailsDto;
import com.ileiwe.data.model.dto.InstructorPartyDto;
import com.ileiwe.data.repository.InstructorRepository;
import com.ileiwe.services.course.CourseService;
import com.ileiwe.services.course.CourseServicesImpl;
import com.ileiwe.services.mail.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.ileiwe.data.model.Role.ROLE_INSTRUCTOR;

@Service
@Slf4j
public class InstructorServiceImpl implements InstructorService{

    @Autowired
    InstructorRepository instructorRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    CourseService courseService;

    @Autowired
    EmailService emailService;


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

       emailService.sendMail(instructorPartyDto);

        return instructorRepository.save(instructor);

    }



    @Override
    @Transactional
    public Course createCourse(CourseCreateDto courseDto, MultipartFile courseImage) throws IOException {
        if(courseDto == null){
            throw new IllegalArgumentException("Course cannot be null");
        }

        return courseService.saveCourse(courseDto, courseImage);
    }

    @Override
    public Course updateCourse(CourseCreateDto courseUpdateDto, Long courseId, MultipartFile courseImage) {
        if(courseImage != null){
            CourseServicesImpl courseServicesImpl = new CourseServicesImpl();
            try {
               Map<?,?> uploadResponse = courseServicesImpl.uploadImage(courseImage);
                log.info("Upload response --> {}", uploadResponse);

                String imageUrl = (String) uploadResponse.get("url");
                courseUpdateDto.addImageUrl(imageUrl);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

       return courseService.updateCourse(courseUpdateDto, courseId);
    }

    @Override
    public InstructorDetailsDto findByUsername(String username){
        if(username == null){
            throw new IllegalArgumentException("username cannot be null");
        }

        return getInstructor(username);

    }

    private InstructorDetailsDto getInstructor(String username) {
        Instructor instructor = instructorRepository.findByLearningParty_Email(username);
        if(instructor == null){
            throw  new IllegalArgumentException("Invalid instructor id");

        }
        if(!instructor.getLearningParty().isEnabled()){
            throw new IllegalStateException("This account is not activated.");
        }
        InstructorDetailsDto instructorDetails = new InstructorDetailsDto();
        modelMapper.map(instructor, instructorDetails);
        instructorDetails.setEnabled(instructor.getLearningParty().isEnabled());

        return instructorDetails;
    }

    @Override
    public void deleteCourse(String instructorUsername, Long courseId) {
        Instructor instructor = instructorRepository.findByLearningParty_Email(instructorUsername);

        Course course = courseService.findById(courseId);

        instructor.removeCourse(course.getId());
        courseService.deleteCourse(course.getId());

        instructorRepository.save(instructor);
    }

    @Override
    public List<Course> getCourses(String title) {
        return courseService.getCoursesByTitle(title);
    }

    @Override
    public List<Course> getInstructorCourses(String instructorUsername) {
        Instructor instructor = instructorRepository.findByLearningParty_Email(instructorUsername);


        return instructor.getCourses().stream()
                .filter(Course::isPublished)
                .collect(Collectors.toList());
    }

    @Override
    public Instructor enableInstructor(Long instructorId) {
       Instructor instructor = instructorRepository.findById(instructorId).orElse(null);

       if(instructor == null){
           throw new IllegalArgumentException("Invalid username");
       }

       instructor.getLearningParty().setEnabled(true);

       return instructorRepository.save(instructor);
    }

    @Override
    public List<InstructorDetailsDto> getAllInstructors() {
        return instructorRepository.findAll().stream()
                .map(instructor -> {
                    InstructorDetailsDto instructorDetailsDto = new InstructorDetailsDto();
                    modelMapper.map(instructor, instructorDetailsDto);
                    instructorDetailsDto.setEnabled(instructor.getLearningParty().isEnabled());

                    return instructorDetailsDto;
                    }
                )
                .collect(Collectors.toList());
    }


}
