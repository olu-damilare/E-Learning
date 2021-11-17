package com.ileiwe.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ileiwe.data.model.dto.CourseDetailsDto;
import com.ileiwe.data.model.dto.CourseCreateDto;
import com.ileiwe.services.course.CourseService;
import com.ileiwe.services.instructor.InstructorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;


@RestController
@Slf4j
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    CourseService courseService;

    @Autowired
    InstructorService instructorService;

    @Autowired
    InstructorController instructorController;


    @GetMapping("")
    public ResponseEntity<?> findAll(){
        try{
            List<CourseDetailsDto> courses = courseService.findAll().stream()
                                                            .map(course -> {
                                                                Link instructorDetails = linkTo(methodOn(InstructorController.class)
                                                                        .getInstructor(course.getInstructorUsername()))
                                                                        .withRel("Instructor details");
                                                                 return course.add(instructorDetails);
                                                            }).collect(Collectors.toList());


            return ResponseEntity.ok().body(courses);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@RequestBody CourseCreateDto courseDto,
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
    public ResponseEntity<?> updateCourse(@RequestParam("jsonRequest") String jsonRequest,
                                          @PathVariable Long id,
                                          @RequestBody MultipartFile courseImage){

        try {
            CourseCreateDto courseDto  = new ObjectMapper().readValue(jsonRequest, CourseCreateDto.class);
            log.info("course dto in course controller --> {}", courseDto);
            return ResponseEntity.ok().body(instructorService.updateCourse(courseDto, id, courseImage));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(path ="", consumes = {MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> createCourse(@RequestParam("jsonRequest") String jsonRequest, @RequestParam("file") MultipartFile file){
        try {
            CourseCreateDto courseDto  = new ObjectMapper().readValue(jsonRequest, CourseCreateDto.class);
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
    public ResponseEntity<?> getCourseById(@PathVariable("id") Long courseId){
        try {

            CourseDetailsDto course = courseService.getCourseById(courseId);
            Link allCourseLink = linkTo(methodOn(CourseController.class).findAll()).withRel("All courses");
            Link instructorLink = linkTo(methodOn(InstructorController.class)
                                        .getInstructor(course.getInstructorUsername()))
                                        .withRel("Instructor");

            course.add(allCourseLink);
            course.add(instructorLink);

            return ResponseEntity.ok(course);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }




}
