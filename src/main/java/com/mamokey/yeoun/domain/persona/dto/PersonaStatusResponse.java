package com.mamokey.yeoun.domain.persona.dto;

import com.mamokey.yeoun.domain.persona.entity.Persona;
import com.mamokey.yeoun.domain.persona.entity.PersonaStatus;

import java.util.UUID;

public record PersonaStatusResponse(
        UUID id,
        PersonaStatus status
) {
    public static PersonaStatusResponse from(Persona persona) {
        return new PersonaStatusResponse(persona.getId(), persona.getStatus());
    }
}
