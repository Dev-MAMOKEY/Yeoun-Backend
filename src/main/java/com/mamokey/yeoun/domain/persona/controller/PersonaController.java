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
            @AuthenticationPrincipal Long userId,
            @RequestBody @Valid CreatePersonaRequest request
    ) {
        return ResponseEntity.ok(RsData.success(personaService.createPersona(userId, request)));
    }

    @PostMapping("/{personaId}/photo")
    public ResponseEntity<RsData<Void>> uploadPhoto(
            @AuthenticationPrincipal Long userId,
            @PathVariable UUID personaId,
            @RequestPart("file") MultipartFile file
    ) {
        personaService.uploadPhoto(userId, personaId, file);
        return ResponseEntity.ok(RsData.success(null));
    }

    @PostMapping("/{personaId}/voice")
    public ResponseEntity<RsData<Void>> uploadVoice(
            @AuthenticationPrincipal Long userId,
            @PathVariable UUID personaId,
            @RequestPart("file") MultipartFile file
    ) {
        personaService.uploadVoice(userId, personaId, file);
        return ResponseEntity.ok(RsData.success(null));
    }

    @PostMapping("/{personaId}/interview")
    public ResponseEntity<RsData<Void>> saveInterview(
            @AuthenticationPrincipal Long userId,
            @PathVariable UUID personaId,
            @RequestBody @Valid List<InterviewAnswerRequest> answers
    ) {
        personaService.saveInterview(userId, personaId, answers);
        return ResponseEntity.ok(RsData.success(null));
    }

    @GetMapping("/{personaId}/status")
    public ResponseEntity<RsData<PersonaStatusResponse>> getStatus(
            @AuthenticationPrincipal Long userId,
            @PathVariable UUID personaId
    ) {
        return ResponseEntity.ok(RsData.success(personaService.getStatus(userId, personaId)));
    }

    @GetMapping("/{personaId}/idle-clips")
    public ResponseEntity<RsData<List<IdleClipResponse>>> getIdleClips(
            @AuthenticationPrincipal Long userId,
            @PathVariable UUID personaId
    ) {
        return ResponseEntity.ok(RsData.success(personaService.getIdleClips(userId, personaId)));
    }

    @GetMapping("/{personaId}/idle-clips/{idx}")
    public ResponseEntity<byte[]> streamIdleClip(
            @AuthenticationPrincipal Long userId,
            @PathVariable UUID personaId,
            @PathVariable int idx
    ) {
        byte[] data = personaService.getIdleClipStream(userId, personaId, idx);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.parseMediaType("video/mp4").toString())
                .body(data);
    }

    @DeleteMapping("/{personaId}")
    public ResponseEntity<RsData<Void>> deletePersona(
            @AuthenticationPrincipal Long userId,
            @PathVariable UUID personaId
    ) {
        personaService.deletePersona(userId, personaId);
        return ResponseEntity.ok(RsData.success(null));
    }
}
