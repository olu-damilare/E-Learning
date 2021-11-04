package com.ileiwe.data.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Instructor {

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
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String specialization;
    @Column(length = 1000)
    private String bio;
    @OneToOne(cascade = CascadeType.PERSIST)
    private LearningParty learningParty;
    @OneToMany(cascade = CascadeType.PERSIST)
    @ToString.Exclude
    private List<Course> courses;

    public void addCourse(Course course){
        if(this.courses == null){
            this.courses = new ArrayList<>();
        }
        this.courses.add(course);
    }
}
