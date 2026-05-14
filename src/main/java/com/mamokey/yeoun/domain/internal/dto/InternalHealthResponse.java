package com.mamokey.yeoun.domain.internal.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "서비스 상태 응답")
public record InternalHealthResponse(
        @Schema(description = "서비스 상태", example = "ok")
        String status,

        @Schema(description = "서비스 버전", example = "0.1.0")
        String version,

        @Schema(description = "서버 실행 시간(초)", example = "120")
        long uptimeSeconds,

        @Schema(description = "모델 로드 상태")
        ModelStatus models,

        @Schema(description = "활성 세션 수", example = "0")
        int sessions,

        @Schema(description = "GPU 사용 가능 여부", example = "true")
        boolean gpuEnabled,

        @Schema(description = "DB 연결 상태", example = "ok")
        String db
) {
    @Schema(description = "AI 모델 로드 상태")
    public record ModelStatus(
            @Schema(description = "LLM 모델 상태", example = "not_loaded")
            String llm,

            @Schema(description = "TTS 모델 상태", example = "not_loaded")
            String tts,

            @Schema(description = "Ditto talking head 모델 상태", example = "not_loaded")
            String ditto
    ) {
    }
}
