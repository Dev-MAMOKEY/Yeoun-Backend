package com.mamokey.yeoun.domain.persona.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "persona_consents")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PersonaConsent {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "consent_id", columnDefinition = "uuid")
    private UUID id;

    @Column(name = "user_id", nullable = false, columnDefinition = "uuid")
    private UUID userId;

    @Column(name = "consent_version", nullable = false)
    private Integer consentVersion;

    @Column(name = "declined_intent_answered", nullable = false)
    private Boolean declinedIntentAnswered;

    @Column(name = "agreed_at", nullable = false)
    private LocalDateTime agreedAt;

    @Builder
    public PersonaConsent(UUID userId, Integer consentVersion, Boolean declinedIntentAnswered, LocalDateTime agreedAt) {
        this.userId = userId;
        this.consentVersion = consentVersion;
        this.declinedIntentAnswered = declinedIntentAnswered;
        this.agreedAt = agreedAt;
    }
}
