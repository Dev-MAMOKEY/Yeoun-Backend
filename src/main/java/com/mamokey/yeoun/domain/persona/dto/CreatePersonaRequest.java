package com.mamokey.yeoun.domain.persona.dto;

import jakarta.validation.constraints.NotBlank;

public record CreatePersonaRequest(
        @NotBlank String name,
        @NotBlank String nickname
) {
}
