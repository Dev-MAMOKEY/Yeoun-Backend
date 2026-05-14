package com.mamokey.yeoun.domain.user.service;

import com.mamokey.yeoun.domain.persona.entity.Persona;
import com.mamokey.yeoun.domain.persona.repository.PersonaConsentRepository;
import com.mamokey.yeoun.domain.persona.repository.PersonaIdleClipRepository;
import com.mamokey.yeoun.domain.persona.repository.PersonaInterviewRepository;
import com.mamokey.yeoun.domain.persona.repository.PersonaPhotoAssetRepository;
import com.mamokey.yeoun.domain.persona.repository.PersonaRepository;
import com.mamokey.yeoun.domain.persona.repository.PersonaVoiceAssetRepository;
import com.mamokey.yeoun.domain.user.dto.UserMeResponse;
import com.mamokey.yeoun.domain.user.entity.User;
import com.mamokey.yeoun.domain.user.repository.UserRepository;
import com.mamokey.yeoun.global.exception.CustomException;
import com.mamokey.yeoun.global.exception.ErrorCode;
import com.mamokey.yeoun.infra.fastapi.FastApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PersonaRepository personaRepository;
    private final PersonaPhotoAssetRepository photoAssetRepository;
    private final PersonaVoiceAssetRepository voiceAssetRepository;
    private final PersonaInterviewRepository interviewRepository;
    private final PersonaIdleClipRepository idleClipRepository;
    private final PersonaConsentRepository consentRepository;
    private final PasswordEncoder passwordEncoder;
    private final FastApiClient fastApiClient;

    public UserMeResponse getMe(UUID userId) {
        User user = getUser(userId);
        List<Persona> personas = personaRepository.findAllByOwnerUserId(userId);
        return UserMeResponse.of(user, personas);
    }

    @Transactional
    public void deleteMe(UUID userId, String password) {
        User user = getUser(userId);
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_CREDENTIALS);
        }

        List<Persona> personas = personaRepository.findAllByOwnerUserId(userId);
        List<UUID> personaIds = personas.stream()
                .map(Persona::getId)
                .toList();

        personas.forEach(persona -> fastApiClient.deletePersona(persona.getId()));

        if (!personaIds.isEmpty()) {
            idleClipRepository.deleteByPersonaIdIn(personaIds);
            interviewRepository.deleteByPersonaIdIn(personaIds);
            voiceAssetRepository.deleteByPersonaIdIn(personaIds);
            photoAssetRepository.deleteByPersonaIdIn(personaIds);
            personaRepository.deleteAllByOwnerUserId(userId);
        }

        consentRepository.deleteByUserId(userId);
        userRepository.delete(user);
    }

    private User getUser(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    }
}
