package com.mamokey.yeoun.domain.persona.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "personas")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "personas_id", columnDefinition = "uuid")
    private UUID id;

    @Column(name = "owner_user_id", nullable = false, columnDefinition = "uuid")
    private UUID ownerUserId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "nickname", nullable = false, length = 50)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private PersonaStatus status;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public Persona(String name, String nickname, UUID ownerUserId) {
        this.name = name;
        this.nickname = nickname;
        this.ownerUserId = ownerUserId;
        this.status = PersonaStatus.DRAFT;
    }

    public void updateStatus(PersonaStatus status) {
        this.status = status;
    }
}
