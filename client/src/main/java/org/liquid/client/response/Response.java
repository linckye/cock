package org.liquid.client.response;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 服务响应
 *
 * @author linckye 2018-05-16
 */
@Data
@Accessors(chain = true)
public class Response<D> implements Serializable {

    private static final long serialVersionUID = 2534513788746062452L;

    /**
     * 响应码
     */
    Integer code;

    /**
     * 响应消息
     */
    String message;

    /**
     * 响应数据
     */
    D data;

}
