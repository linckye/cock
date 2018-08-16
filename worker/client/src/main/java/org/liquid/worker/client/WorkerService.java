package org.liquid.worker.client;

import org.liquid.worker.client.model.CallFunctionRequest;
import org.liquid.worker.client.model.CallFunctionResponse;

/**
 * @author linckye 2018-08-09
 */
public interface WorkerService {

    CallFunctionResponse call(CallFunctionRequest callFunctionRequest);

}
