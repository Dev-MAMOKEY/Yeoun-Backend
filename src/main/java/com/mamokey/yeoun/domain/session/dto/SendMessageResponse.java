package com.mamokey.yeoun.domain.session.dto;

import java.util.UUID;

public record SendMessageResponse(UUID messageId, String text) {}
