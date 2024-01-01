package com.comepethome.user_commend.exception.user;


public class CustomException extends RuntimeException{
    private int code;
    private String message;
    public CustomException(int code, String message){
        super(String.valueOf(code) + message);
        this.code = code;
        this.message = message;
    }

}
