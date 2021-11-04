package com.ileiwe.controller;


import com.ileiwe.data.model.dto.CourseDto;
import com.ileiwe.services.course.CourseService;
import com.ileiwe.services.instructor.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    CourseService courseService;

    @Autowired
    InstructorService instructorService;


    @GetMapping("")
    public ResponseEntity<?> findAll(){
        try{
            return ResponseEntity.ok().body(courseService.findAll());
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@RequestBody CourseDto courseDto,
                                          @PathVariable("id") Long id
    ){
        try {
            instructorService.deleteCourse(courseDto.getInstructorUsername(), id);
            return ResponseEntity.ok().build();
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateCourse(@RequestBody CourseDto courseDto,
                                          @PathVariable Long id,
                                          @RequestBody MultipartFile courseImage){
        try {
            return ResponseEntity.ok().body(instructorService.updateCourse(courseDto, id, courseImage));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("")
    public ResponseEntity<?> createCourse(@RequestBody CourseDto courseDto, @RequestBody MultipartFile courseImage){
        try {
            return ResponseEntity.ok().body(instructorService.createCourse(courseDto, courseImage));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/course")
    public ResponseEntity<?> getCourses(@RequestParam("title") String title){
        try {
            System.out.println(title);

            return ResponseEntity.ok().body(instructorService.getCourses(title));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
