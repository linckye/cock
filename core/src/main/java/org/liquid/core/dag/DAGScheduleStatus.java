package org.liquid.core.dag;

/**
 * {@code DAG} 的调度情况。
 *
 * @author linckye 2018-07-28
 */
public enum DAGScheduleStatus {

    /** 调度中。**/
    SCHEDULING,

    /** 完成。**/
    FINISHED,

    /** 失败。**/
    FAILED

}
