package com.mamokey.yeoun.infra.fastapi;

public record FastApiResponse<T>(boolean success, T data, Object error) {}
