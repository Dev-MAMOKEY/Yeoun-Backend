package com.mamokey.yeoun.infra.fastapi;

import com.mamokey.yeoun.global.exception.CustomException;
import com.mamokey.yeoun.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;

import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class FastApiClient {

    private final RestClient restClient;

    // 세션 시작
    public FastApiSessionStartResponse startSession(UUID sessionId, UUID personaId, UUID userId) {
        try {
            FastApiResponse<FastApiSessionStartResponse> response = restClient.post()
                    .uri("/internal/sessions")
                    .body(Map.of(
                            "session_id", sessionId.toString(),
                            "persona_id", personaId.toString(),
                            "user_id", userId.toString()
                    ))
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});
            return response.data();
        } catch (RestClientResponseException e) {
            log.error("[FastAPI] startSession failed: status={}, body={}", e.getStatusCode(), e.getResponseBodyAsString());
            throw new CustomException(ErrorCode.AI_SERVER_REQUEST_FAILED);
        } catch (RestClientException e) {
            log.error("[FastAPI] startSession connection failed: {}", e.getMessage());
            throw new CustomException(ErrorCode.AI_SERVER_REQUEST_FAILED);
        }
    }

    // 메시지 전송
    public FastApiSendMessageResponse sendMessage(UUID sessionId, String message) {
        try {
            FastApiResponse<FastApiSendMessageResponse> response = restClient.post()
                    .uri("/internal/sessions/{sessionId}/messages", sessionId)
                    .body(Map.of("message", message))
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});
            return response.data();
        } catch (RestClientResponseException e) {
            log.error("[FastAPI] sendMessage failed: status={}, body={}", e.getStatusCode(), e.getResponseBodyAsString());
            throw new CustomException(ErrorCode.AI_SERVER_REQUEST_FAILED);
        } catch (RestClientException e) {
            log.error("[FastAPI] sendMessage connection failed: {}", e.getMessage());
            throw new CustomException(ErrorCode.AI_SERVER_REQUEST_FAILED);
        }
    }

    // 세션 종료
    public void endSession(UUID sessionId) {
        try {
            restClient.delete()
                    .uri("/internal/sessions/{sessionId}", sessionId)
                    .retrieve()
                    .toBodilessEntity();
        } catch (RestClientResponseException e) {
            log.error("[FastAPI] endSession failed: status={}, body={}", e.getStatusCode(), e.getResponseBodyAsString());
            throw new CustomException(ErrorCode.AI_SERVER_REQUEST_FAILED);
        } catch (RestClientException e) {
            log.error("[FastAPI] endSession connection failed: {}", e.getMessage());
            throw new CustomException(ErrorCode.AI_SERVER_REQUEST_FAILED);
        }
    }

    // 미디어 스트리밍
    public byte[] streamMessageMedia(UUID sessionId, UUID messageId) {
        try {
            return restClient.get()
                    .uri("/internal/sessions/{sessionId}/messages/{messageId}/media", sessionId, messageId)
                    .retrieve()
                    .body(byte[].class);
        } catch (RestClientResponseException e) {
            log.error("[FastAPI] streamMessageMedia failed: status={}, body={}", e.getStatusCode(), e.getResponseBodyAsString());
            throw new CustomException(ErrorCode.AI_SERVER_REQUEST_FAILED);
        } catch (RestClientException e) {
            log.error("[FastAPI] streamMessageMedia connection failed: {}", e.getMessage());
            throw new CustomException(ErrorCode.AI_SERVER_REQUEST_FAILED);
        }
    }
}
