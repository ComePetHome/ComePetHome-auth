package com.project.compethome.controller;

import com.project.compethome.controller.request.UserJoinRequest;
import com.project.compethome.controller.request.UserLoginRequest;
import com.project.compethome.dto.UserDTO;
import com.project.compethome.exception.ResponseMessage;
import com.project.compethome.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody UserJoinRequest request){
        userService.save(UserDTO.translate(request));
        return ResponseEntity.ok(ResponseMessage.USER_JOIN_SUCCESS);
    }

//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody UserLoginRequest request){
//        userService.login(UserDTO.translate(request));
//        return ResponseEntity.ok(ResponseMessage.USER_LOGIN_SUCCESS);
//    }

    @PostMapping("/test")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("test");
    }
}
