package com.ileiwe.services.course;

import com.ileiwe.data.model.Course;
import com.ileiwe.data.model.dto.CourseDto;

public interface CourseService {

    Course saveCourse(CourseDto courseDto);
}
