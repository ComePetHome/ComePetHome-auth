package com.comepethome.user_query.scheduler;

import com.comepethome.user_query.controller.UserController;
import com.comepethome.user_query.controller.request.UserJoinRequest;
import com.comepethome.user_query.controller.request.UserProfileUpdateRequest;
import com.comepethome.user_query.exception.user.UserAlreadyExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@EnableAsync
@EnableScheduling
public class Schedule {

    @Value("${test.id}")
    private String testId;

    @Value("${test.pw}")
    private String testPw;

    @Value("${test.server-ip}")
    private String serverIp;

    @Value("${test.port}")
    private String port;

    @Autowired
    private UserController userController;

    @Scheduled(fixedDelay = 1000)
    public void join() throws InterruptedException {
        log.info("join update delete scheduled start - {}", System.currentTimeMillis() / 1000);

        try {
            userController.join(new UserJoinRequest(testId, testPw, "t1", "t1", "1"));
        }catch(UserAlreadyExistException ignored){
            log.info("warm up 중 userId 가 존재하여 가입불가,무시하고 패스 - {}", System.currentTimeMillis() / 1000);
        }
        login();
        userController.update(new UserProfileUpdateRequest("t","","t","0"), testId);
        userController.delete(testId);
        log.info("join update delete scheduled finished - {}", System.currentTimeMillis() / 1000);
        Thread.sleep(5000);
    }

    public void login(){
        String userId = testId;
        String password = testPw;
        String loginUrl = new StringBuilder().append("http://")
                             .append(serverIp)
                             .append(":")
                             .append(port)
                             .append("/api/user/command/login").toString();

        Map<String, String> loginData = new HashMap<>();
        loginData.put("userId", userId);
        loginData.put("password", password);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(loginData, headers);

        log.info("login url {} - {}", loginUrl, System.currentTimeMillis() / 1000);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(loginUrl, requestEntity, String.class);

        if (responseEntity.getStatusCode().value() == 200) {
            log.info("login success - {}", System.currentTimeMillis() / 1000);
        } else {
            log.info("login fail - {}", System.currentTimeMillis() / 1000);
            log.info("login fail log {} - {}", responseEntity.getBody(),System.currentTimeMillis() / 1000);
        }
    }
}
