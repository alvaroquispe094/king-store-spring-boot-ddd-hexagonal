package com.groupal.king.store.application.exception;

import com.groupal.king.store.config.ErrorCode;
import com.groupal.king.store.config.GenericException;

public class EmailTakenException extends GenericException {

    public EmailTakenException(ErrorCode errorCode) {
        super(errorCode);
    }

    public EmailTakenException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public EmailTakenException(ErrorCode errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }

}
