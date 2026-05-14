package com.mamokey.yeoun.infra.fastapi;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record FastApiSendMessageResponse(
        @JsonProperty("message_id") UUID messageId,
        String text
) {}
