//package com.comepethome.user_query.scheduler;
//
//import com.comepethome.user_query.controller.UserController;
//import com.comepethome.user_query.controller.request.UserJoinRequest;
//import com.comepethome.user_query.controller.request.UserProfileUpdateRequest;
//import com.comepethome.user_query.controller.response.UserStatusResponse;
//import com.comepethome.user_query.exception.CustomException;
//import com.comepethome.user_query.exception.user.UserAlreadyExistException;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.*;
//import org.springframework.scheduling.annotation.EnableAsync;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Objects;
//import java.util.Optional;
//
//@Slf4j
//@Component
//@EnableAsync
//@EnableScheduling
//public class Schedule {
//
//    @Value("${test.id}")
//    private String testId;
//
//    @Value("${test.pw}")
//    private String testPw;
//
//    @Value("${test.server-ip}")
//    private String serverIp;
//
//    @Value("${test.port}")
//    private String port;
//
//    @Autowired
//    private UserController userController;
//
//    RestTemplate restTemplate = new RestTemplate();
//
//    @Scheduled(fixedDelay = 1000)
//    public void join() throws InterruptedException {
//        log.info("join update delete scheduled start - {}", System.currentTimeMillis() / 1000);
//
//        try {
//            userController.join(new UserJoinRequest(testId, testPw, "t1", "t1", "1"));
//        }catch(UserAlreadyExistException ignored){
//            log.info("warm up 중 userId 가 존재하여 가입불가,무시하고 패스 - {}", System.currentTimeMillis() / 1000);
//        }
//        request();
//        userController.update(new UserProfileUpdateRequest("t","","t","0"), testId);
//        userController.delete(testId);
//        log.info("join update delete scheduled finished - {}", System.currentTimeMillis() / 1000);
//        Thread.sleep(5000);
//    }
//
//    public void joinRequest(String uri){
//        String userId = testId;
//        String password = testPw;
//        String url =  createUrl(uri);
//
//        UserJoinRequest userJoinRequest = new UserJoinRequest(testId, testPw,"t", "t", "0");
//
//        HttpEntity<UserJoinRequest> requestHttpEntity = new HttpEntity<>(userJoinRequest);
//
//        ResponseEntity<UserStatusResponse> responseEntity = restTemplate.exchange(
//                url,
//                HttpMethod.POST,
//                requestHttpEntity,
//                UserStatusResponse.class
//        );
//    }
//
//    public void updateRequest(String uri){
//        String userId = testId;
//        String url =  createUrl(uri);
//
//        UserProfileUpdateRequest userProfileUpdateRequest = new UserProfileUpdateRequest("t", "t", "t", "0");
//
//        HttpEntity<UserProfileUpdateRequest> requestHttpEntity = new HttpEntity<>(userProfileUpdateRequest, createHeaderIncludeUserId());
//
//        Optional<ResponseEntity<UserStatusResponse>> responseEntity = requestRestful(url, requestHttpEntity);
//
//        responseEntity
//                .filter(item-> Objects.requireNonNull(item.getBody()).getCode() == 200);
//
//    }
//
//    private Optional<ResponseEntity<UserStatusResponse>> requestRestful(String url, HttpEntity<?> requestHttpEntity){
//        return Optional.of(restTemplate.exchange(
//                url,
//                HttpMethod.POST,
//                requestHttpEntity,
//                UserStatusResponse.class
//        ));
//    }
//
//
//    private String createUrl(String uri){
//        return new StringBuilder().append("http://")
//                .append(serverIp)
//                .append(":")
//                .append(port)
//                .append(uri).toString();
//    }
//
//    private HttpHeaders createHeaderIncludeUserId(){
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("userId", testId);
//        return headers;
//    }
//}
