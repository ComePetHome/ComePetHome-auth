package com.comepethome.user_query.exception.user;


import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{
    private int code;
    private String message;
    public CustomException(int code, String message){
        super(String.valueOf(code) + message);
        this.code = code;
        this.message = message;
    }

}
