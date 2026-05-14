package com.mamokey.yeoun.domain.persona.dto;

import com.mamokey.yeoun.domain.persona.entity.PersonaIdleClip;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Idle 영상 응답")
public record IdleClipResponse(
        int idx
) {
    public static IdleClipResponse from(PersonaIdleClip clip) {
        return new IdleClipResponse(clip.getSequenceOrder());
    }
}
