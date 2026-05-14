package com.mamokey.yeoun.domain.persona.dto;

import com.mamokey.yeoun.domain.persona.entity.PersonaIdleClip;

public record IdleClipResponse(
        int idx
) {
    public static IdleClipResponse from(PersonaIdleClip clip) {
        return new IdleClipResponse(clip.getSequenceOrder());
    }
}
