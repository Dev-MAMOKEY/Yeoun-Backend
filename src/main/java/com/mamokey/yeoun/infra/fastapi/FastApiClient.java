package com.mamokey.yeoun.infra.fastapi;

import com.mamokey.yeoun.global.exception.CustomException;
import com.mamokey.yeoun.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class FastApiClient {

    private final RestClient restClient;

    public FastApiPhotoUploadResponse uploadPhoto(UUID personaId, MultipartFile file) {
        try {
            log.debug("[FastAPI] uploadPhoto: personaId={}, filename={}, contentType={}, size={}",
                    personaId, file.getOriginalFilename(), file.getContentType(), file.getSize());
            FastApiResponse<FastApiPhotoUploadResponse> response = restClient.post()
                    .uri("/internal/personas/{id}/photo", personaId)
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(toMultipart(file))
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});
            return response.data();
        } catch (RestClientResponseException e) {
            log.error("[FastAPI] uploadPhoto failed: status={}, body={}", e.getStatusCode(), e.getResponseBodyAsString());
            if (e.getStatusCode().value() == 409) {
                throw new CustomException(ErrorCode.PERSONA_PROCESSING);
            }
            throw new CustomException(ErrorCode.AI_SERVER_REQUEST_FAILED);
        } catch (RestClientException e) {
            log.error("[FastAPI] uploadPhoto connection failed: {}", e.getMessage());
            throw new CustomException(ErrorCode.AI_SERVER_REQUEST_FAILED);
        }
    }

    public FastApiVoiceUploadResponse uploadVoice(UUID personaId, MultipartFile file) {
        try {
            log.debug("[FastAPI] uploadVoice: personaId={}, filename={}, contentType={}, size={}",
                    personaId, file.getOriginalFilename(), file.getContentType(), file.getSize());
            FastApiResponse<FastApiVoiceUploadResponse> response = restClient.post()
                    .uri("/internal/personas/{id}/voice", personaId)
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(toMultipart(file))
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});
            return response.data();
        } catch (RestClientResponseException e) {
            log.error("[FastAPI] uploadVoice failed: status={}, body={}", e.getStatusCode(), e.getResponseBodyAsString());
            if (e.getStatusCode().value() == 409) {
                throw new CustomException(ErrorCode.PERSONA_PROCESSING);
            }
            throw new CustomException(ErrorCode.AI_SERVER_REQUEST_FAILED);
        } catch (RestClientException e) {
            log.error("[FastAPI] uploadVoice connection failed: {}", e.getMessage());
            throw new CustomException(ErrorCode.AI_SERVER_REQUEST_FAILED);
        }
    }

    public void triggerProcess(UUID personaId) {
        try {
            restClient.post()
                    .uri("/internal/personas/{id}/process", personaId)
                    .retrieve()
                    .toBodilessEntity();
        } catch (RestClientException e) {
            throw new CustomException(ErrorCode.AI_SERVER_REQUEST_FAILED);
        }
    }

    public void deletePersona(UUID personaId) {
        try {
            restClient.delete()
                    .uri("/internal/personas/{id}", personaId)
                    .retrieve()
                    .toBodilessEntity();
        } catch (RestClientResponseException e) {
            if (e.getStatusCode().value() == 404) {
                log.warn("[FastAPI] deletePersona: persona not found on AI server, proceeding with DB delete. personaId={}", personaId);
                return;
            }
            if (e.getStatusCode().value() == 409) {
                throw new CustomException(ErrorCode.PERSONA_PROCESSING);
            }
            log.error("[FastAPI] deletePersona failed: status={}, body={}", e.getStatusCode(), e.getResponseBodyAsString());
            throw new CustomException(ErrorCode.AI_SERVER_REQUEST_FAILED);
        } catch (RestClientException e) {
            log.error("[FastAPI] deletePersona connection failed: {}", e.getMessage());
            throw new CustomException(ErrorCode.AI_SERVER_REQUEST_FAILED);
        }
    }

    public byte[] streamIdleClip(UUID personaId, int idx) {
        try {
            return restClient.get()
                    .uri("/internal/personas/{id}/idle-clips/{idx}", personaId, idx)
                    .retrieve()
                    .body(byte[].class);
        } catch (RestClientException e) {
            throw new CustomException(ErrorCode.AI_SERVER_REQUEST_FAILED);
        }
    }

    public byte[] streamMessageMedia(UUID sessionId, UUID messageId) {
        try {
            return restClient.get()
                    .uri("/internal/sessions/{sessionId}/messages/{messageId}/media", sessionId, messageId)
                    .retrieve()
                    .body(byte[].class);
        } catch (RestClientException e) {
            throw new CustomException(ErrorCode.AI_SERVER_REQUEST_FAILED);
        }
    }

    private MultiValueMap<String, Object> toMultipart(MultipartFile file) {
        try {
            String original = file.getOriginalFilename() != null ? file.getOriginalFilename() : "upload";
            String ext = original.contains(".") ? original.substring(original.lastIndexOf('.')) : "";
            String filename = UUID.randomUUID() + ext;
            String contentType = file.getContentType() != null ? file.getContentType() : MediaType.APPLICATION_OCTET_STREAM_VALUE;

            ByteArrayResource resource = new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return filename;
                }
            };

            HttpHeaders fileHeaders = new HttpHeaders();
            fileHeaders.setContentType(MediaType.parseMediaType(contentType));
            fileHeaders.setContentDispositionFormData("file", filename);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", new HttpEntity<>(resource, fileHeaders));
            return body;
        } catch (IOException e) {
            throw new CustomException(ErrorCode.FILE_UPLOAD_FAILED);
        }
    }
}
