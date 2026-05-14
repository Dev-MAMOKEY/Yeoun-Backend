package com.mamokey.yeoun.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Refresh Token 요청")
public record RefreshTokenRequest(
        @NotBlank(message = "리프레시 토큰은 필수입니다.")
        String refreshToken
) {
}
