package com.mamokey.yeoun.domain.persona.service;

import com.mamokey.yeoun.domain.persona.dto.*;
import com.mamokey.yeoun.domain.persona.entity.*;
import com.mamokey.yeoun.domain.persona.repository.*;
import com.mamokey.yeoun.global.exception.CustomException;
import com.mamokey.yeoun.global.exception.ErrorCode;
import com.mamokey.yeoun.infra.fastapi.FastApiClient;
import com.mamokey.yeoun.infra.fastapi.FastApiPhotoUploadResponse;
import com.mamokey.yeoun.infra.fastapi.FastApiVoiceUploadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PersonaService {

    private static final long MAX_PHOTO_SIZE = 50L * 1024 * 1024;
    private static final Set<String> ALLOWED_PHOTO_TYPES = Set.of(
            "image/jpeg", "image/png", "image/webp"
    );
    private static final Set<String> ALLOWED_VOICE_TYPES = Set.of(
            "video/mp4", "video/quicktime", "audio/mp4",
            "audio/mpeg", "audio/wav", "audio/x-wav"
    );

    private final PersonaRepository personaRepository;
    private final PersonaPhotoAssetRepository photoAssetRepository;
    private final PersonaVoiceAssetRepository voiceAssetRepository;
    private final PersonaInterviewRepository interviewRepository;
    private final PersonaIdleClipRepository idleClipRepository;
    private final FastApiClient fastApiClient;

    public PersonaResponse createPersona(Long userId, CreatePersonaRequest request) {
        Persona persona = Persona.builder()
                .name(request.name())
                .nickname(request.nickname())
                .ownerUserId(userId)
                .build();
        return PersonaResponse.from(personaRepository.save(persona));
    }

    public void uploadPhoto(Long userId, UUID personaId, MultipartFile file) {
        Persona persona = getOwnedPersona(userId, personaId);
        validatePhotoFile(file);

        FastApiPhotoUploadResponse meta = fastApiClient.uploadPhoto(personaId, file);

        PersonaPhotoAsset asset = PersonaPhotoAsset.builder()
                .persona(persona)
                .filesystemPath(meta.path())
                .build();
        photoAssetRepository.save(asset);
    }

    public void uploadVoice(Long userId, UUID personaId, MultipartFile file) {
        Persona persona = getOwnedPersona(userId, personaId);
        validateVoiceFile(file);

        FastApiVoiceUploadResponse meta = fastApiClient.uploadVoice(personaId, file);

        PersonaVoiceAsset asset = PersonaVoiceAsset.builder()
                .persona(persona)
                .originalName(file.getOriginalFilename())
                .filesystemPath(meta.path())
                .build();
        voiceAssetRepository.save(asset);
    }

    public void saveInterview(Long userId, UUID personaId, List<InterviewAnswerRequest> answers) {
        Persona persona = getOwnedPersona(userId, personaId);

        List<PersonaInterview> interviews = answers.stream()
                .filter(a -> a.answer() != null && !a.answer().isBlank())
                .map(a -> PersonaInterview.builder()
                        .persona(persona)
                        .questionNumber(a.questionNumber())
                        .answerText(a.answer())
                        .build())
                .toList();

        interviewRepository.saveAll(interviews);
        persona.updateStatus(PersonaStatus.PROCESSING);

        fastApiClient.triggerProcess(personaId);
    }

    @Transactional(readOnly = true)
    public PersonaStatusResponse getStatus(Long userId, UUID personaId) {
        Persona persona = getOwnedPersona(userId, personaId);
        return PersonaStatusResponse.from(persona);
    }

    @Transactional(readOnly = true)
    public List<IdleClipResponse> getIdleClips(Long userId, UUID personaId) {
        getOwnedPersona(userId, personaId);
        return idleClipRepository.findAllByPersonaIdOrderBySequenceOrder(personaId)
                .stream()
                .map(IdleClipResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public byte[] getIdleClipStream(Long userId, UUID personaId, int idx) {
        getOwnedPersona(userId, personaId);
        idleClipRepository.findByPersonaIdAndSequenceOrder(personaId, idx)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));
        return fastApiClient.streamIdleClip(personaId, idx);
    }

    public void deletePersona(Long userId, UUID personaId) {
        getOwnedPersona(userId, personaId);
        fastApiClient.deletePersona(personaId);
        personaRepository.deleteById(personaId);
    }

    private Persona getOwnedPersona(Long userId, UUID personaId) {
        return personaRepository.findByIdAndOwnerUserId(personaId, userId)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));
    }

    private void validatePhotoFile(MultipartFile file) {
        if (file.getSize() > MAX_PHOTO_SIZE) {
            throw new CustomException(ErrorCode.FILE_SIZE_EXCEEDED);
        }
        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_PHOTO_TYPES.contains(contentType)) {
            throw new CustomException(ErrorCode.UNSUPPORTED_FILE_TYPE);
        }
    }

    private void validateVoiceFile(MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_VOICE_TYPES.contains(contentType)) {
            throw new CustomException(ErrorCode.UNSUPPORTED_FILE_TYPE);
        }
    }
}
