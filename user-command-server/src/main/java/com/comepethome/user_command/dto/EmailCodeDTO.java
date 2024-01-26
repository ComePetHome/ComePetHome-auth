package com.comepethome.user_command.dto;

import com.comepethome.user_command.controller.request.EmailCodeRequest;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmailCodeDTO {

    @Column
    private String userId;

    @Column
    private String emailCode;


    public static EmailCodeDTO translate(EmailCodeRequest request){
        return new EmailCodeDTO(request.getUserId(), request.getCode());
    }
}
