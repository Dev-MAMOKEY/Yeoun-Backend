package com.mamokey.yeoun.domain.auth.service;

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
        User user = userRepository.save(
                new User(
                        request.email(),
                        passwordEncoder.encode(request.password())
                )
        );
        return user.toSignupResponse();
    }
}
