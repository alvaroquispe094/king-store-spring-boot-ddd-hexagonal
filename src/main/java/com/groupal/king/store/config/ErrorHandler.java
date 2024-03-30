package com.groupal.king.store.config;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.groupal.king.store.adapter.security.exception.TokenRefreshException;
import com.groupal.king.store.application.exception.*;
import com.groupal.king.store.domain.ErrorMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ValidationException;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
@ControllerAdvice
public class ErrorHandler {

    private final HttpServletRequest httpServletRequest;

    public ErrorHandler(final HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    @ExceptionHandler(value = TokenRefreshException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorMessage handleTokenRefreshException(TokenRefreshException ex, WebRequest request) {
        return new ErrorMessage(
                HttpStatus.FORBIDDEN.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResponse> handle(Throwable ex) {
        log.error("ErrorCode="+HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), ex);
        return buildResponseError(HttpStatus.INTERNAL_SERVER_ERROR,ex, ErrorCode.INTERNAL_ERROR);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handle(ValidationException ex) {
        log.error("ErrorCode="+HttpStatus.BAD_REQUEST.getReasonPhrase(), ex);
        return buildResponseError(HttpStatus.BAD_REQUEST,ex, ErrorCode.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException ex) {
        log.error("ErrorCode="+HttpStatus.BAD_REQUEST.getReasonPhrase(), ex);
        return buildResponseError(HttpStatus.BAD_REQUEST,ex, ErrorCode.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handle(HttpMessageNotReadableException ex) {
        log.error("ErrorCode="+HttpStatus.BAD_REQUEST.getReasonPhrase(), ex);
        return buildResponseError(HttpStatus.BAD_REQUEST,ex, ErrorCode.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handle(MissingServletRequestParameterException ex) {
        log.error("ErrorCode="+ErrorCode.INVALID_PARAMETERS_ERROR.getCode(), ex);
        return buildResponseError(HttpStatus.BAD_REQUEST,ex, ErrorCode.INVALID_PARAMETERS_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handle(NotFoundException ex) {
        log.error("ErrorCode="+ErrorCode.NOT_FOUND_EXCEPTION.getCode(), ex);
        return buildResponseError(HttpStatus.BAD_REQUEST, ex, ErrorCode.NOT_FOUND_EXCEPTION);
    }

    @ExceptionHandler(GenerateTokenException.class)
    public ResponseEntity<ErrorResponse> handle(GenerateTokenException ex) {
        log.error("ErrorCode="+ErrorCode.GENERATE_TOKEN_ERROR.getCode(), ex);
        return buildResponseError(HttpStatus.BAD_REQUEST, ex, ErrorCode.GENERATE_TOKEN_ERROR);
    }

    @ExceptionHandler(UsernameTakenException.class)
    public ResponseEntity<ErrorResponse> handle(UsernameTakenException ex) {
        log.error("ErrorCode="+ErrorCode.INVALID_USERNAME_TAKEN.getCode(), ex);
        return buildResponseError(HttpStatus.BAD_REQUEST, ex, ErrorCode.INVALID_USERNAME_TAKEN);
    }

    @ExceptionHandler(EmailTakenException.class)
    public ResponseEntity<ErrorResponse> handle(EmailTakenException ex) {
        log.error("ErrorCode="+ErrorCode.INVALID_EMAIL_TAKEN.getCode(), ex);
        return buildResponseError(HttpStatus.BAD_REQUEST, ex, ErrorCode.INVALID_EMAIL_TAKEN);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ErrorResponse> handle(RoleNotFoundException ex) {
        log.error("ErrorCode="+ErrorCode.USER_ROLE_NOT_FOUND.getCode(), ex);
        return buildResponseError(HttpStatus.BAD_REQUEST, ex, ErrorCode.USER_ROLE_NOT_FOUND);
    }


    @Builder
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class ErrorResponse {

        private static final String DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss[.SSSSSSSSS]['Z']";

        @JsonProperty
        private String name;
        @JsonProperty
        private Integer status;

        @JsonProperty
        private Integer code;

        @JsonProperty
        String errorCode;

        @JsonProperty
        String errorDescription;



        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATETIME_FORMAT)
        private LocalDateTime timestamp;

        @JsonProperty
        private String resource;

        @JsonProperty
        private String detail;
    }

    private ResponseEntity<ErrorResponse> buildResponseError(
            HttpStatus httpStatus,
            Throwable ex,
            ErrorCode errorCode
    ) {

        final ErrorResponse apiErrorResponse = ErrorResponse
                .builder()
                .timestamp(LocalDateTime.now())
                .name(httpStatus.getReasonPhrase())
                .detail(String.format("%s: %s", ex.getClass().getCanonicalName(), ex.getMessage()))
                .status(httpStatus.value())
                .code(errorCode.value())
                .resource(httpServletRequest.getRequestURI())
                .errorDescription(errorCode.getDetail())
                .errorCode(errorCode.getCode())
                .build();

        return new ResponseEntity<>(apiErrorResponse, httpStatus);
    }

}

