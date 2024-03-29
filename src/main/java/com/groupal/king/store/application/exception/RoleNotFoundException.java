package com.groupal.king.store.application.exception;


import com.groupal.king.store.config.ErrorCode;
import com.groupal.king.store.config.GenericException;

public class RoleNotFoundException extends GenericException {

    public RoleNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public RoleNotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public RoleNotFoundException(ErrorCode errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }

}
