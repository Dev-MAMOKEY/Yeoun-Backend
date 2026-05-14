package com.mamokey.yeoun.domain.user.controller;

import com.mamokey.yeoun.domain.user.dto.DeleteUserRequest;
import com.mamokey.yeoun.domain.user.dto.UserMeResponse;
import com.mamokey.yeoun.domain.user.service.UserService;
import com.mamokey.yeoun.global.rsdata.RsData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "User", description = "사용자 내 정보 조회 및 계정 삭제 API")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserService userService;

    @Operation(summary = "내 정보 조회", description = "현재 로그인 사용자의 이메일과 페르소나 정보를 조회합니다.")
    @GetMapping("/me")
    public ResponseEntity<RsData<UserMeResponse>> getMe(@Parameter(hidden = true) @AuthenticationPrincipal UUID userId) {
        return ResponseEntity.ok(RsData.success(userService.getMe(userId)));
    }

    @Operation(summary = "계정 삭제", description = "비밀번호를 확인한 뒤 사용자 계정과 연결된 페르소나 데이터를 삭제합니다.")
    @DeleteMapping("/me")
    public ResponseEntity<RsData<Void>> deleteMe(
            @Parameter(hidden = true) @AuthenticationPrincipal UUID userId,
            @RequestBody @Valid DeleteUserRequest request
    ) {
        userService.deleteMe(userId, request.password());
        return ResponseEntity.ok(RsData.success(null));
    }
}
