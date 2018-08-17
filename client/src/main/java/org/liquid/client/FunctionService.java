package org.liquid.client;

import org.liquid.client.models.functions.GetFunctionRequest;
import org.liquid.client.models.functions.GetFunctionResponse;

/**
 * @author linckye 2018-08-16
 */
public interface FunctionService {

    GetFunctionResponse get(GetFunctionRequest getFunctionRequest);

}
