package com.todomanager.api.common.definition;

public enum ErrorCode {

    AUTHENTICATION_FAILURE("AUTHENTICATION_FAILURE", "Geçersiz kullanıcı adı veya şifre"),
    VALIDATION_FAILURE("VALIDATION_ERROR", "Geçersiz istek"),
    DUPLICATE_DATA_FAILURE("DUPLICATE_FAILURE", "Bu veri daha önce kaydedilmiş."),
    NOT_FOUND("NOT_FOUND", "Kayıt bulunamadı.");

    ErrorCode(String validationError, String s) {
    }
}