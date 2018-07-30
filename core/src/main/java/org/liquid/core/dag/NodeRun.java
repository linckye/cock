package org.liquid.core.dag;

import lombok.Data;
import lombok.experimental.Accessors;
import org.joda.time.DateTime;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 节点运行情况
 *
 * @author linckye 2018-07-26
 */
@Data
@Accessors(chain = true, fluent = true)
public class NodeRun {

    /**
     * 此次运行的节点
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
     * 节点状态
     */
    private NodeStatus nodeStatus;

    /**
     * 异常
     */
    private Throwable throwable;

    /**
     * 到达节点分支数量计数器
     */
    private AtomicInteger arrivalCounter;


    private Set<NodeRunListener> nodeRunListeners;

}
