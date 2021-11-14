package com.ileiwe.data.model.dto;


import com.ileiwe.data.model.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstructorDetailsDto extends RepresentationModel<InstructorDetailsDto> {

    private Long id;
    private String firstName;
    private String lastName;
    private Gender gender;
    private String specialization;
    private String bio;
    private String username;
    private boolean isEnabled;
}
