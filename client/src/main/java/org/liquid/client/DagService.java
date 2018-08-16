package org.liquid.client;

import org.liquid.client.model.dags.GetDagRequest;
import org.liquid.client.model.dags.GetDagResponse;

/**
 * @author linckye 2018-08-16
 */
public interface DagService {

    GetDagResponse get(GetDagRequest getDagRequest);

}
