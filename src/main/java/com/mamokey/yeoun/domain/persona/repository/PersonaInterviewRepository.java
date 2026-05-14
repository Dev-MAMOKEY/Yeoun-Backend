package com.mamokey.yeoun.domain.persona.repository;

import com.mamokey.yeoun.domain.persona.entity.PersonaInterview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Collection;
import java.util.UUID;

public interface PersonaInterviewRepository extends JpaRepository<PersonaInterview, UUID> {
    @Modifying
    void deleteByPersonaIdIn(Collection<UUID> personaIds);
}
