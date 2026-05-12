package com.mamokey.yeoun.global.exception;

import com.mamokey.yeoun.global.rsdata.RsData;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 요청 관련 예외 처리 (400)
    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            BindException.class,
            ConstraintViolationException.class
    })
    public ResponseEntity<RsData<Void>> handleValidationException(Exception exception) {
        return ResponseEntity
                .status(ErrorCode.VALIDATION_FAILED.getStatus())
                .body(RsData.fail(ErrorCode.VALIDATION_FAILED));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<RsData<Void>> handleMissingServletRequestParameter(
            MissingServletRequestParameterException exception
    ) {
        return ResponseEntity
                .status(ErrorCode.MISSING_REQUIRED_VALUE.getStatus())
                .body(RsData.fail(ErrorCode.MISSING_REQUIRED_VALUE));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<RsData<Void>> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException exception
    ) {
        return ResponseEntity
                .status(ErrorCode.INVALID_PARAMETER.getStatus())
                .body(RsData.fail(ErrorCode.INVALID_PARAMETER));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<RsData<Void>> handleHttpMessageNotReadable(
            HttpMessageNotReadableException exception
    ) {
        return ResponseEntity
                .status(ErrorCode.INVALID_JSON_FORMAT.getStatus())
                .body(RsData.fail(ErrorCode.INVALID_JSON_FORMAT));
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<RsData<Void>> handleMaxUploadSizeExceeded(
            MaxUploadSizeExceededException exception
    ) {
        return ResponseEntity
                .status(ErrorCode.FILE_SIZE_EXCEEDED.getStatus())
                .body(RsData.fail(ErrorCode.FILE_SIZE_EXCEEDED));
    }

    // 인증 관련 예외 처리 (401)
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<RsData<Void>> handleBadCredentials(BadCredentialsException exception) {
        return ResponseEntity
                .status(ErrorCode.INVALID_CREDENTIALS.getStatus())
                .body(RsData.fail(ErrorCode.INVALID_CREDENTIALS));
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<RsData<Void>> handleJwtException(JwtException exception) {
        return ResponseEntity
                .status(ErrorCode.TOKEN_INVALID.getStatus())
                .body(RsData.fail(ErrorCode.TOKEN_INVALID));
    }

    // 존재하지 않는 리소스 예외 처리 (404)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<RsData<Void>> handleEntityNotFound(EntityNotFoundException exception) {
        return ResponseEntity
                .status(ErrorCode.ENTITY_NOT_FOUND.getStatus())
                .body(RsData.fail(ErrorCode.ENTITY_NOT_FOUND));
    }

    // 허용되지 않은 HTTP 메서드 예외 처리 (405)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<RsData<Void>> handleMethodNotSupported(
            HttpRequestMethodNotSupportedException exception
    ) {
        return ResponseEntity
                .status(ErrorCode.METHOD_NOT_ALLOWED.getStatus())
                .body(RsData.fail(ErrorCode.METHOD_NOT_ALLOWED));
    }

    // 커스텀 예외 처리
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<RsData<Void>> handleCustomException(CustomException exception) {
        return ResponseEntity
                .status(exception.getErrorCode().getStatus())
                .body(RsData.fail(exception.getErrorCode()));
    }

    // 서버 에러 (500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<RsData<Void>> handleException(Exception exception) {
        log.error("[500 Internal Server Error] {}: {}",
                exception.getClass().getName(),
                exception.getMessage(),
                exception
        );

        return ResponseEntity
                .status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus())
                .body(RsData.fail(ErrorCode.INTERNAL_SERVER_ERROR));
    }
}
