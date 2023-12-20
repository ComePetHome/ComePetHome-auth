package com.project.compethome.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class TokenDTO {
    String accessToken;
    String refreshToken;
}
