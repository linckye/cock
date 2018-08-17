package org.liquid.client.models.functions;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author linckye 2018-08-16
 */
@Data
@Accessors(chain = true, fluent = true)
public class FunctionDescription implements Serializable {

    private String functionName;

}
