package org.liquid.core.dag;

import org.liquid.core.dag.DAGRun;
import org.liquid.core.dag.NodeRun;

import java.util.EventListener;

/**
 * @author linckye 2018-07-26
 */
public interface NodeRunListener
        extends EventListener {

    void onSuccess(DAGRun dagRun, NodeRun nodeRun);

    void onFailure(DAGRun dagRun, NodeRun nodeRun, Throwable throwable);

}
