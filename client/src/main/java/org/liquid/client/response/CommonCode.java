package org.liquid.client.response;

import lombok.Getter;

/**
 * 通用响应状态码
 *
 * @author linckye 2018-05-16
 */
@Getter
public enum CommonCode {

    /**
     * 成功
     */
    SUCCESS(0),

    /**
     * 未知错误
     */
    UNEXPECTED_ERROR(-1)
    ;

    CommonCode(int code) {
        this.code = code;
    }

    private int code;

}
