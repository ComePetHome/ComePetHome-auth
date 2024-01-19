package com.comepethome.user_query.controller.response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
public class UserStatusResponse {
    private String message;
    private HttpStatus httpStatus;
    private String time;
    private int code;
}
