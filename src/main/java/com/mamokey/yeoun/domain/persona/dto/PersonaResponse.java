package com.mamokey.yeoun.domain.persona.dto;

import com.mamokey.yeoun.domain.persona.entity.Persona;
import com.mamokey.yeoun.domain.persona.entity.PersonaStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "페르소나 응답")
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
