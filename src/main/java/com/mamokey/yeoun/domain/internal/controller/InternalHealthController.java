package com.mamokey.yeoun.domain.internal.controller;

import com.mamokey.yeoun.domain.internal.dto.InternalHealthResponse;
import com.mamokey.yeoun.domain.internal.service.InternalHealthService;
import com.mamokey.yeoun.global.rsdata.RsData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal")
@Tag(name = "Internal", description = "운영 및 컨테이너 내부 상태 확인 API")
@SecurityRequirement(name = "bearerAuth")
public class InternalHealthController {

    private final InternalHealthService internalHealthService;

    @Operation(
            summary = "서비스 상태 확인",
            description = "모델 로드 상태, 활성 세션 수, GPU 활성 여부, DB 연결 상태를 반환합니다. 컨테이너 HEALTHCHECK와 운영 대시보드에서 사용됩니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful Response"),
                    @ApiResponse(responseCode = "401", description = "토큰이 없거나 유효하지 않음 (UNAUTHORIZED).")
            }
    )
    @GetMapping("/health")
    public ResponseEntity<RsData<InternalHealthResponse>> getHealth() {
        return ResponseEntity.ok(RsData.success(internalHealthService.getHealth()));
    }
}
