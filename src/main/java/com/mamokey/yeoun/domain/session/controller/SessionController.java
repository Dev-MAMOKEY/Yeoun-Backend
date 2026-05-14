package com.mamokey.yeoun.domain.session.controller;

import com.mamokey.yeoun.domain.session.dto.SessionStartRequest;
import com.mamokey.yeoun.domain.session.dto.SessionStartResponse;
import com.mamokey.yeoun.domain.session.service.SessionService;
import com.mamokey.yeoun.global.rsdata.RsData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.UUID;

@RestController
@RequestMapping("/api/session")
@RequiredArgsConstructor
@Tag(name = "Session", description = "대화 세션 시작, 메시지 전송, 미디어 조회 API")
@SecurityRequirement(name = "bearerAuth")
public class SessionController {

    private final SessionService sessionService;

    // 세션 시작
    @Operation(summary = "대화 세션 시작", description = "페르소나와의 대화 세션을 시작합니다.")
    @PostMapping("/start")
    public ResponseEntity<RsData<SessionStartResponse>> startSession(
            @Parameter(hidden = true) @AuthenticationPrincipal UUID userId,
            @RequestBody @Valid SessionStartRequest request
    ) {
        return ResponseEntity.ok(RsData.success(sessionService.startSession(userId, request)));
    }

    // 메시지 전송 (오디오 → SSE 스트림)
    @Operation(summary = "음성 메시지 전송", description = "음성 파일을 전송하고 SSE로 응답 진행 상태를 수신합니다.")
    @PostMapping(value = "/{sessionId}/message",
                 consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
                 produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter sendMessage(
            @Parameter(hidden = true) @AuthenticationPrincipal UUID userId,
            @Parameter(description = "세션 ID") @PathVariable("sessionId") UUID sessionId,
            @Parameter(description = "사용자 음성 파일") @RequestPart("audio") MultipartFile audio
    ) {
        return sessionService.sendMessage(userId, sessionId, audio);
    }

    // 세션 종료
    @Operation(summary = "대화 세션 종료", description = "진행 중인 대화 세션을 종료합니다.")
    @PostMapping("/{sessionId}/end")
    public ResponseEntity<RsData<Void>> endSession(
            @Parameter(hidden = true) @AuthenticationPrincipal UUID userId,
            @Parameter(description = "세션 ID") @PathVariable("sessionId") UUID sessionId
    ) {
        sessionService.endSession(userId, sessionId);
        return ResponseEntity.ok(RsData.success(null));
    }

    // 미디어 스트리밍 (음성+영상 합본)
    @Operation(summary = "메시지 미디어 스트리밍", description = "메시지에 연결된 음성/영상 미디어를 스트리밍합니다.")
    @GetMapping("/{sessionId}/messages/{messageId}/media")
    public ResponseEntity<byte[]> getMessageMedia(
            @Parameter(hidden = true) @AuthenticationPrincipal UUID userId,
            @Parameter(description = "세션 ID") @PathVariable("sessionId") UUID sessionId,
            @Parameter(description = "메시지 ID") @PathVariable("messageId") UUID messageId
    ) {
        byte[] data = sessionService.getMessageMedia(userId, sessionId, messageId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.parseMediaType("video/mp4").toString())
                .body(data);
    }
}
