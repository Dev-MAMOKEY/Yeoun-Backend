package com.mamokey.yeoun.domain.persona.repository;

import com.mamokey.yeoun.domain.persona.entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface PersonaRepository extends JpaRepository<Persona, UUID> {

    Optional<Persona> findByIdAndOwnerUserId(UUID id, UUID ownerUserId);

    boolean existsByOwnerUserId(UUID ownerUserId);

    @Modifying
    @Query("DELETE FROM Persona p WHERE p.id = :id")
    void deleteByPersonaId(@Param("id") UUID id);
}
