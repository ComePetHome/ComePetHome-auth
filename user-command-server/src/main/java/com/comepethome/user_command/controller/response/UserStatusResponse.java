package com.comepethome.user_command.controller.response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserStatusResponse {
    private String message;
    private HttpStatus httpStatus;
    private String time;
    private int code;
}
