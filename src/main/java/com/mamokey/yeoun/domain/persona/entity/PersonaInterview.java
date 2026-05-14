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
@Table(name = "persona_interviews")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PersonaInterview {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "interview_id", columnDefinition = "uuid")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "persona_id", nullable = false)
    private Persona persona;

    @Column(name = "question_number", nullable = false)
    private Integer questionNumber;

    @Column(name = "answer_text", columnDefinition = "TEXT")
    private String answerText;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public PersonaInterview(Persona persona, Integer questionNumber, String answerText) {
        this.persona = persona;
        this.questionNumber = questionNumber;
        this.answerText = answerText;
    }
}
