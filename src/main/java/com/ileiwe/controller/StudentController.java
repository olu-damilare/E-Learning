package com.ileiwe.controller;


import com.ileiwe.data.model.dto.StudentPartyDto;
import com.ileiwe.services.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
