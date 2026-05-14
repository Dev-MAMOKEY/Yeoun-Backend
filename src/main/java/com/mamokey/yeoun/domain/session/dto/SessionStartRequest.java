package com.mamokey.yeoun.domain.session.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Schema(description = "대화 세션 시작 요청")
public record SessionStartRequest(
        @NotNull UUID personaId
) {}
