package com.mamokey.yeoun.domain.persona.controller;

import com.mamokey.yeoun.domain.persona.dto.*;
import com.mamokey.yeoun.domain.persona.service.PersonaService;
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

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/persona")
@RequiredArgsConstructor
@Tag(name = "Persona", description = "페르소나 생성, 동의, 업로드, 상태 조회, 삭제 API")
@SecurityRequirement(name = "bearerAuth")
public class PersonaController {

    private final PersonaService personaService;

    @Operation(
            summary = "페르소나 생성 동의 기록",
            description = "페르소나 기본 정보 입력 전 약관 동의와 고인의 디지털 추모 거부 의사 질문 답변을 기록합니다."
    )
    @PostMapping("/consent")
    public ResponseEntity<RsData<Void>> recordConsent(
            @Parameter(hidden = true) @AuthenticationPrincipal UUID userId,
            @RequestBody @Valid PersonaConsentRequest request
    ) {
        personaService.recordConsent(userId, request);
        return ResponseEntity.ok(RsData.success(null));
    }

    @Operation(summary = "페르소나 생성", description = "로그인 사용자의 draft 상태 페르소나를 생성합니다.")
    @PostMapping
    public ResponseEntity<RsData<PersonaResponse>> createPersona(
            @Parameter(hidden = true) @AuthenticationPrincipal UUID userId,
            @RequestBody @Valid CreatePersonaRequest request
    ) {
        return ResponseEntity.ok(RsData.success(personaService.createPersona(userId, request)));
    }

    @Operation(summary = "페르소나 사진 업로드", description = "페르소나 생성에 사용할 사진 파일을 업로드합니다.")
    @PostMapping("/{personaId}/photo")
    public ResponseEntity<RsData<Void>> uploadPhoto(
            @Parameter(hidden = true) @AuthenticationPrincipal UUID userId,
            @Parameter(description = "페르소나 ID") @PathVariable("personaId") UUID personaId,
            @Parameter(description = "사진 파일") @RequestPart("file") MultipartFile file
    ) {
        personaService.uploadPhoto(userId, personaId, file);
        return ResponseEntity.ok(RsData.success(null));
    }

    @Operation(summary = "페르소나 음성 업로드", description = "페르소나 voice cloning에 사용할 음성 파일을 업로드합니다.")
    @PostMapping("/{personaId}/voice")
    public ResponseEntity<RsData<Void>> uploadVoice(
            @Parameter(hidden = true) @AuthenticationPrincipal UUID userId,
            @Parameter(description = "페르소나 ID") @PathVariable("personaId") UUID personaId,
            @Parameter(description = "음성 파일") @RequestPart("file") MultipartFile file
    ) {
        personaService.uploadVoice(userId, personaId, file);
        return ResponseEntity.ok(RsData.success(null));
    }

    @Operation(summary = "인터뷰 답변 저장", description = "페르소나 인터뷰 답변을 저장하고 생성 처리를 요청합니다.")
    @PostMapping("/{personaId}/interview")
    public ResponseEntity<RsData<Void>> saveInterview(
            @Parameter(hidden = true) @AuthenticationPrincipal UUID userId,
            @Parameter(description = "페르소나 ID") @PathVariable("personaId") UUID personaId,
            @RequestBody @Valid List<InterviewAnswerRequest> answers
    ) {
        personaService.saveInterview(userId, personaId, answers);
        return ResponseEntity.ok(RsData.success(null));
    }

    @Operation(summary = "페르소나 생성 상태 조회", description = "페르소나의 현재 생성 상태를 조회합니다.")
    @GetMapping("/{personaId}/status")
    public ResponseEntity<RsData<PersonaStatusResponse>> getStatus(
            @Parameter(hidden = true) @AuthenticationPrincipal UUID userId,
            @Parameter(description = "페르소나 ID") @PathVariable("personaId") UUID personaId
    ) {
        return ResponseEntity.ok(RsData.success(personaService.getStatus(userId, personaId)));
    }

    @Operation(summary = "Idle 영상 목록 조회", description = "페르소나의 Idle clip 목록을 조회합니다.")
    @GetMapping("/{personaId}/idle-clips")
    public ResponseEntity<RsData<List<IdleClipResponse>>> getIdleClips(
            @Parameter(hidden = true) @AuthenticationPrincipal UUID userId,
            @Parameter(description = "페르소나 ID") @PathVariable("personaId") UUID personaId
    ) {
        return ResponseEntity.ok(RsData.success(personaService.getIdleClips(userId, personaId)));
    }

    @Operation(summary = "Idle 영상 스트리밍", description = "페르소나의 특정 Idle clip 영상을 스트리밍합니다.")
    @GetMapping("/{personaId}/idle-clips/{idx}")
    public ResponseEntity<byte[]> streamIdleClip(
            @Parameter(hidden = true) @AuthenticationPrincipal UUID userId,
            @Parameter(description = "페르소나 ID") @PathVariable("personaId") UUID personaId,
            @Parameter(description = "Idle clip 순서") @PathVariable("idx") int idx
    ) {
        byte[] data = personaService.getIdleClipStream(userId, personaId, idx);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.parseMediaType("video/mp4").toString())
                .body(data);
    }

    @Operation(summary = "페르소나 삭제", description = "로그인 사용자가 소유한 페르소나와 관련 파일을 삭제합니다.")
    @DeleteMapping("/{personaId}")
    public ResponseEntity<RsData<Void>> deletePersona(
            @Parameter(hidden = true) @AuthenticationPrincipal UUID userId,
            @Parameter(description = "페르소나 ID") @PathVariable("personaId") UUID personaId
    ) {
        personaService.deletePersona(userId, personaId);
        return ResponseEntity.ok(RsData.success(null));
    }
}
