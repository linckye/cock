package org.liquid.scheduler.core.dags;

import lombok.Data;
import lombok.experimental.Accessors;
import org.joda.time.DateTime;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * {@code Node} 的发布情况。
 *
 * @author linckye 2018-07-26
 */
@Data
@Accessors(chain = true, fluent = true)
public class NodeDistribution {

    private Node node;

    private DateTime startTime;

    private DateTime endTime;

    private NodeDistributionStatus nodeDistributionStatus;

    private Throwable throwable;

    /** 到达 {@code Node} 的分支数量计数器，{@code Dag} 中多分支合并为单分支时的合并节点的调度，应由最后到达分支决定。**/
    private AtomicInteger branchArrivalCounter;

    private List<NodeDistributionListener> nodeDistributionListeners;

}
