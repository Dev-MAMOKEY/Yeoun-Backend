package com.mamokey.yeoun.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "회원가입 응답")
public record SignUpResponse(
        UUID id,
        String email
) {
}
