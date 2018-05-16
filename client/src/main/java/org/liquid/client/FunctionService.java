package org.liquid.client;

import org.liquid.client.model.CallFunctionRequest;
import org.liquid.client.model.CallFunctionResponse;
import org.liquid.client.response.Response;

/**
 * 函数相关服务
 *
 * @author linckye 2018-05-16
 */
public interface FunctionService {

    /**
     * 调用函数
     *
     * @param callFunctionRequest 调用函数请求
     * @return 调用函数响应
     */
    Response<? extends CallFunctionResponse> callFunction(CallFunctionRequest callFunctionRequest);

}
