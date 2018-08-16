package org.liquid.client.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

import static org.liquid.client.model.Codes.*;

/**
 * {@code Request} 响应.
 *
 * @author linckye 2018-08-16
 */
@Data
@Accessors(chain = true, fluent = true)
public class Response implements Serializable {

    /** 状态码. **/
    private Integer code;

    /** 状态码额外说明 **/
    private String message;

    public boolean successful() {
        return code == SUCCESS;
    }

    public boolean failed() {
        return code != SUCCESS;
    }

}
