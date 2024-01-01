package com.comepethome.user.controller;

import com.comepethome.user.controller.request.UserFindIdRequest;
import com.comepethome.user.controller.response.UserIdResponse;
import com.comepethome.user.dto.UserDTO;
import com.comepethome.user.service.UserService;
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

    @PostMapping("/findUserId")
    public ResponseEntity<UserIdResponse> findUserId(@RequestBody UserFindIdRequest request){
        String userId = userService.findUserIdByNameAndPhoneNumber(UserDTO.translate(request));
        return ResponseEntity.ok(new UserIdResponse(userId));
    }

    @PostMapping("/test")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("test");
    }
}
