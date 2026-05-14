package com.mamokey.yeoun.infra.fastapi;

public record FastApiPhotoUploadResponse(
        String path,
        long sizeBytes
) {
}
