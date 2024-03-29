package com.groupal.king.store.config;

public enum ErrorCode {

    WEB_CLIENT_GENERIC(103,"103", "Unexpected rest client error", "MS-SEED-INTERNAL_SERVER_ERROR"),
    NOT_FOUND_EXCEPTION(104, "104", "No se encontro el recurso", "NOT_FOUND_EXCEPTION"),
    BAD_REQUEST(105,"105", "La request esta mal formateada", "BAD_REQUEST"),
    INVALID_FILTERS_ERROR(107,"107", "Invalid filters", "MS-SEED-INVALID_FILTERS"),
    INTERNAL_ERROR(108,"108","Internal Error","MS-SEED-INTERNAL_ERROR"),
    KAFKA_EXCEPTION(109,"109", "Error interno de kafka", "MS-SEED-KAFKA_EXCEPTION"),
    INVALID_PARAMETERS_ERROR(110,"110", "{}", "MS-SEED-INVALID_PARAMETERS"),
    DATA_ACCESS_ERROR(111,"111", "Unable to access Account data", "MS-SEED-DATA_ACCESS_ERROR"),
    ESTABLISHMENT_SERVICE_ERROR(112,"112", "Establishment service error", "MS-SEED-ESTABLISHMENT_SERVICE_ERROR"),
    AMOUNT_INVALID(113,"113", "Establishment service error", "MS-SEED-ESTABLISHMENT_SERVICE_ERROR"),

    // Custom errors ecommerce
    GENERATE_TOKEN_ERROR(114,"114", "Generate token error", "generate_token_114"),
    INVALID_USERNAME_TAKEN(115,"115", "Username already taken", "username_already_taken_115"),
    INVALID_EMAIL_TAKEN(116,"116", "Email already taken", "email_already_taken_116"),
    USER_ROLE_NOT_FOUND(117,"117", "Role is not found.", "role_not_found_117"),

    ;

    private final int value;
    private final String status;
    private final String detail;
    private final String code;

    ErrorCode(int value,String status, String detail, String code) {
        this.value = value;
        this.status = status;
        this.detail = detail;
        this.code = code;
    }

    public int value() {
        return this.value;
    }

    public  String getStatus(){return this.status;}

    public String getDetail() {
        return this.detail;
    }

    public String getCode() {
        return this.code;
    }
}