package org.liquid.core.dag;

import lombok.*;
import lombok.experimental.Accessors;
import org.joda.time.DateTime;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * {@code DAG} 的调度情况。
 *
 * @author linckye 2018-07-28
 */
@Data
@Accessors(chain = true, fluent = true)
public class DAGSchedule {

    DAG dag;

    /** 每个 {@code Node} 的 {@code NodeDistribution} 映射表。**/
    Map<Node, NodeDistribution> nodeDistributionMap;

    DateTime startTime;

    DateTime endTime;

    DAGScheduleStatus dagScheduleStatus;

    /** 到达结束节点的分支数量计数器，{@code DAG} 是否完全调度，应由最后到达分支决定。**/
    AtomicInteger endNodeBranchArrivalCounter;

    Set<DAGScheduleListener> dagScheduleListeners;

}
