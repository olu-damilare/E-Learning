package com.ileiwe.services.course;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.ileiwe.data.model.Course;
import com.ileiwe.data.model.Instructor;
import com.ileiwe.data.model.dto.CourseDetailsDto;
import com.ileiwe.data.model.dto.CourseCreateDto;
import com.ileiwe.data.repository.CourseRepository;
import com.ileiwe.data.repository.InstructorRepository;
import com.ileiwe.services.instructor.InstructorService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@Slf4j
public class CourseServicesImpl implements CourseService{

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    InstructorService instructorService;

    @Autowired
    Cloudinary cloudinary;

    @Autowired
    InstructorRepository instructorRepository;

    @Override
    public Course saveCourse(CourseCreateDto courseDto, MultipartFile courseImage) throws IOException {

        if(courseDto == null){
            throw new IllegalArgumentException("Course cannot be null");
        }

        Instructor instructor = instructorRepository.findByLearningParty_Email(courseDto.getInstructorUsername());
        log.info("found instructor --> {}", instructor);

        Course course = new Course();
        modelMapper.map(courseDto, course);
        Map<?, ? > uploadResponse = uploadImage(courseImage);
        log.info("Upload response --> {}", uploadResponse);

        String imageUrl = (String) uploadResponse.get("url");
        course.addImageUrl(imageUrl);

        log.info("course before saving --> {}", courseDto);

        course.setInstructor(instructor);
        instructor.addCourse(course);

        course = courseRepository.save(course);
        log.info("course after saving --> {}", course);
        log.info("instructor after saving course --> {}", instructor);


        return course;
    }

    public Map<?, ?> uploadImage(MultipartFile file) throws IOException {

        Map<?, ?> imageProperties = ObjectUtils.asMap("transformation", new Transformation()
                .background("black")
                .gravity("face")
                .height(700)
                .width(700)
                .crop("fill")
                .chain()
                .opacity(50).chain());

        return cloudinary.uploader().upload(file.getBytes(), imageProperties);
    }

    @Override
    public Course findById(Long id) {
        return courseRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid course id"));
    }

    @Override
    public Course updateCourse(CourseCreateDto courseDto, Long courseId) {
        if(courseDto == null){
            throw new IllegalArgumentException("Please provide a field to update");
        }
        Course course = courseRepository.findById(courseId).orElse(null);
        if(course == null){
            throw new IllegalArgumentException("Invalid course id");
        }

        log.info("Course dto in update impl --> {}", courseDto);
        modelMapper.map(courseDto, course);
        if(courseDto.isPublished()){
//            course.setPublished(true);
            course.setDatePublished(LocalDate.now());
        }
        log.info("fetched course --> {}", course);

        List<String> updateImageUrls = courseDto.getImageUpdateUrls();
        if(updateImageUrls != null){
            for (String updateImageUrl : updateImageUrls) {
                course.addImageUrl(updateImageUrl);
            }
        }

        List<Course> courses =  course.getInstructor().getCourses();
        for (int i = 0; i < courses.size(); i++) {
            if(courses.get(i).getId().equals(course.getId())){
                courses.set(i, course);
                break;
            }
        }
        return courseRepository.save(course);
    }

    @Override
    public void deleteCourse(Long courseId) {
        courseRepository.deleteById(courseId);
    }

    @Override
    public List<Course> getCoursesByTitle(String title) {
        return courseRepository.findAll().stream()
                .filter(course -> {
                    String savedCourseTitle = course.getTitle().toLowerCase();
                    String searchTitle = title.toLowerCase();
                    return course.isPublished() && savedCourseTitle.contains(searchTitle);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseDetailsDto> findAll() {
        return courseRepository.findAll().stream()
                .filter(Course::isPublished)
                .map(course -> modelMapper.map(course, CourseDetailsDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public CourseDetailsDto getCourseById(Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(()-> new IllegalArgumentException("Invalid course id"));
        if(!course.isPublished()){
            throw new IllegalStateException("The course with this id has not been published");
        }
        CourseDetailsDto courseDetailsDto = new CourseDetailsDto();
        modelMapper.map(course, courseDetailsDto);
        courseDetailsDto.setInstructorUsername(course.getInstructor().getLearningParty().getEmail());

        return courseDetailsDto;
    }


}
