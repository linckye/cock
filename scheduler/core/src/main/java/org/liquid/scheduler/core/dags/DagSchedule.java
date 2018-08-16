package org.liquid.scheduler.core.dags;

import lombok.Data;
import lombok.experimental.Accessors;
import org.joda.time.DateTime;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * {@code Dag} 的调度情况。
 *
 * @author linckye 2018-07-28
 */
@Data
@Accessors(chain = true, fluent = true)
public class DagSchedule {

    Dag dag;

    /** 每个 {@code Node} 的 {@code NodeDistribution} 映射表。**/
    Map<Node, NodeDistribution> nodeDistributionMap;

    DateTime startTime;

    DateTime endTime;

    DagScheduleStatus dagScheduleStatus;

    /** 到达结束节点的分支数量计数器，{@code Dag} 是否完全调度，应由最后到达分支决定。**/
    AtomicInteger endNodeBranchArrivalCounter;

    List<DagScheduleListener> dagScheduleListeners;

}
