package com.ileiwe.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ileiwe.data.model.dto.CourseDto;
import com.ileiwe.services.course.CourseService;
import com.ileiwe.services.instructor.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;


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

    @PostMapping(path ="", consumes = {MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> createCourse(@RequestParam("jsonRequest") String jsonRequest, @RequestParam("file") MultipartFile file){
        try {
            CourseDto courseDto  = new ObjectMapper().readValue(jsonRequest, CourseDto.class);
            return ResponseEntity.ok().body(instructorService.createCourse(courseDto, file));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> getCourses(@RequestParam String title){
        try {

            return ResponseEntity.ok().body(instructorService.getCourses(title));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> unEnrollForCourse(@PathVariable("id") Long courseId){
        try {

            return ResponseEntity.ok(courseService.getCourseById(courseId));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }




}
