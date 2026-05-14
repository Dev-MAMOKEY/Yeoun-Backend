package com.mamokey.yeoun.domain.persona.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "페르소나 생성 요청")
public record CreatePersonaRequest(
        @NotBlank String name,
        @NotBlank String nickname
) {
}
