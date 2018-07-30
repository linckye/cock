package org.liquid.core.dag;

import java.util.EventListener;

/**
 * 对一次 DAG 调度的监听器
 *
 * @author linckye 2018-07-29
 */
public interface DAGRunListener
        extends EventListener {

    /**
     * DAG 调度成功时的回调
     *
     * @param dagRun 此次 DAG 调度中的 DAGRun
     */
    void onSuccess(DAGRun dagRun);

    /**
     * DAG 调度失败时的回调
     *
     * @param dagRun 此次 DAG 调度中的 DAGRun
     */
    void onFailure(DAGRun dagRun);

}
