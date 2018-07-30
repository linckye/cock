package org.liquid.core.dag;

import java.util.concurrent.ExecutorService;

/**
 * Node 发布者
 *
 * @author linckye 2018-07-27
 */
public interface NodeDistributor {

    void distribute(DAGRun dagRun, NodeRun nodeRun, ExecutorService distributedPool);

}
