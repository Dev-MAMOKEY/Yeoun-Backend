package com.mamokey.yeoun.domain.persona.repository;

import com.mamokey.yeoun.domain.persona.entity.PersonaPhotoAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Collection;
import java.util.UUID;

public interface PersonaPhotoAssetRepository extends JpaRepository<PersonaPhotoAsset, UUID> {
    @Modifying
    void deleteByPersonaIdIn(Collection<UUID> personaIds);
}
