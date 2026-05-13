package com.mamokey.yeoun.domain.persona.repository;

import com.mamokey.yeoun.domain.persona.entity.PersonaInterview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PersonaInterviewRepository extends JpaRepository<PersonaInterview, UUID> {
}
