package com.comepethome.user_query.controller;

import com.comepethome.user_query.controller.request.UserJoinRequest;
import com.comepethome.user_query.controller.request.UserProfileUpdateRequest;
import com.comepethome.user_query.controller.response.UserStatusResponse;
import com.comepethome.user_query.dto.UserDTO;
import com.comepethome.user_query.exception.ApiExceptionHandler;
import com.comepethome.user_query.exception.SuccessResponseMessage;
import com.comepethome.user_query.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/api/user/command")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/join")
    public ResponseEntity<UserStatusResponse> join(@RequestBody @Valid UserJoinRequest request){
        log.info("join controller position - {}", System.currentTimeMillis() / 1000);
        userService.save(UserDTO.translate(request));
        return ResponseEntity.ok(new UserStatusResponse(
                        SuccessResponseMessage.USER_JOIN_SUCCESS.getMessage(),
                        HttpStatus.OK,
                        ApiExceptionHandler.getNowDateTime(),
                        SuccessResponseMessage.USER_JOIN_SUCCESS.getCode())
        );
    }

    @PatchMapping("/profile")
    public ResponseEntity<UserStatusResponse> update(@RequestBody UserProfileUpdateRequest userProfileUpdateRequest, @RequestHeader("userId") String userId){
        userService.update(UserDTO.translate(userProfileUpdateRequest, userId));
        return ResponseEntity.ok(new UserStatusResponse(
                SuccessResponseMessage.USER_PATCH_SUCCESS.getMessage(),
                HttpStatus.OK,
                ApiExceptionHandler.getNowDateTime(),
                SuccessResponseMessage.USER_PATCH_SUCCESS.getCode())
        );
    }

    @DeleteMapping("/withdraw")
    public ResponseEntity<UserStatusResponse> delete(@RequestHeader("userId") String userId){
        userService.delete(UserDTO.translate(userId));
        return ResponseEntity.ok(new UserStatusResponse(
                SuccessResponseMessage.USER_DELETE_SUCCESS.getMessage(),
                HttpStatus.OK,
                ApiExceptionHandler.getNowDateTime(),
                SuccessResponseMessage.USER_DELETE_SUCCESS.getCode())
        );
    }
}
