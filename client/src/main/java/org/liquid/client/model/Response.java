package org.liquid.client.model;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

import static org.liquid.client.model.Codes.*;

/**
 * {@code Request} 响应.
 *
 * @author linckye 2018-08-16
 */
@ToString
@EqualsAndHashCode
@Getter
@Accessors(chain = true, fluent = true)
public class Response implements Serializable {

    /** 状态码. **/
    private Integer code;

    /** 状态码额外说明 **/
    private String message;

    public <T extends Response> T code(Integer code) {
        this.code = code;
        @SuppressWarnings("unchecked")
        T t = (T) this;
        return t;
    }

    public <T extends Response> T message(String message) {
        this.message = message;
        @SuppressWarnings("unchecked")
        T t = (T) this;
        return t;
    }

    public boolean successful() {
        return code == SUCCESS;
    }

    public boolean failed() {
        return code != SUCCESS;
    }

    public static <T extends Response> T failure(Response other) {
        Response response = new Response();
        response.code = other.code;
        response.message = other.message;
        @SuppressWarnings("unchecked")
        T t = (T) response;
        return t;
    }

}
