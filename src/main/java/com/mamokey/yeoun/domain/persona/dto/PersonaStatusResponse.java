package com.mamokey.yeoun.domain.persona.dto;

import com.mamokey.yeoun.domain.persona.entity.Persona;
import com.mamokey.yeoun.domain.persona.entity.PersonaStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "페르소나 상태 응답")
public record PersonaStatusResponse(
        UUID id,
        PersonaStatus status
) {
    public static PersonaStatusResponse from(Persona persona) {
        return new PersonaStatusResponse(persona.getId(), persona.getStatus());
    }
}
