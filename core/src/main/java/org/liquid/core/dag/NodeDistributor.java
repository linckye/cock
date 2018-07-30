package org.liquid.core.dag;

/**
 * Node 发布者，负责发布一个 Node，并在 Node 运行时产生相应回调
 *
 * @author linckye 2018-07-27
 */
public interface NodeDistributor {

    /**
     * 发布一个 Node
     *
     * @param dagRun 待发布的 DAGRun
     * @param nodeRun 待发布的 NodeRun，在 Node 运行时会针对 NodeRunListener 产生相应回调
     */
    void distribute(DAGRun dagRun, NodeRun nodeRun);

}
