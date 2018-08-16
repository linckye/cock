package org.liquid.scheduler.core.dags;

/**
 * {@code Node} 的发布情况。
 *
 * @author linckye 2018-07-28
 */
public enum NodeDistributionStatus {

    /** 已发布。**/
    DISTRIBUTED,

    /** 完成。**/
    FINISHED,

    /** 失败。**/
    FAILED

}
