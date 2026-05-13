package com.mamokey.yeoun.domain.auth.dto;

public record LoginResponse(
        String accessToken,
        String refreshToken
) {
}
