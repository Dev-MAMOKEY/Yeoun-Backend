package com.mamokey.yeoun.domain.persona.repository;

import com.mamokey.yeoun.domain.persona.entity.PersonaIdleClip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PersonaIdleClipRepository extends JpaRepository<PersonaIdleClip, UUID> {

    List<PersonaIdleClip> findAllByPersonaIdOrderBySequenceOrder(UUID personaId);

    Optional<PersonaIdleClip> findByPersonaIdAndSequenceOrder(UUID personaId, int sequenceOrder);
}
