package org.liquid.client.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author linckye 2018-05-16
 */
@Data
@Accessors(chain = true)
public class CallFunctionResponse
        implements Serializable {

    private static final long serialVersionUID = -2763351808058277117L;

    private String callFunctionId;

}
