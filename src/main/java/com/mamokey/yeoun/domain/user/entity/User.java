package com.mamokey.yeoun.domain.user.entity;

import com.mamokey.yeoun.domain.auth.dto.SignUpResponse;
import com.mamokey.yeoun.global.entity.GlobalEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User extends GlobalEntity {

    @Column(name = "email" , nullable = false)
    private String email;

    @Column(name = "password_hash" , nullable = false)
    private String password;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "rt_at")
    private LocalDateTime rtAt;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public SignUpResponse toSignupResponse() {
        return new SignUpResponse(this.getId(), this.getEmail());
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        this.rtAt = LocalDateTime.now();
    }

    public void revokeRefreshToken() {
        this.refreshToken = null;
        this.rtAt = null;
    }
}
