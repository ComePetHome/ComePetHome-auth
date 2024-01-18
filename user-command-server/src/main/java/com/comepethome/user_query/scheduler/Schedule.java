package com.comepethome.user_query.scheduler;

import com.comepethome.user_query.controller.UserController;
import com.comepethome.user_query.controller.request.UserJoinRequest;
import com.comepethome.user_query.controller.request.UserLoginRequest;
import com.comepethome.user_query.controller.request.UserProfileUpdateRequest;
import com.comepethome.user_query.controller.response.UserStatusResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

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

    private String accessToken;

    @Autowired
    private UserController userController;

    RestTemplate restTemplate = new RestTemplate();

    @Scheduled(fixedDelay = 5 * 60 * 1000)
    public void join() throws InterruptedException {
        log.info("join update delete scheduled start - {}", System.currentTimeMillis() / 1000);

        try {
            joinRequest("/api/user/command/join");
            loginRequest("/api/user/command/login");
            updateRequest("/api/user/command/profile");
            updateRequest("/api/user/command/withdraw");
        }catch (Exception e){
            log.info("Exception {} - {}",e.getMessage(), System.currentTimeMillis() / 1000);
        }

        log.info("join update delete scheduled finished - {}", System.currentTimeMillis() / 1000);
        Thread.sleep(5000);
    }

    public void joinRequest(String uri){
        String url =  createUrl(uri);

        log.info("join url - {}", url);

        if(testId.isEmpty()){
            log.info("join testId empty - {}", System.currentTimeMillis() / 1000);
        }

        if(testPw.isEmpty()){
            log.info("join testPw empty - {}", System.currentTimeMillis() / 1000);
        }

        UserJoinRequest userJoinRequest = new UserJoinRequest(testId, testPw,"t", "t", "000");

        HttpHeaders httpHeaders = new HttpHeaders();
        createContentTypeIncludeUserId(httpHeaders);
        createAcceptTypeIncludeUserId(httpHeaders);
        createAcceptEncodingTypeIncludeUserId(httpHeaders);

        HttpEntity<UserJoinRequest> requestHttpEntity = new HttpEntity<>(userJoinRequest, httpHeaders);

        log.info("join request start");
        ResponseEntity<UserStatusResponse> responseEntity = requestRestful(url, requestHttpEntity, HttpMethod.POST);

        log.info("join request end");
        logResponseCode(responseEntity);
    }

    public void loginRequest(String uri){
        String url =  createUrl(uri);

        UserLoginRequest userLoginRequest = new UserLoginRequest(testId, testPw);

        HttpHeaders httpHeaders = new HttpHeaders();
        createContentTypeIncludeUserId(httpHeaders);
        createAcceptTypeIncludeUserId(httpHeaders);
        createAcceptEncodingTypeIncludeUserId(httpHeaders);

        HttpEntity<UserLoginRequest> requestHttpEntity = new HttpEntity<>(userLoginRequest, httpHeaders);

        ResponseEntity<UserStatusResponse> responseEntity = requestRestful(url, requestHttpEntity, HttpMethod.POST);

        accessToken = Objects.requireNonNull(responseEntity.getHeaders().get("access-toke")).get(0);

        logResponseCode(responseEntity);
    }

    public void updateRequest(String uri){
        String userId = testId;
        String url =  createUrl(uri);

        UserProfileUpdateRequest userProfileUpdateRequest = new UserProfileUpdateRequest("t", "t", "t", "0");

        HttpHeaders httpHeaders = new HttpHeaders();
        createHeaderIncludeUserId(httpHeaders);
        createAcceptTypeIncludeUserId(httpHeaders);
        createAcceptEncodingTypeIncludeUserId(httpHeaders);
        createAccessTokenIncludeUserId(httpHeaders);
        createContentTypeIncludeUserId(httpHeaders);

        HttpEntity<UserProfileUpdateRequest> requestHttpEntity = new HttpEntity<>(userProfileUpdateRequest, httpHeaders);

        ResponseEntity<UserStatusResponse> responseEntity = requestRestful(url, requestHttpEntity, HttpMethod.PATCH);

        logResponseCode(responseEntity);
    }

    public void deleteRequest(String uri){
        String url =  createUrl(uri);

        HttpHeaders httpHeaders = new HttpHeaders();
        createHeaderIncludeUserId(httpHeaders);
        createContentTypeIncludeUserId(httpHeaders);
        createAcceptTypeIncludeUserId(httpHeaders);
        createAcceptEncodingTypeIncludeUserId(httpHeaders);

        HttpEntity<String> requestHttpEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<UserStatusResponse> responseEntity = requestRestful(url, requestHttpEntity, HttpMethod.DELETE);

        logResponseCode(responseEntity);
    }

    private void logResponseCode(ResponseEntity<?> responseEntity){
        log.info("join finished state code {} - {}", responseEntity.getBody(), System.currentTimeMillis() / 1000);
    }

    private ResponseEntity<UserStatusResponse> requestRestful(String url, HttpEntity<?> requestHttpEntity, HttpMethod method){
        return restTemplate.exchange(
                url,
                method,
                requestHttpEntity,
                UserStatusResponse.class
        );
    }

    private String createUrl(String uri){
        return "http://" + serverIp + ":" + port + uri;
    }

    private void createHeaderIncludeUserId(HttpHeaders headers){
        headers.set("userId", testId);
    }

    private void createAccessTokenIncludeUserId(HttpHeaders headers){
        headers.set("access-token", accessToken);
    }

    private void createContentTypeIncludeUserId(HttpHeaders headers){
        headers.set("Content-Type", "application/json");
    }

    private void createAcceptTypeIncludeUserId(HttpHeaders headers){
        headers.add("Accept", "application/json");
    }

    private void createAcceptEncodingTypeIncludeUserId(HttpHeaders headers){
        headers.add("Accept-Encoding", "application/json");
    }

}
