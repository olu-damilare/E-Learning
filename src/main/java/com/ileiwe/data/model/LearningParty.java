package com.ileiwe.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LearningParty {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull @NotEmpty @NotBlank
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    @NotNull @NotEmpty @NotBlank
    private String password;
    private boolean enabled;
    @CreationTimestamp
    private LocalDateTime dateCreated;
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Authority> authorities;

    public LearningParty(String email, String password, Authority authority){
        this.email = email;
        this.password = password;
        addAuthority ( authority );
        this.enabled = false;

    }
    public void addAuthority(Authority authority){
        if(this.authorities == null){
            this.authorities = new ArrayList<> ();
        }
        this.authorities.add ( authority );
    }

}
