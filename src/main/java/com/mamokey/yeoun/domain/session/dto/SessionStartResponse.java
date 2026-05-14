package com.mamokey.yeoun.domain.session.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "대화 세션 시작 응답")
public record SessionStartResponse(UUID sessionId) {}
