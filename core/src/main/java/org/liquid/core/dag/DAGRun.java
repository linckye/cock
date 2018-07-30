package org.liquid.core.dag;

import lombok.Data;
import lombok.experimental.Accessors;
import org.joda.time.DateTime;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author linckye 2018-07-28
 */
@Data
@Accessors(chain = true, fluent = true)
public class DAGRun {

    private DAG dag;

    private Map<Node, NodeRun> nodeRunMap;

    private DateTime startTime;

    private DateTime endTime;

    private DAGStatus dagStatus;

    private AtomicInteger endNodeArrivalCounter;

    private Set<DAGRunListener> dagRunListeners;

}
