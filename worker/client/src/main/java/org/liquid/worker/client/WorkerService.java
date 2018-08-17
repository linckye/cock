package org.liquid.worker.client;

import org.liquid.worker.client.models.CallFunctionRequest;
import org.liquid.worker.client.models.CallFunctionResponse;

/**
 * @author linckye 2018-08-09
 */
public interface WorkerService {

    CallFunctionResponse call(CallFunctionRequest callFunctionRequest);

}
