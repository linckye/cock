package org.liquid.client.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author linckye 2018-05-16
 */
@Data
@Accessors(chain = true)
public class CallFunctionRequest
        implements Serializable {

    private static final long serialVersionUID = -3715644681165492245L;

    private String functionId;

}
