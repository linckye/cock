package org.liquid.core.dag;

import java.util.EventListener;

/**
 * {@code Node} 发布监听器，负责监听 {@code Node}  的发布情况。
 *
 * @author linckye 2018-07-26
 */
public interface NodeDistributionListener
        extends EventListener {

    /** {@code Node} 完成时对 {@code dagSchedule} 和 {@code nodeDistribution} 产生回调。**/
    void onCompletion(DAGSchedule dagSchedule, NodeDistribution nodeDistribution);

    /** {@code Node} 失败时对 {@code dagSchedule}，{@code nodeDistribution} 和 {@code throwable} 产生回调。**/
    void onFailure(DAGSchedule dagSchedule, NodeDistribution nodeDistribution, Throwable throwable);

}
