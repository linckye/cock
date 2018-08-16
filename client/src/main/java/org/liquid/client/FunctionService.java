package org.liquid.client;

import org.liquid.client.model.functions.GetFunctionRequest;
import org.liquid.client.model.functions.GetFunctionResponse;

/**
 * @author linckye 2018-08-16
 */
public interface FunctionService {

    GetFunctionResponse get(GetFunctionRequest getFunctionRequest);

}
