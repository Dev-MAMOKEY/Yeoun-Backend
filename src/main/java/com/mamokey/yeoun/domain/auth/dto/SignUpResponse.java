package com.mamokey.yeoun.domain.auth.dto;

import java.util.UUID;

public record SignUpResponse(
        UUID id,
        String email
) {
}
