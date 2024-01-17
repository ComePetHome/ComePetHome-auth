package com.comepethome.user_query.scheduler;

import com.comepethome.user_query.controller.UserController;
import com.comepethome.user_query.controller.request.UserJoinRequest;
import com.comepethome.user_query.controller.request.UserProfileUpdateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
@Slf4j
@Component
@EnableAsync
@EnableScheduling
public class Schedule {

    @Value("${test.id}")
    private String testId;

    @Value("${test.pw}")
    private String testPw;

    @Autowired
    private UserController userController;
    @Scheduled(fixedDelay = 1000)
    public void join() throws InterruptedException {
        log.info("join update delete scheduled start - {}", System.currentTimeMillis() / 1000);
        userController.join(new UserJoinRequest(testId, testPw, "t1", "t1", "1"));
        userController.update(new UserProfileUpdateRequest("t","","t","0"), testId);
        userController.delete(testId);
        log.info("join update delete scheduled finished - {}", System.currentTimeMillis() / 1000);
        Thread.sleep(5000);
    }
}
