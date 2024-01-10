package com.comepethome.user_query.controller;

import com.comepethome.user_query.controller.request.UserJoinRequest;
import com.comepethome.user_query.controller.response.UserProfileResponse;
import com.comepethome.user_query.controller.response.UserStatusResponse;
import com.comepethome.user_query.dto.UserDTO;
import com.comepethome.user_query.exception.ApiException;
import com.comepethome.user_query.exception.ApiExceptionHandler;
import com.comepethome.user_query.exception.SuccessResponseMessage;
import com.comepethome.user_query.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/join")
    public ResponseEntity<UserStatusResponse> join(@RequestBody @Valid UserJoinRequest request){
        userService.save(UserDTO.translate(request));
        return ResponseEntity.ok(new UserStatusResponse(
                        SuccessResponseMessage.USER_JOIN_SUCCESS.getMessage(),
                        HttpStatus.OK,
                        ApiExceptionHandler.getNowDateTime(),
                        SuccessResponseMessage.USER_JOIN_SUCCESS.getCode())
        );
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity<UserProfileResponse> profile(@PathVariable String userId){
        UserDTO userDTO = userService.getProfile(UserDTO.translate(userId));
        return ResponseEntity.ok(new UserProfileResponse(userDTO));
    }

    @PostMapping("/test")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("test");
    }
}
