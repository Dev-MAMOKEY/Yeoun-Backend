package com.mamokey.yeoun.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "계정 삭제 요청")
public record DeleteUserRequest(
        @NotBlank(message = "비밀번호는 필수입니다.")
        String password
) {
}
