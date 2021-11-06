package com.ileiwe.controller;


import com.ileiwe.data.model.Student;
import com.ileiwe.data.model.dto.StudentPartyDto;
import com.ileiwe.services.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student")
public class StudentController {


    @Autowired
    StudentService studentService;

    @PostMapping("/register")
    public ResponseEntity<?> registerAsStudent(@RequestBody StudentPartyDto studentPartyDto){
        try {
            return ResponseEntity.ok().body(studentService.registerStudent(studentPartyDto));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{username}/enroll")
    public ResponseEntity<?> enrollForCourse(@PathVariable("username") String username, @RequestParam Long courseId){
        try {
            Student student = studentService.findStudentByUsername(username);
            studentService.enroll(student.getId(), courseId);
            return ResponseEntity.noContent().build();
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{username}/unenroll")
    public ResponseEntity<?> unEnrollForCourse(@PathVariable("username") String username, @RequestParam Long courseId){
        try {
            Student student = studentService.findStudentByUsername(username);
            studentService.unEnroll(student.getId(), courseId);
            return ResponseEntity.noContent().build();
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{username}/courses")
    public ResponseEntity<?> findAll(@PathVariable("username") String username){
        try{
            return ResponseEntity.ok().body(studentService.findAllCoursesForStudent(username));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }






}
