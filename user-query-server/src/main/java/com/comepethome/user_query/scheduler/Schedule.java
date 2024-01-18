package com.comepethome.user_query.scheduler;

import com.comepethome.user_query.controller.UserController;
import com.comepethome.user_query.controller.request.UserFindIdRequest;
import com.comepethome.user_query.controller.request.UserJoinRequest;
import com.comepethome.user_query.controller.request.UserLoginRequest;
import com.comepethome.user_query.controller.response.UserIdResponse;
import com.comepethome.user_query.controller.response.UserProfileResponse;
import com.comepethome.user_query.controller.response.UserStatusResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
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

    RestTemplate restTemplate = new RestTemplateBuilder().build();

    @Scheduled(fixedDelay = 3 * 60 * 1000)
    public void join() throws InterruptedException {

        try {
            joinRequest("/api/user/command/join");
            String accessToken = loginRequest("/api/user/command/login");
            findUserIdRequest("/api/user/query/findUserId");
            profileRequest("/api/user/query/profile", accessToken);
            availableUserIdRequest("/api/user/query/availableUserId");
            logoutRequest("/api/user/query/logout", accessToken);
        }catch (Exception e){
            log.info("Exception {} - {}",e.getMessage(), System.currentTimeMillis() / 1000);
        }

        Thread.sleep(5000);
    }

    public void joinRequest(String uri){
        String url =  createUrl(uri);

        log.info("join request start - {}", System.currentTimeMillis() / 1000);
        UserJoinRequest userJoinRequest = new UserJoinRequest(testId + "1" , testPw,"t", "t", "1");


        HttpHeaders httpHeaders = new HttpHeaders();
        includeContentType(httpHeaders);
        includeAcceptType(httpHeaders);
        includeAcceptEncodingType(httpHeaders);

        HttpEntity<UserJoinRequest> requestHttpEntity = new HttpEntity<>(userJoinRequest, httpHeaders);

        ResponseEntity<?> responseEntity = requestRestful(url, requestHttpEntity, HttpMethod.POST);

        logResponseCode(responseEntity);

        log.info("join request end - {}", System.currentTimeMillis() / 1000);
    }

    public String loginRequest(String uri){
        String url =  createUrl(uri);

        log.info("login request start - {}", System.currentTimeMillis() / 1000);
        UserLoginRequest userLoginRequest = new UserLoginRequest(testId, testPw);

        HttpHeaders httpHeaders = new HttpHeaders();
        includeContentType(httpHeaders);
        includeAcceptType(httpHeaders);
        includeAcceptEncodingType(httpHeaders);

        HttpEntity<UserLoginRequest> requestHttpEntity = new HttpEntity<>(userLoginRequest, httpHeaders);

        ResponseEntity<?> responseEntity = requestRestful(url, requestHttpEntity, HttpMethod.POST);

        logResponseCode(responseEntity);

        log.info("login request end - {}", System.currentTimeMillis() / 1000);

        return Objects.requireNonNull(responseEntity.getHeaders().get("access-token")).get(0);
    }

    public void findUserIdRequest(String uri){
        String url =  createUrl(uri);

        log.info("findUserId request start - {}", System.currentTimeMillis() / 1000);
        UserFindIdRequest userFindIdRequest = new UserFindIdRequest("t", "0");

        HttpHeaders httpHeaders = new HttpHeaders();
        includeAcceptEncodingType(httpHeaders);
        includeContentType(httpHeaders);
        includeAcceptType(httpHeaders);

        HttpEntity<UserFindIdRequest> requestHttpEntity = new HttpEntity<>(userFindIdRequest, httpHeaders);

        ResponseEntity<?> responseEntity = requestRestful(url, requestHttpEntity, HttpMethod.POST);

        logResponseCode(responseEntity);
        log.info("findUserId request end - {}", System.currentTimeMillis() / 1000);
    }

    public void profileRequest(String uri, String accessToken){
        String url =  createUrl(uri);

        log.info("profile request start - {}", System.currentTimeMillis() / 1000);
        HttpHeaders httpHeaders = new HttpHeaders();
        includeAccessToken(httpHeaders, accessToken);
        includeContentType(httpHeaders);
        includeAcceptType(httpHeaders);
        includeAcceptEncodingType(httpHeaders);

        HttpEntity<String> requestHttpEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<?> responseEntity = requestRestful(url, requestHttpEntity, HttpMethod.GET);

        logResponseCode(responseEntity);
        log.info("profile request end - {}", System.currentTimeMillis() / 1000);
    }

    public void availableUserIdRequest(String uri){
        String url =  createUrl(uri);

        log.info("availableUserId request start - {}", System.currentTimeMillis() / 1000);
        HttpHeaders httpHeaders = new HttpHeaders();
        includeContentType(httpHeaders);
        includeAcceptType(httpHeaders);
        includeAcceptEncodingType(httpHeaders);

        HttpEntity<String> requestHttpEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<?> responseEntity = requestRestful(url, requestHttpEntity, HttpMethod.GET);

        logResponseCode(responseEntity);
        log.info("availableUserId request request end - {}", System.currentTimeMillis() / 1000);
    }
    public void logoutRequest(String uri, String accessToken){
        String url =  createUrl(uri);

        log.info("logout request start - {}", System.currentTimeMillis() / 1000);
        HttpHeaders httpHeaders = new HttpHeaders();
        includeAccessToken(httpHeaders, accessToken);
        includeContentType(httpHeaders);
        includeAcceptType(httpHeaders);
        includeAcceptEncodingType(httpHeaders);

        HttpEntity<String> requestHttpEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<?> responseEntity = requestRestful(url, requestHttpEntity, HttpMethod.GET);

        logResponseCode(responseEntity);
        log.info("logout request request end - {}", System.currentTimeMillis() / 1000);
    }

    private void logResponseCode(ResponseEntity<?> responseEntity){
        log.info("join finished state code {} - {}", responseEntity.getBody(), System.currentTimeMillis() / 1000);
    }

    private ResponseEntity<?> requestRestful(String url, HttpEntity<?> requestHttpEntity, HttpMethod method){
        return restTemplate.exchange(
                url,
                method,
                requestHttpEntity,
                UserStatusResponse.class
        );
    }

    private String createUrl(String uri){
        return "http://" + serverIp + ":9001" + uri;
    }

    private void includeUserId(HttpHeaders headers){
        headers.set("userId", testId);
    }

    private void includeAccessToken(HttpHeaders headers, String accessToken){
        headers.set("access-token", accessToken);
    }

    private void includeContentType(HttpHeaders headers){
        headers.set("Content-Type", "application/json");
    }

    private void includeAcceptType(HttpHeaders headers){
        headers.add("Accept", "application/json");
    }

    private void includeAcceptEncodingType(HttpHeaders headers){
        headers.add("Accept-Encoding", "gzip, deflate, br");
    }

}
