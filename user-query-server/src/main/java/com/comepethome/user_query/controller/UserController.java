package com.comepethome.user_query.controller;

import com.comepethome.user_query.controller.request.UserFindIdRequest;
import com.comepethome.user_query.controller.response.UserIdResponse;
import com.comepethome.user_query.controller.response.UserProfileResponse;
import com.comepethome.user_query.dto.UserDTO;
import com.comepethome.user_query.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/query")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/findUserId")
    public ResponseEntity<UserIdResponse> findUserId(@RequestBody UserFindIdRequest request){
        String userId = userService.findUserIdByNameAndPhoneNumber(UserDTO.translate(request));
        return ResponseEntity.ok(new UserIdResponse(userId));
    }

    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> getProfile(@RequestHeader("userId") String userId){
        UserDTO userDTO = userService.getProfile(UserDTO.translate(userId));
        return ResponseEntity.ok(new UserProfileResponse(userDTO));
    }

    @GetMapping("/availableUserId")
    public ResponseEntity<String> availableUserId(@RequestParam("userId") String userId){
        String availableUserId = userService.getAvailableUserId(UserDTO.translate(userId));
        return ResponseEntity.ok(availableUserId);
    }

    @PostMapping("/test")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("test");
    }
}
