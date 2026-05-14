package com.mamokey.yeoun.domain.persona.repository;

import com.mamokey.yeoun.domain.persona.entity.PersonaConsent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.UUID;

public interface PersonaConsentRepository extends JpaRepository<PersonaConsent, UUID> {
    @Modifying
    void deleteByUserId(UUID userId);
}
