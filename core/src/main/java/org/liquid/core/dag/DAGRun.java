package org.liquid.core.dag;

import lombok.*;
import lombok.experimental.Accessors;
import org.joda.time.DateTime;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 一次 DAG 的调度情况、参数和为完成调度所附加的上下文信息
 *
 * @author linckye 2018-07-28
 */
@Data
@Accessors(chain = true, fluent = true)
public class DAGRun {

    /**
     * 运行的 DAG
     */
    DAG dag;

    /**
     * DAG 中每一个 Node 对应的 NodeRun 映射表
     */
    Map<Node, NodeRun> nodeRunMap;

    /**
     * 开始时间
     */
    DateTime startTime;

    /**
     * 结束时间
     */
    DateTime endTime;

    /**
     * 该 DAG 的运行状态
     */
    DAGRunStatus dagRunStatus;

    /**
     * 到达结束节点的分支数量计数器，DAG 是否完全调度，应由最后到达分支决定
     */
    AtomicInteger endNodeBranchArrivalCounter;

    /**
     * DAGRunListener 集合，监听 DAG 运行
     */
    Set<DAGRunListener> dagRunListeners;

}
