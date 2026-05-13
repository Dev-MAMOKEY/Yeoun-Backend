package com.mamokey.yeoun.domain.persona.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record InterviewAnswerRequest(
        @Min(1) @Max(10) int questionNumber,
        String answer
) {
}
