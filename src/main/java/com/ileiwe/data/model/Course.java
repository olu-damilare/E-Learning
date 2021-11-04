package com.ileiwe.data.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @CreationTimestamp
    private LocalDate dateCreated;
    private LocalDate datePublished;
    @UpdateTimestamp
    private LocalDate updatedAt;
    private boolean isPublished;
    private String duration;
    @Column(length = 1000)
    private String description;
    @ElementCollection
    private List<String> imageUrls;
    private String language;
    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Instructor instructor;
    @ManyToMany
    private List<Student> students;
}
