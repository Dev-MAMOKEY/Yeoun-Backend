package com.mamokey.yeoun.domain.persona.repository;

import com.mamokey.yeoun.domain.persona.entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PersonaRepository extends JpaRepository<Persona, UUID> {

    Optional<Persona> findByIdAndOwnerUserId(UUID id, Long ownerUserId);

    boolean existsByOwnerUserId(Long ownerUserId);
}
