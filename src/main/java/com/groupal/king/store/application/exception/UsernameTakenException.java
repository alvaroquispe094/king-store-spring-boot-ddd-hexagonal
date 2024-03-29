package com.groupal.king.store.application.exception;


import com.groupal.king.store.config.ErrorCode;
import com.groupal.king.store.config.GenericException;

public class UsernameTakenException extends GenericException {

    public UsernameTakenException(ErrorCode errorCode) {
        super(errorCode);
    }

    public UsernameTakenException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public UsernameTakenException(ErrorCode errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }

}
