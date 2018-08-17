package org.liquid.client;

import org.liquid.client.models.dags.GetDagRequest;
import org.liquid.client.models.dags.GetDagResponse;

/**
 * @author linckye 2018-08-16
 */
public interface DagService {

    GetDagResponse get(GetDagRequest getDagRequest);

}
