package com.mamokey.yeoun.domain.persona.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Schema(description = "인터뷰 답변 요청")
public record InterviewAnswerRequest(
        @Min(1) @Max(10) int questionNumber,
        String answer
) {
}
