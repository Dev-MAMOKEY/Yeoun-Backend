package com.mamokey.yeoun.domain.session.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record SessionStartRequest(
        @NotNull UUID personaId
) {}
