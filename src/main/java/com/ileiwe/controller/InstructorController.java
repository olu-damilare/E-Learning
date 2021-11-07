package com.ileiwe.controller;


import com.ileiwe.data.model.dto.InstructorPartyDto;
import com.ileiwe.services.instructor.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{username}")
    public ResponseEntity<?> enableInstructor(@PathVariable("username") String username){
        try {
            return ResponseEntity.ok().body(instructorService.enableInstructor(username));
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






}
