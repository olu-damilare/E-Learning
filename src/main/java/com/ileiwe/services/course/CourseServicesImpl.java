package com.ileiwe.services.course;

import com.ileiwe.data.model.Course;
import com.ileiwe.data.model.dto.CourseDto;
import com.ileiwe.data.repository.CourseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CourseServicesImpl implements CourseService{

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    CourseRepository courseRepository;

    @Override
    public Course saveCourse(CourseDto courseDto) {

        if(courseDto == null){
            throw new IllegalArgumentException("course cannot be null");
        }
        Course course = new Course();
        modelMapper.map(courseDto, course);

        return courseRepository.save(course);
    }
}
