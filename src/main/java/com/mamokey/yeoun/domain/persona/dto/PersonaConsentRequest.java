package com.mamokey.yeoun.domain.persona.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;

@Schema(description = "페르소나 생성 전 약관 및 윤리 동의 요청")
public record PersonaConsentRequest(
        @Schema(description = "동의 약관 버전", example = "1")
        @NotNull
        Integer consentVersion,

        @Schema(description = "고인의 디지털 추모 거부 의사 질문 답변 여부. 예=true, 아니오=false", example = "false")
        @NotNull
        Boolean declinedIntentAnswered,

        @Schema(description = "서비스 이용약관 및 개인정보처리방침 동의 여부", example = "true")
        @NotNull(message = "약관에 동의해야 진행할 수 있습니다")
        @AssertTrue(message = "약관에 동의해야 진행할 수 있습니다")
        Boolean termsAgreed
) {
}
