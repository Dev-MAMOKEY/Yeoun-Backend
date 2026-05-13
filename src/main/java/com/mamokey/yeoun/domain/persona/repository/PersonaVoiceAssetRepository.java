package com.mamokey.yeoun.domain.persona.repository;

import com.mamokey.yeoun.domain.persona.entity.PersonaVoiceAsset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PersonaVoiceAssetRepository extends JpaRepository<PersonaVoiceAsset, UUID> {
}
