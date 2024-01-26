package com.comepethome.user_command.service;

import com.comepethome.user_command.controller.request.EmailCodeRequest;
import com.comepethome.user_command.dto.EmailCodeDTO;
import com.comepethome.user_command.entity.EmailCode;
import com.comepethome.user_command.entity.User;
import com.comepethome.user_command.exception.email.CreateCodeException;
import com.comepethome.user_command.exception.email.EmailCodeNotMatchException;
import com.comepethome.user_command.exception.email.SendMailException;
import com.comepethome.user_command.exception.user.UserNotExistException;
import com.comepethome.user_command.repository.EmailCodeRepository;
import com.comepethome.user_command.repository.UserRepository;
import jakarta.ws.rs.NotAuthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Optional;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {
    @Autowired
    private final JavaMailSender emailSender;

    @Autowired
    private final EmailCodeRepository emailCodeRepository;

    @Autowired
    private final UserRepository userRepository;

    private static final String AUTH_CODE = "ComePetHome auth-code";

    public void sendCodeEmail(String userId){
        Optional<EmailCode> emailCode = Optional.ofNullable(emailCodeRepository.findByUserId(userId));

        emailCode.ifPresent(emailCodeRepository::delete);

        try {
            EmailCode entity = new EmailCode( null, userId, createCode());
            emailCodeRepository.save(entity);
            sendMail(userId, AUTH_CODE, entity.getEmailCode());
        } catch (RuntimeException e) {
            throw new SendMailException();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private String createCode() throws NoSuchAlgorithmException {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            Random rand = SecureRandom.getInstanceStrong();
            for(int i = 0; i < 6; i++){
                stringBuilder.append(rand.nextInt(10));
            }
        }catch (NoSuchAlgorithmException e){
            throw new CreateCodeException();
        }

        return stringBuilder.toString();
    }

    private void sendMail(String toEmail, String title, String text){
        SimpleMailMessage simpleMailMessage = createEmailForm(toEmail, title, text);

        try{
            emailSender.send(simpleMailMessage);
        } catch (RuntimeException e){
            log.debug("Mail send exception toEmail: {}, title: {}, text: {}", toEmail, title, text);
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

    public String verification(EmailCodeDTO emailCodeDTO) {
        Optional<EmailCode> emailCode = Optional.ofNullable(emailCodeRepository.findByUserId(emailCodeDTO.getUserId()));

        emailCode.filter(e -> e.getEmailCode().equals(emailCodeDTO.getEmailCode()))
                 .ifPresentOrElse(emailCodeRepository::delete, ()->{throw new EmailCodeNotMatchException();} );

        return emailCode.get().getUserId();
    }

    public String verificationUserId(EmailCodeDTO emailCodeDTO){
        Optional<User> user = Optional.ofNullable(userRepository.findByUserId(emailCodeDTO.getUserId()));

        if(user.isPresent()){
            return verification(emailCodeDTO);
        }

        throw new UserNotExistException();
    }
}
