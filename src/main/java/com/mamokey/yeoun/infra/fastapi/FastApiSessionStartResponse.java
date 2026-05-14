package com.mamokey.yeoun.infra.fastapi;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record FastApiSessionStartResponse(
        @JsonProperty("session_id") UUID sessionId
) {}
