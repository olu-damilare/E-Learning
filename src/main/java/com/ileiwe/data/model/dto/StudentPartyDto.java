package com.ileiwe.data.model.dto;

import com.ileiwe.data.model.Gender;
import lombok.Data;

@Data
public class StudentPartyDto {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Integer yearOfBirth;
    private Integer monthOfBirth;
    private Integer dayOfBirth;
    private Gender gender;

}

