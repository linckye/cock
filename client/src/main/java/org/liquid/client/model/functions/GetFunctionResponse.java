package org.liquid.client.model.functions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.liquid.client.model.Response;

import java.io.Serializable;

/**
 * @author linckye 2018-08-16
 */
@Data
@Accessors(chain = true, fluent = true)
@EqualsAndHashCode(callSuper = true)
public class GetFunctionResponse
        extends Response
        implements Serializable {

    private FunctionDescription functionDescription;

}
