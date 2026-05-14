package com.mamokey.yeoun.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "로그인 응답")
public record LoginResponse(
        String accessToken,
        String refreshToken
) {
}
