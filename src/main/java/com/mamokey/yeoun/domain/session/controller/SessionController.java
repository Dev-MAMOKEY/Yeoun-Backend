package com.mamokey.yeoun.domain.session.controller;

import com.mamokey.yeoun.domain.session.dto.SessionStartRequest;
import com.mamokey.yeoun.domain.session.dto.SessionStartResponse;
import com.mamokey.yeoun.domain.session.service.SessionService;
import com.mamokey.yeoun.global.rsdata.RsData;
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
public class SessionController {

    private final SessionService sessionService;

    // 세션 시작
    @PostMapping("/start")
    public ResponseEntity<RsData<SessionStartResponse>> startSession(
            @AuthenticationPrincipal UUID userId,
            @RequestBody @Valid SessionStartRequest request
    ) {
        return ResponseEntity.ok(RsData.success(sessionService.startSession(userId, request)));
    }

    // 메시지 전송 (오디오 → SSE 스트림)
    @PostMapping(value = "/{sessionId}/message",
                 consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
                 produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter sendMessage(
            @AuthenticationPrincipal UUID userId,
            @PathVariable UUID sessionId,
            @RequestPart("audio") MultipartFile audio
    ) {
        return sessionService.sendMessage(userId, sessionId, audio);
    }

    // 세션 종료
    @PostMapping("/{sessionId}/end")
    public ResponseEntity<RsData<Void>> endSession(
            @AuthenticationPrincipal UUID userId,
            @PathVariable UUID sessionId
    ) {
        sessionService.endSession(userId, sessionId);
        return ResponseEntity.ok(RsData.success(null));
    }

    // 미디어 스트리밍 (음성+영상 합본)
    @GetMapping("/{sessionId}/messages/{messageId}/media")
    public ResponseEntity<byte[]> getMessageMedia(
            @AuthenticationPrincipal UUID userId,
            @PathVariable UUID sessionId,
            @PathVariable UUID messageId
    ) {
        byte[] data = sessionService.getMessageMedia(userId, sessionId, messageId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.parseMediaType("video/mp4").toString())
                .body(data);
    }
}
