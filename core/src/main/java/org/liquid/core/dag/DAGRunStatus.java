package org.liquid.core.dag;

/**
 * DAGRun 状态
 *
 * @author linckye 2018-07-28
 */
public enum DAGRunStatus {

    /**
     * 运行中
     */
    RUNNING,

    /**
     * 完成
     */
    FINISHED,

    /**
     * 失败
     */
    FAILED
    ;

}
