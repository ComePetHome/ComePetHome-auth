package com.comepethome.user_command.service;

import com.comepethome.user_command.exception.email.SendMailException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender emailSender;

    public void sendMail(String toEmail, String title, String text){
        SimpleMailMessage simpleMailMessage = createEmailForm(toEmail, title, text);

        try{
            //emailSender.send(simpleMailMessage);
        } catch (RuntimeException e){
            log.debug("Mail send exception toEmail: {}, titile: {}, text: {}", toEmail, title, text);
            throw new SendMailException();
        }

    }

    private SimpleMailMessage createEmailForm(String toEmail, String title, String text){

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(title);
        message.setText(text);

        return message;
    }


}
