package org.liquid.client.models;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

import static org.liquid.client.models.Codes.*;

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
    private Integer code = SUCCESS;

    /** 状态码额外说明 **/
    private String message;

    public boolean successful() {
        return code == SUCCESS;
    }

    public boolean failed() {
        return code != SUCCESS;
    }

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

    public <T extends Response> T from(Response other) {
        this.code = other.code;
        this.message = other.message;
        @SuppressWarnings("unchecked")
        T t = (T) this;
        return t;
    }

}
