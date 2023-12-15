package com.project.compethome.controller;

import com.project.compethome.controller.request.UsersJoinRequest;
import com.project.compethome.controller.request.UsersLoginRequest;
import com.project.compethome.dto.UsersDTO;
import com.project.compethome.exception.ResponseMessage;
import com.project.compethome.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody UsersJoinRequest request){
        usersService.save(UsersDTO.translate(request));
        return ResponseEntity.ok(ResponseMessage.USER_JOIN_SUCCESS);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UsersLoginRequest request){
        usersService.login(UsersDTO.translate(request));
        return ResponseEntity.ok(ResponseMessage.USER_LOGIN_SUCCESS);
    }
}
