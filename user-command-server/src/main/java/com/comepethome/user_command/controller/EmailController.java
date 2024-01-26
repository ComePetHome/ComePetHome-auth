package com.comepethome.user_command.controller;

import com.comepethome.user_command.controller.request.EmailCodeRequest;
import com.comepethome.user_command.controller.response.UserStatusResponse;
import com.comepethome.user_command.dto.EmailCodeDTO;
import com.comepethome.user_command.exception.ApiExceptionHandler;
import com.comepethome.user_command.exception.SuccessResponseMessage;
import com.comepethome.user_command.service.EmailService;
import com.comepethome.user_command.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/email/verification")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/request")
    public ResponseEntity<UserStatusResponse> sendMessage(@RequestParam("userId") String userId){
        emailService.sendCodeEmail(userId);
        return ResponseEntity.ok( new UserStatusResponse(
                SuccessResponseMessage.MAIL_SEND_SUCCESS.getMessage(),
                HttpStatus.OK,
                ApiExceptionHandler.getNowDateTime(),
                SuccessResponseMessage.MAIL_SEND_SUCCESS.getCode()));
    }

    @PostMapping("/match-code")
    public ResponseEntity<UserStatusResponse> sendCode(@RequestBody EmailCodeRequest request){
        emailService.verification(EmailCodeDTO.translate(request));
        return ResponseEntity.ok( new UserStatusResponse(
                SuccessResponseMessage.MAIL_VERIFY_SUCCESS.getMessage(),
                HttpStatus.OK,
                ApiExceptionHandler.getNowDateTime(),
                SuccessResponseMessage.MAIL_VERIFY_SUCCESS.getCode()));
    }

    @PostMapping("/temp-token")
    public ResponseEntity<UserStatusResponse> getTempToken(@RequestBody EmailCodeRequest request, HttpServletResponse response){
        String userId = emailService.verificationUserId(EmailCodeDTO.translate(request));
        response.setHeader("userId", userId);

        return ResponseEntity.ok( new UserStatusResponse(
                SuccessResponseMessage.MAIL_VERIFY_SUCCESS.getMessage(),
                HttpStatus.OK,
                ApiExceptionHandler.getNowDateTime(),
                SuccessResponseMessage.MAIL_VERIFY_SUCCESS.getCode()));
    }
}
