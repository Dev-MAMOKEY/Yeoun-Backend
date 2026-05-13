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
@Table(name = "persona_voice_assets")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PersonaVoiceAsset {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "voice_asset_id", columnDefinition = "uuid")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "persona_id", nullable = false)
    private Persona persona;

    @Column(name = "original_name", nullable = false, length = 255)
    private String originalName;

    @Column(name = "filesystem_path", nullable = false, columnDefinition = "TEXT")
    private String filesystemPath;

    @CreationTimestamp
    @Column(name = "uploaded_at", nullable = false, updatable = false)
    private LocalDateTime uploadedAt;

    @Builder
    public PersonaVoiceAsset(Persona persona, String originalName, String filesystemPath) {
        this.persona = persona;
        this.originalName = originalName;
        this.filesystemPath = filesystemPath;
    }
}
