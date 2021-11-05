package com.ileiwe.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @NotNull
    @Column(nullable = false)
    private String firstName;
    @NotBlank
    @NotNull
    @Column(nullable = false)
    private String lastName;
    private LocalDate dob;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @OneToOne(cascade = CascadeType.PERSIST)
    private LearningParty learningParty;
    @ManyToMany
    private List<Course> courses;

    public void addCourse(Course course) {
        if(courses == null){
            courses = new ArrayList<>();
        }
        courses.add(course);
    }
}
