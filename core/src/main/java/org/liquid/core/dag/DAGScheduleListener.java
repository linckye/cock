package org.liquid.core.dag;

import java.util.EventListener;

/**
 * {@code DAG} 调度监听器，负责监听 {@code DAG} 的调度情况。
 *
 * @author linckye 2018-07-29
 */
public interface DAGScheduleListener
        extends EventListener {

    /** {@code DAG} 调度成功时对 {@code dagSchedule} 产生回调。**/
    void onSuccess(DAGSchedule dagSchedule);

    /** {@code DAG} 调度失败时对 {@code dagSchedule} 产生回调。**/
    void onFailure(DAGSchedule dagSchedule);

}
