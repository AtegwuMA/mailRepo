package com.martins.mailExample.enums;

public enum ErrorCodes {
    UNKNOWN_ERROR("01", "An unknown error occurred"),
    VALIDATION_ERROR("02", "Validation error"),
    APP_REQUEST_ERROR("03", "Application request error"),
    RESOURCE_NOT_FOUND("04", "Resource not found");

    private final String code;
    private final String description;

    ErrorCodes(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}