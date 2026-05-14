package com.mamokey.yeoun.domain.user.controller;

import com.mamokey.yeoun.domain.user.dto.DeleteUserRequest;
import com.mamokey.yeoun.domain.user.dto.UserMeResponse;
import com.mamokey.yeoun.domain.user.service.UserService;
import com.mamokey.yeoun.global.rsdata.RsData;
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
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<RsData<UserMeResponse>> getMe(@AuthenticationPrincipal UUID userId) {
        return ResponseEntity.ok(RsData.success(userService.getMe(userId)));
    }

    @DeleteMapping("/me")
    public ResponseEntity<RsData<Void>> deleteMe(
            @AuthenticationPrincipal UUID userId,
            @RequestBody @Valid DeleteUserRequest request
    ) {
        userService.deleteMe(userId, request.password());
        return ResponseEntity.ok(RsData.success(null));
    }
}
