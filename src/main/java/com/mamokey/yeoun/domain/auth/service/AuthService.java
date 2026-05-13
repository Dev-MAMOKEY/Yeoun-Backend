package com.mamokey.yeoun.domain.auth.service;

import com.mamokey.yeoun.domain.auth.dto.LoginRequest;
import com.mamokey.yeoun.domain.auth.dto.LoginResponse;
import com.mamokey.yeoun.domain.auth.dto.SignUpRequest;
import com.mamokey.yeoun.domain.auth.dto.SignUpResponse;
import com.mamokey.yeoun.domain.user.entity.User;
import com.mamokey.yeoun.domain.user.repository.UserRepository;
import com.mamokey.yeoun.global.exception.CustomException;
import com.mamokey.yeoun.global.exception.ErrorCode;
import com.mamokey.yeoun.global.security.jwt.JwtProvider;
import com.mamokey.yeoun.global.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private JwtUtil jwtUtil;
    private JwtProvider jwtProvider;

    @Transactional
    public SignUpResponse signup(SignUpRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new CustomException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        if (!request.password().equals(request.passwordConfirm())) {
            throw new CustomException(ErrorCode.PASSWORD_CONFIRMATION_MISMATCH);
        }
        User user = userRepository.save(
                new User(
                        request.email(),
                        passwordEncoder.encode(request.password())
                )
        );
        return user.toSignupResponse();
    }

    @Transactional
    public LoginResponse login(LoginRequest request) {
        // ① 이메일로 아이디 조회
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_CREDENTIALS));

        // ② 비밀번호 검증
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_CREDENTIALS);
        }

        // ④ AT/RT 발급
        String accessToken = jwtProvider.createAccessToken(user.getId());
        String refreshToken = jwtProvider.createRefreshToken(user.getId());
        user.updateRefreshToken(refreshToken); // RT DB 저장

        return new LoginResponse(accessToken, refreshToken);
    }

    @Transactional
    public void logout(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        user.revokeRefreshToken();
    }
}
