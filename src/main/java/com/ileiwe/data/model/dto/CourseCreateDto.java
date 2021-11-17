package com.ileiwe.data.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseCreateDto {


    private String title;
    private String description;
    private String duration;
    private String language;
    private String instructorUsername;
    private boolean isPublished;
    private List<String> imageUpdateUrls;

    public void addImageUrl(String imageUrl){
        if(imageUpdateUrls == null){
            imageUpdateUrls = new ArrayList<>();
        }
        imageUpdateUrls.add(imageUrl);
    }
}
