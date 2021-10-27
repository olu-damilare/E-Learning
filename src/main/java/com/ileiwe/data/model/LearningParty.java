package com.ileiwe.data.model;

import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


@Data
@Entity
public class LearningParty {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    private boolean enabled;
    @CreationTimestamp
    private LocalDate dateCreated;
    @OneToMany
    private List<Authority> authorities;
}
