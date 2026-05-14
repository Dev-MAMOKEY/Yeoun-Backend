package com.mamokey.yeoun.infra.fastapi;

import com.fasterxml.jackson.annotation.JsonProperty;

public record FastApiPhotoUploadResponse(
        String path,
        @JsonProperty("size_bytes") long sizeBytes
) {
}
