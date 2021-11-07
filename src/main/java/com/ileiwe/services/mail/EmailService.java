package com.ileiwe.services.mail;

import com.ileiwe.data.model.Mail;
import com.ileiwe.data.model.dto.InstructorPartyDto;
import com.ileiwe.data.model.dto.StudentPartyDto;

import javax.mail.MessagingException;

public interface EmailService {


        public void sendMail(StudentPartyDto studentPartyDto);
        public void sendMail(InstructorPartyDto instructorPartyDto);



}
