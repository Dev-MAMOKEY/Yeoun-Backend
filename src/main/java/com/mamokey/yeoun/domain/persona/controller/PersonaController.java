package com.mamokey.yeoun.domain.persona.controller;

import com.mamokey.yeoun.domain.persona.dto.*;
import com.mamokey.yeoun.domain.persona.service.PersonaService;
import com.mamokey.yeoun.global.rsdata.RsData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/persona")
@RequiredArgsConstructor
public class PersonaController {

    private final PersonaService personaService;

    @PostMapping
    public ResponseEntity<RsData<PersonaResponse>> createPersona(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody @Valid CreatePersonaRequest request
    ) {
        UUID userId = UUID.fromString(jwt.getSubject());
        return ResponseEntity.ok(RsData.success(personaService.createPersona(userId, request)));
    }

    @PostMapping("/{personaId}/photo")
    public ResponseEntity<RsData<Void>> uploadPhoto(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID personaId,
            @RequestPart MultipartFile file
    ) {
        UUID userId = UUID.fromString(jwt.getSubject());
        personaService.uploadPhoto(userId, personaId, file);
        return ResponseEntity.ok(RsData.success(null));
    }

    @PostMapping("/{personaId}/voice")
    public ResponseEntity<RsData<Void>> uploadVoice(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID personaId,
            @RequestPart MultipartFile file
    ) {
        UUID userId = UUID.fromString(jwt.getSubject());
        personaService.uploadVoice(userId, personaId, file);
        return ResponseEntity.ok(RsData.success(null));
    }

    @PostMapping("/{personaId}/interview")
    public ResponseEntity<RsData<Void>> saveInterview(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID personaId,
            @RequestBody @Valid List<InterviewAnswerRequest> answers
    ) {
        UUID userId = UUID.fromString(jwt.getSubject());
        personaService.saveInterview(userId, personaId, answers);
        return ResponseEntity.ok(RsData.success(null));
    }

    @GetMapping("/{personaId}/status")
    public ResponseEntity<RsData<PersonaStatusResponse>> getStatus(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID personaId
    ) {
        UUID userId = UUID.fromString(jwt.getSubject());
        return ResponseEntity.ok(RsData.success(personaService.getStatus(userId, personaId)));
    }

    @GetMapping("/{personaId}/idle-clips")
    public ResponseEntity<RsData<List<IdleClipResponse>>> getIdleClips(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID personaId
    ) {
        UUID userId = UUID.fromString(jwt.getSubject());
        return ResponseEntity.ok(RsData.success(personaService.getIdleClips(userId, personaId)));
    }

    @GetMapping("/{personaId}/idle-clips/{idx}")
    public ResponseEntity<byte[]> streamIdleClip(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID personaId,
            @PathVariable int idx
    ) {
        UUID userId = UUID.fromString(jwt.getSubject());
        byte[] data = personaService.getIdleClipStream(userId, personaId, idx);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.parseMediaType("video/mp4").toString())
                .body(data);
    }

    @DeleteMapping("/{personaId}")
    public ResponseEntity<RsData<Void>> deletePersona(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID personaId
    ) {
        UUID userId = UUID.fromString(jwt.getSubject());
        personaService.deletePersona(userId, personaId);
        return ResponseEntity.ok(RsData.success(null));
    }
}
