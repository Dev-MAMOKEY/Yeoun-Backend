package com.mamokey.yeoun.domain.session.service;

import com.mamokey.yeoun.domain.session.dto.SendMessageRequest;
import com.mamokey.yeoun.domain.session.dto.SendMessageResponse;
import com.mamokey.yeoun.domain.session.dto.SessionStartRequest;
import com.mamokey.yeoun.domain.session.dto.SessionStartResponse;
import com.mamokey.yeoun.infra.fastapi.FastApiClient;
import com.mamokey.yeoun.infra.fastapi.FastApiSendMessageResponse;
import com.mamokey.yeoun.infra.fastapi.FastApiSessionStartResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final FastApiClient fastApiClient;

    public SessionStartResponse startSession(UUID userId, SessionStartRequest request) {
        UUID sessionId = UUID.randomUUID();

        // TODO: feat/#4 머지 후 personaId로 소유권 확인 추가
        FastApiSessionStartResponse response = fastApiClient.startSession(
                sessionId, request.personaId(), userId
        );

        return new SessionStartResponse(response.sessionId());
    }

    public SendMessageResponse sendMessage(UUID userId, UUID sessionId, SendMessageRequest request) {
        // TODO: feat/#4 머지 후 sessionId에 대한 userId 소유권 확인 추가
        FastApiSendMessageResponse response = fastApiClient.sendMessage(sessionId, request.message());

        return new SendMessageResponse(response.messageId(), response.text());
    }

    public void endSession(UUID userId, UUID sessionId) {
        // TODO: feat/#4 머지 후 sessionId에 대한 userId 소유권 확인 추가
        fastApiClient.endSession(sessionId);
    }

    public byte[] getMessageMedia(UUID userId, UUID sessionId, UUID messageId) {
        // TODO: feat/#4 머지 후 sessionId에 대한 userId 소유권 확인 추가
        return fastApiClient.streamMessageMedia(sessionId, messageId);
    }
}
