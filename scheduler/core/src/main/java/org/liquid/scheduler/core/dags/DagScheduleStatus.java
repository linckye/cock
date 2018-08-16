package org.liquid.scheduler.core.dags;

/**
 * {@code Dag} 的调度情况。
 *
 * @author linckye 2018-07-28
 */
public enum DagScheduleStatus {

    /** 调度中。**/
    SCHEDULING,

    /** 完成。**/
    FINISHED,

    /** 失败。**/
    FAILED

}
