package org.liquid.core.dag;

/**
 * {@code DAG} 调度器，负责调度 {@code DAG} 运行。{@code DAG} 调度器并不执行 {@code Node}，
 * 而是将其提交给 {@code NodeDistributor} 发布。
 *
 * @author linckye 2018-07-31
 */
public interface DAGScheduler {

    /** 针对参数 {@code dagScheduleParameters} 对 {@code DAG} 进行调度。**/
    void schedule(DAGScheduleParameters dagScheduleParameters);

    /** 关闭 {@code DAGScheduler}。**/
    void shutdown();

    /** 注册 {@code nodeDistributor}，负责 {@code DAGScheduler} 中 {@code Node} 的发布。**/
    void registerDistributor(NodeDistributor nodeDistributor);

    /** 获取 {@code NodeDistributor}。**/
    NodeDistributor nodeDistributor();

}
