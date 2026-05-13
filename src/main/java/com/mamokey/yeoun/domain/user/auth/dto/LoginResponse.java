package com.mamokey.yeoun.domain.user.auth.dto;

public record LoginResponse(
        String accessToken,
        String refreshToken
) {
}
