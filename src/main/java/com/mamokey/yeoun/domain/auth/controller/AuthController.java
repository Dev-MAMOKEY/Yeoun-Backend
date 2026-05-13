package com.mamokey.yeoun.domain.auth.controller;

import com.mamokey.yeoun.domain.auth.dto.LoginRequest;
import com.mamokey.yeoun.domain.auth.dto.LoginResponse;
import com.mamokey.yeoun.domain.auth.dto.SignUpRequest;
import com.mamokey.yeoun.domain.auth.dto.SignUpResponse;
import com.mamokey.yeoun.domain.auth.service.AuthService;
import com.mamokey.yeoun.global.rsdata.RsData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<RsData<SignUpResponse>> signup(@Valid @RequestBody SignUpRequest signUpRequest) {
        SignUpResponse response = authService.signup(signUpRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(RsData.success(response));
    }

    @PostMapping("/login")
    public ResponseEntity<RsData<LoginResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse response = authService.login(loginRequest);
        return ResponseEntity.ok(RsData.success(response));
    }

    @PostMapping("/logout")
    public ResponseEntity<RsData<Void>> logout(@AuthenticationPrincipal Long userId) {
        authService.logout(userId);
        return ResponseEntity.ok(RsData.success(null));
    }
}
