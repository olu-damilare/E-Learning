package com.ileiwe.data.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDetailsDto extends RepresentationModel<CourseDetailsDto> {

    private String title;
    private String description;
    private String duration;
    private String language;
    private String instructorUsername;
    private boolean isPublished;
    private List<String> imageUrls;
    private LocalDate datePublished;
    private LocalDate dateCreated;

}
