package org.liquid.scheduler.core.dags;

import javax.annotation.Nonnull;

/**
 * {@code Dag} 调度器，负责调度 {@code Dag} 运行。{@code Dag} 调度器并不执行 {@code Node}，
 * 而是将其提交给 {@code NodeDistributor} 发布。
 *
 * @author linckye 2018-07-31
 */
public interface DagScheduler {

    /** 对 {@code dag} 进行调度。**/
    void schedule(@Nonnull Dag dag);

    /** 关闭 {@code DagScheduler}。**/
    void stop();

    DagScheduler registerDAGScheduleListener(DagScheduleListener dagScheduleListener);

    DagScheduler registerNodeDistributionListener(NodeDistributionListener nodeDistributionListener);

}
