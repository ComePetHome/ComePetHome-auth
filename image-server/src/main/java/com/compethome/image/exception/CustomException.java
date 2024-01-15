package com.compethome.image.exception;


import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{
    private final int code;
    private final String message;
    public CustomException(int code, String message){
        super(String.valueOf(code) + message);
        this.code = code;
        this.message = message;
    }
}
