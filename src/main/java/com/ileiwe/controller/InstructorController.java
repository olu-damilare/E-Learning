package com.ileiwe.controller;


import com.ileiwe.data.model.dto.CourseDto;
import com.ileiwe.data.model.dto.InstructorPartyDto;
import com.ileiwe.services.instructor.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController()
@RequestMapping("/api/instructor")
public class InstructorController {

    @Autowired
    InstructorService instructorService;

    @PostMapping("/register")
    public ResponseEntity<?> registerAsInstructor(@RequestBody InstructorPartyDto instructorPartyDto){
        try {
            return ResponseEntity.ok().body(instructorService.saveInstructor(instructorPartyDto));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/course")
    public ResponseEntity<?> getCourses(@RequestParam("title") String title){
        try {
            System.out.println(title);
            ;
            return ResponseEntity.ok().body(instructorService.getCourses(title));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/course")
    public ResponseEntity<?> createCourse(@RequestBody CourseDto courseDto, @RequestBody MultipartFile courseImage){
        try {
            return ResponseEntity.ok().body(instructorService.createCourse(courseDto, courseImage));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/course/{id}")
    public ResponseEntity<?> updateCourse(@RequestBody CourseDto courseDto,
                                          @PathVariable Long id,
                                          @RequestBody MultipartFile courseImage){
        try {
            return ResponseEntity.ok().body(instructorService.updateCourse(courseDto, id, courseImage));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/course/{courseId}")
    public ResponseEntity<?> deleteCourse(@RequestBody CourseDto courseDto,
                                          @PathVariable("courseId") Long courseId
                                          ){
        try {
            instructorService.deleteCourse(courseDto.getInstructorUsername(),courseId);
            return ResponseEntity.ok().build();
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }









}
