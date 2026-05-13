package com.mamokey.yeoun.domain.persona.dto;

import com.mamokey.yeoun.domain.persona.entity.Persona;
import com.mamokey.yeoun.domain.persona.entity.PersonaStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record PersonaResponse(
        UUID id,
        String name,
        String nickname,
        PersonaStatus status,
        LocalDateTime createdAt
) {
    public static PersonaResponse from(Persona persona) {
        return new PersonaResponse(
                persona.getId(),
                persona.getName(),
                persona.getNickname(),
                persona.getStatus(),
                persona.getCreatedAt()
        );
    }
}
