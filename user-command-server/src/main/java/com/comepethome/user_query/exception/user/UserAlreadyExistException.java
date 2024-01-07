package com.comepethome.user_query.exception.user;

public class UserAlreadyExistException extends CustomException{
    public UserAlreadyExistException() {
        super(150, "사용자가 이미 있습니다.");
    }
}
