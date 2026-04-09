package com.klu.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.Map;

@Schema(description = "Standard error payload returned by APIs")
public class ErrorResponse {

    @Schema(example = "404")
    private int status;

    @Schema(example = "Not Found")
    private String error;

    @Schema(example = "Student not found with id: 999")
    private String message;

    @Schema(example = "/students/999")
    private String path;

    @Schema(example = "2026-04-09T13:35:21")
    private LocalDateTime timestamp;

    @Schema(description = "Field-level validation errors")
    private Map<String, String> validationErrors;

    public ErrorResponse() {
    }

    public ErrorResponse(int status, String error, String message, String path, LocalDateTime timestamp, Map<String, String> validationErrors) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.timestamp = timestamp;
        this.validationErrors = validationErrors;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Map<String, String> getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(Map<String, String> validationErrors) {
        this.validationErrors = validationErrors;
    }
}
