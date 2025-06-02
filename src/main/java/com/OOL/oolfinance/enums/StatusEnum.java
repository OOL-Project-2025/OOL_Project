package com.OOL.oolfinance.enums;

import lombok.Getter;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : StatusEnum
 * @date : 2025. 5. 31. / 오후 10:37
 * @modifyed :
 **/

@Getter
public enum StatusEnum {
    OK(200, "OK"),
    BAD_REQUEST(400, "BAD_REQUEST"),
    NOT_FOUND(404, "NOT_FOUND"),
    CONFLICT(409, "CONFLICT"),
    UNAUTHORIZED(401, "UNAUTHORIZED"),
    INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR"),
    FORBIDDEN(403, "FORBIDDEN");


    int statusCode;
    String code;

    StatusEnum(int statusCode, String code) {
        this.statusCode = statusCode;
        this.code = code;
    }


}
