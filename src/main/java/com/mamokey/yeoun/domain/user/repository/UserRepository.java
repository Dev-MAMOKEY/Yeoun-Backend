package com.mamokey.yeoun.domain.user.repository;

import com.mamokey.yeoun.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email); // 회원가입 시 이메일 중복 방지
    Optional<User> findByEmail(String email); // 로그인 시 이메일로 회원 조회
}
