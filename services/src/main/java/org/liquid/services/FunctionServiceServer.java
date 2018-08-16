package org.liquid.services;

import lombok.Data;
import lombok.experimental.Accessors;
import org.liquid.client.FunctionService;
import org.liquid.client.model.functions.GetFunctionRequest;
import org.liquid.client.model.functions.GetFunctionResponse;
import org.springframework.stereotype.Service;

/**
 * @author linckye 2018-08-16
 */
@Service
@Data
@Accessors(chain = true, fluent = true)
public class FunctionServiceServer
        implements FunctionService {

    @Override
    public GetFunctionResponse get(GetFunctionRequest getFunctionRequest) {
        // TODO
        return null;
    }

}
