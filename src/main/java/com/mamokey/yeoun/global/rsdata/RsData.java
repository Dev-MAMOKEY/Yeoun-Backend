package com.mamokey.yeoun.global.rsdata;

import java.time.LocalDateTime;

public record RsData<T>(
    boolean success,
    T data,
    ErrorInfo error,
    LocalDateTime timestamp
) {
    public record ErrorInfo(
        String code,
        String message
    ) {
    }

    public static <T> RsData<T> success(T data) {
        return new RsData<>(true, data, null, LocalDateTime.now());
    }

    public static <T> RsData<T> fail(ErrorCode errorCode) {
        return new RsData<>(false, null, new ErrorInfo(errorCode.getCode(), errorCode.getMessage()), LocalDateTime.now());
    }
}
