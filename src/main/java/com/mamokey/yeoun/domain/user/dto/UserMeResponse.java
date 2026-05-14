package com.mamokey.yeoun.domain.user.dto;

import com.mamokey.yeoun.domain.persona.entity.Persona;
import com.mamokey.yeoun.domain.persona.entity.PersonaStatus;
import com.mamokey.yeoun.domain.user.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record UserMeResponse(
        UUID id,
        String email,
        List<PersonaSummary> personas
) {
    public static UserMeResponse of(User user, List<Persona> personas) {
        return new UserMeResponse(
                user.getId(),
                user.getEmail(),
                personas.stream()
                        .map(PersonaSummary::from)
                        .toList()
        );
    }

    public record PersonaSummary(
            UUID id,
            String name,
            String nickname,
            PersonaStatus status,
            LocalDateTime createdAt
    ) {
        public static PersonaSummary from(Persona persona) {
            return new PersonaSummary(
                    persona.getId(),
                    persona.getName(),
                    persona.getNickname(),
                    persona.getStatus(),
                    persona.getCreatedAt()
            );
        }
    }
}
