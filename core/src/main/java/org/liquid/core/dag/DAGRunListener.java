package org.liquid.core.dag;

import java.util.EventListener;

/**
 * @author linckye 2018-07-29
 */
public interface DAGRunListener extends EventListener {

    void onSuccess(DAGRun dagRun);

    void onFailure(DAGRun dagRun);

}
