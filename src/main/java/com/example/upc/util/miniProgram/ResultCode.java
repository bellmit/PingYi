package com.example.upc.util.miniProgram;

import lombok.Getter;

@Getter
public enum ResultCode implements StatusCode{
    SUCCESS(200, "请求成功"),
    FAILED(201, "请求失败"),
    VALIDATE_ERROR(202, "参数校验失败"),
    RESPONSE_PACK_ERROR(203, "response返回包装失败");

    private int code;
    private String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
