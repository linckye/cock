package org.liquid.core.dag;

import java.util.EventListener;

/**
 * 对一次 Node 运行的监听器
 *
 * @author linckye 2018-07-26
 */
public interface NodeRunListener
        extends EventListener {

    /**
     * Node 运行成功时的回调
     *
     * @param dagRun 此次 DAG 调度中的 DAGRun
     * @param nodeRun 此次 Node 的 NodeRun
     */
    void onSuccess(DAGRun dagRun, NodeRun nodeRun);

    /**
     * Node 运行失败时的回调
     *
     * @param dagRun 此次 DAG 调度中的 DAGRun
     * @param nodeRun 此次 Node 的 NodeRun
     * @param throwable 此次 Node 的失败信息
     */
    void onFailure(DAGRun dagRun, NodeRun nodeRun, Throwable throwable);

}
