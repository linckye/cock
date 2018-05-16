package org.liquid.service;

import org.liquid.client.FunctionService;
import org.liquid.client.model.CallFunctionRequest;
import org.liquid.client.model.CallFunctionResponse;
import org.liquid.client.response.Response;
import org.springframework.stereotype.Service;

/**
 * 函数相关服务实现
 *
 * @author linckye 2018-05-16
 */
@Service
public class FunctionServiceImpl implements FunctionService {

    @Override
    public Response<? extends CallFunctionResponse> callFunction(CallFunctionRequest callFunctionRequest) {
        // TODO

        return null;
    }

}
