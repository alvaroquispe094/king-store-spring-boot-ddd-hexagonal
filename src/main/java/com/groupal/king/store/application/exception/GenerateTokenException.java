package com.groupal.king.store.application.exception;

import com.groupal.king.store.config.ErrorCode;
import com.groupal.king.store.config.GenericException;

public class GenerateTokenException extends GenericException {

    public GenerateTokenException(ErrorCode errorCode) {
        super(errorCode);
    }

    public GenerateTokenException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public GenerateTokenException(ErrorCode errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }

}
