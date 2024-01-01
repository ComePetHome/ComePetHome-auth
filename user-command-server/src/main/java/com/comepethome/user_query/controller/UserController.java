package com.comepethome.user_query.controller;

import com.comepethome.user_query.controller.request.UserJoinRequest;
import com.comepethome.user_query.dto.UserDTO;
import com.comepethome.user_query.exception.ResponseMessage;
import com.comepethome.user_query.service.UserService;
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

    @PostMapping("/test")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("test");
    }
}