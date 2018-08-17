package org.liquid.worker.services;

import org.liquid.worker.client.WorkerService;
import org.liquid.worker.client.models.CallFunctionRequest;
import org.liquid.worker.client.models.CallFunctionResponse;
import org.springframework.stereotype.Service;

/**
 * @author linckye 2018-08-16
 */
@Service
public class WorkerServiceServer
        implements WorkerService {

    @Override
    public CallFunctionResponse call(CallFunctionRequest callFunctionRequest) {
        return null;
    }

}
