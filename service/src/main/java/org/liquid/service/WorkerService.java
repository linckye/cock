package org.liquid.service;

import org.liquid.service.model.RunNodeRequest;
import org.liquid.service.model.RunNodeResponse;

/**
 * @author linckye 2018-04-09
 */
public interface WorkerService {

    RunNodeResponse runNode(RunNodeRequest runNodeRequest);

}
