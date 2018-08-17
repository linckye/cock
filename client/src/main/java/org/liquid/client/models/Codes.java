package org.liquid.client.models;

/**
 * {@code Response} 状态码.
 *
 * @author linckye 2018-08-16
 */
public interface Codes {

    /** 成功. **/
    int SUCCESS = 0;

    /** 未考虑到的异常. **/
    int UNEXPECTED_ERROR = -1;

    /** 参数非法. **/
    int ILLEGAL_ARGUMENT = 1;

    /** 资源未找到. **/
    int NOT_FOUND = 2;

}
