package com.ileiwe.data.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseDto {


    private String title;
    private String description;
    private String duration;
    private String language;
    private String instructorUsername;
    private boolean isPublished;
}
