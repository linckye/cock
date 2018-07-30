package org.liquid.core.dag;

import lombok.*;
import lombok.experimental.Accessors;
import org.joda.time.DateTime;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 一次 Node 的运行情况、参数和为完成运行所附加的上下文信息
 *
 * @author linckye 2018-07-26
 */
@Data
@Accessors(chain = true, fluent = true)
public class NodeRun {

    /**
     * 运行的 Node
     */
    private Node node;

    /**
     * 开始时间
     */
    private DateTime startTime;

    /**
     * 结束时间
     */
    private DateTime endTime;

    /**
     * 该 Node 的运行状态
     */
    private NodeRunStatus nodeRunStatus;

    /**
     * 异常
     */
    private Throwable throwable;

    /**
     * 到达该节点的分支数量计数器，DAG 中多分支合并为单分支时的合并节点的调度，应由最后到达分支决定
     */
    private AtomicInteger branchArrivalCounter;

    /**
     * NodeRunListener 集合，监听 DAG 运行
     */
    private Set<NodeRunListener> nodeRunListeners;

}
