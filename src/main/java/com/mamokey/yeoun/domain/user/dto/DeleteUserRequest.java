package com.mamokey.yeoun.domain.user.dto;

import jakarta.validation.constraints.NotBlank;

public record DeleteUserRequest(
        @NotBlank(message = "비밀번호는 필수입니다.")
        String password
) {
}
