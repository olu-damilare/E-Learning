package com.ileiwe.services.mail;

import com.ileiwe.data.model.Mail;
import com.ileiwe.data.model.dto.InstructorPartyDto;
import com.ileiwe.data.model.dto.StudentPartyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;

//    public void sendSimpleMessage(
//            String to, String subject, String text) {
//
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("noreply@slimdaddy.com");
//        message.setTo(to);
//        message.setSubject(subject);
//        message.setText(text);
//        emailSender.send(message);
//
//    }

//    private final JavaMailSender javaMailSender;

//    public SendMailServiceImpl(JavaMailSender javaMailSender) {
//        this.javaMailSender = javaMailSender;
//    }

    public void sendMail(Mail mail) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(mail.getRecipient());
        msg.setFrom("no-reply@slimdaddy.com");

        msg.setSubject(Mail.subject);


        msg.setText(mail.getMailBody());

        emailSender.send(msg);
    }

    public void sendMailWithAttachments(Mail mail) throws MessagingException {
        MimeMessage msg = emailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(msg, true);

        helper.setTo("djolayemi@gmail.com");

        helper.setSubject("Testing from Spring Boot");

        helper.setText("Find the attached image", true);

        helper.addAttachment("hero.jpg", new ClassPathResource("hero.jpg"));

        emailSender.send(msg);
    }

    public void sendMail(InstructorPartyDto instructorPartyDto) {
        Mail mail = new Mail();
        mail.setRecipient(instructorPartyDto.getEmail());
        String mailBody = "Dear " + instructorPartyDto.getFirstName() +
                ",\n\n" + "Welcome to Slim-Daddy's E-Learning institute. Click the link below to activate your account.\n " +
                "https://slim-learning.herokuapp.com/api/instructor/"+ instructorPartyDto.getEmail();

        mail.setMailBody(mailBody);

        sendMail(mail);
    }

    public void sendMail(StudentPartyDto studentPartyDto) {
        Mail mail = new Mail();
        mail.setRecipient(studentPartyDto.getEmail());
        String mailBody = "Dear " + studentPartyDto.getFirstName() +
                ",\n\n" + "Welcome to Slim-Daddy's E-Learning institute. Click the link below to activate your account.\n " +
                "http://localhost:8081/api/student/"+ studentPartyDto.getEmail();

        mail.setMailBody(mailBody);

        sendMail(mail);
    }
}