package com.ileiwe.data.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mail {
    private String recipient;
    private String mailBody;
    public static final String subject = "E-Learning account activation";

}