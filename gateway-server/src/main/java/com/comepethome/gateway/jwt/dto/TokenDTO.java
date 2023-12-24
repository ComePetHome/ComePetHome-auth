package com.comepethome.gateway.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenDTO {
    String accessToken;
    String refreshToken;
}
