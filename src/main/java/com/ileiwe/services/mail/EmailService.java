package com.ileiwe.services.mail;

import com.ileiwe.data.model.Mail;

import javax.mail.MessagingException;

public interface EmailService {


        void sendMail(Mail mail);

        void sendMailWithAttachments(Mail mail) throws MessagingException;


}
