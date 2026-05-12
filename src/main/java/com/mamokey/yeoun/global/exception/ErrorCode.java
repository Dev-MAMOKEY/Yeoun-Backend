package com.mamokey.yeoun.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // 요청 관련 예외 처리 (400)
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "잘못된 요청입니다."),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "INVALID_INPUT_VALUE", "입력값이 올바르지 않습니다."),
    VALIDATION_FAILED(HttpStatus.BAD_REQUEST, "VALIDATION_FAILED", "요청 데이터 검증에 실패했습니다."),
    MISSING_REQUIRED_VALUE(HttpStatus.BAD_REQUEST, "MISSING_REQUIRED_VALUE", "필수 입력값이 누락되었습니다."),
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "INVALID_PARAMETER", "요청 파라미터가 올바르지 않습니다."),
    INVALID_JSON_FORMAT(HttpStatus.BAD_REQUEST, "INVALID_JSON_FORMAT", "요청 본문의 JSON 형식이 올바르지 않습니다."),
    UNSUPPORTED_FILE_TYPE(HttpStatus.BAD_REQUEST, "UNSUPPORTED_FILE_TYPE", "지원하지 않는 파일 형식입니다."),
    FILE_SIZE_EXCEEDED(HttpStatus.BAD_REQUEST, "FILE_SIZE_EXCEEDED", "파일 크기가 허용 범위를 초과했습니다."),

    // 인증 관련 예외 처리 (401)
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "INVALID_CREDENTIALS", "아이디 또는 비밀번호가 올바르지 않습니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "TOKEN_EXPIRED", "Access Token이 만료되었습니다."),
    TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "TOKEN_INVALID", "토큰 형식이 잘못되었거나 서명이 유효하지 않습니다."),
    REFRESH_TOKEN_REVOKED(HttpStatus.UNAUTHORIZED, "REFRESH_TOKEN_REVOKED", "이미 무효화된 Refresh Token입니다."),
    PASSWORD_CONFIRMATION_MISMATCH(HttpStatus.UNAUTHORIZED, "PASSWORD_MISMATCH", "현재 비밀번호가 일치하지 않습니다."),

    // 404 (리소스 찾을 수 없을 때 예외 처리)
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "ENTITY_NOT_FOUND", "요청한 리소스를 찾을 수 없습니다."),

    // 405 (HTTP 메서드 관련 예외 처리)
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "METHOD_NOT_ALLOWED", "지원하지 않는 HTTP 메서드입니다."),

    // 서버 관련 예외 처리 (500)
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "서버 내부 오류가 발생했습니다."),
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "DATABASE_ERROR", "데이터베이스 처리 중 오류가 발생했습니다."),
    FILE_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "FILE_UPLOAD_FAILED", "파일 업로드 중 오류가 발생했습니다."),
    FILE_READ_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "FILE_READ_FAILED", "파일을 읽는 중 오류가 발생했습니다."),
    AI_SERVER_REQUEST_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "AI_SERVER_REQUEST_FAILED", "AI 서버 요청 중 오류가 발생했습니다."),
    EXTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "EXTERNAL_SERVER_ERROR", "외부 서버 연동 중 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
