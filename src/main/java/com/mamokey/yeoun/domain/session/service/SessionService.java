package com.mamokey.yeoun.domain.session.service;

import com.mamokey.yeoun.domain.session.dto.SessionStartResponse;
import com.mamokey.yeoun.domain.session.dto.SessionStartRequest;
import com.mamokey.yeoun.global.exception.CustomException;
import com.mamokey.yeoun.global.exception.ErrorCode;
import com.mamokey.yeoun.infra.fastapi.FastApiClient;
import com.mamokey.yeoun.infra.fastapi.FastApiSessionStartResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessionService {

    private final FastApiClient fastApiClient;

    public SessionStartResponse startSession(UUID userId, SessionStartRequest request) {
        FastApiSessionStartResponse response = fastApiClient.startSession(
                request.personaId(), userId
        );
        return new SessionStartResponse(response.sessionId());
    }

    public SseEmitter sendMessage(UUID userId, UUID sessionId, MultipartFile audio) {
        SseEmitter emitter = new SseEmitter(300_000L); // 5분 타임아웃

        CompletableFuture.runAsync(() -> {
            try {
                fastApiClient.sendMessageStream(sessionId, audio, emitter);
                emitter.complete();
            } catch (CustomException e) {
                log.error("[Session] sendMessage stream error: {}", e.getMessage());
                try {
                    emitter.send(SseEmitter.event()
                            .name("error")
                            .data("{\"reason\":\"AI 서버 오류가 발생했습니다.\"}"));
                } catch (IOException ignored) {}
                emitter.completeWithError(e);
            } catch (Exception e) {
                log.error("[Session] sendMessage unexpected error: {}", e.getMessage());
                emitter.completeWithError(e);
            }
        });

        return emitter;
    }

    public void endSession(UUID userId, UUID sessionId) {
        fastApiClient.endSession(sessionId);
    }

    public byte[] getMessageMedia(UUID userId, UUID sessionId, UUID messageId) {
        return fastApiClient.streamMessageMedia(sessionId, messageId);
    }
}
