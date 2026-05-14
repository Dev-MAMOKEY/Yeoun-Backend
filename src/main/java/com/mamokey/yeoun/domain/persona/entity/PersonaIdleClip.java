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
@Table(name = "persona_idle_clips")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PersonaIdleClip {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "clip_id", columnDefinition = "uuid")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "persona_id", nullable = false)
    private Persona persona;

    @Column(name = "sequence_order", nullable = false)
    private Integer sequenceOrder;

    @Column(name = "filesystem_path", nullable = false, columnDefinition = "TEXT")
    private String filesystemPath;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public PersonaIdleClip(Persona persona, Integer sequenceOrder, String filesystemPath) {
        this.persona = persona;
        this.sequenceOrder = sequenceOrder;
        this.filesystemPath = filesystemPath;
    }
}
