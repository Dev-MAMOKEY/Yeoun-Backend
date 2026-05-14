package com.mamokey.yeoun.domain.auth.controller;

import com.mamokey.yeoun.domain.auth.dto.LoginRequest;
import com.mamokey.yeoun.domain.auth.dto.LoginResponse;
import com.mamokey.yeoun.domain.auth.dto.SignUpRequest;
import com.mamokey.yeoun.domain.auth.dto.SignUpResponse;
import com.mamokey.yeoun.domain.auth.service.AuthService;
import com.mamokey.yeoun.global.rsdata.RsData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Auth", description = "회원가입, 로그인, 로그아웃 API")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "회원가입", description = "이메일, 비밀번호, 비밀번호 확인을 받아 신규 사용자를 생성합니다.")
    @PostMapping("/signup")
    public ResponseEntity<RsData<SignUpResponse>> signup(@Valid @RequestBody SignUpRequest signUpRequest) {
        SignUpResponse response = authService.signup(signUpRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(RsData.success(response));
    }

    @Operation(summary = "로그인", description = "이메일과 비밀번호를 검증하고 Access Token과 Refresh Token을 발급합니다.")
    @PostMapping("/login")
    public ResponseEntity<RsData<LoginResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse response = authService.login(loginRequest);
        return ResponseEntity.ok(RsData.success(response));
    }

    @Operation(summary = "로그아웃", description = "현재 로그인 사용자의 Refresh Token을 무효화합니다.")
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/logout")
    public ResponseEntity<RsData<Void>> logout(@Parameter(hidden = true) @AuthenticationPrincipal UUID userId) {
        authService.logout(userId);
        return ResponseEntity.ok(RsData.success(null));
    }
}
