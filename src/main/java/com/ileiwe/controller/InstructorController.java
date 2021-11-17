package com.ileiwe.controller;


import com.ileiwe.data.model.dto.InstructorDetailsDto;
import com.ileiwe.data.model.dto.InstructorPartyDto;
import com.ileiwe.services.instructor.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController()
@RequestMapping("/instructors")
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

    @PutMapping("/{id}")
    public ResponseEntity<?> enableInstructor(@PathVariable("id") Long instructorId){
        try {
            return ResponseEntity.ok().body(instructorService.enableInstructor(instructorId));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{username}/courses")
    public ResponseEntity<?> getInstructorCourses(@PathVariable("username") String username){
        try {
            return ResponseEntity.ok().body(instructorService.getInstructorCourses(username));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/{username}")
    public ResponseEntity<?> getInstructor(@PathVariable("username") String username) {
        try {
            InstructorDetailsDto instructorDetailsDto = instructorService.findByUsername(username);
            Link allInstructors = linkTo(methodOn(InstructorController.class).getAllInstructors()).withRel("All Instructors");
            Link instructorCourses = linkTo(methodOn(InstructorController.class).getInstructorCourses(username)).withRel("Courses");

            instructorDetailsDto.add(allInstructors);
            instructorDetailsDto.add(instructorCourses);

            return ResponseEntity.ok().body(instructorDetailsDto);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getAllInstructors() {
        try {
            List<InstructorDetailsDto> instructors = instructorService.getAllInstructors().stream()
                                                .peek(instructor -> {
                                                    Link instructorCourses = linkTo(methodOn(InstructorController.class)
                                                                    .getInstructorCourses(instructor.getUsername()))
                                                                    .withRel("Courses");

                                                    instructor.add(instructorCourses);
                                                })
                                                .collect(Collectors.toList());

            return ResponseEntity.ok().body(instructors);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
