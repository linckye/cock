package org.liquid.core.dag;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.Data;
import lombok.experimental.Accessors;
import org.liquid.common.util.Blank;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author linckye 2018-07-27
 */
@Named
@Data
@Accessors(chain = true, fluent = true)
public class DAGScheduler {

    @Resource(name = "scheduledPool")
    private ExecutorService scheduledPool;

    @Resource(name = "distributedPool")
    private ExecutorService distributedPool;

    @Inject
    private NodeDistributor nodeDistributor;

    private NodeRunListener nodeRunSpreadListener = new NodeRunListener() {

        @Override
        public void onSuccess(DAGRun dagRun, NodeRun nodeRun) {
            nodeRun.nodeStatus(NodeStatus.FINISHED);

            DAG dag = dagRun.dag();
            Node node = nodeRun.node();

            Set<Node> endNodes = dag.endNotes();
            if (endNodes.contains(node) &&
                    dagRun.endNodeArrivalCounter().incrementAndGet() == endNodes.size()) {
                dagRun.dagStatus(DAGStatus.FINISHED)
                        .dagRunListeners()
                        .forEach(dagRunListener -> dagRunListener.onSuccess(dagRun));
            }

            dag.successors(node).forEach(successor ->
                    distributeNode(dagRun, dagRun.nodeRunMap().get(successor)));
        }

        @Override
        public void onFailure(DAGRun dagRun, NodeRun nodeRun, Throwable throwable) {
            nodeRun.nodeStatus(NodeStatus.FAILED).throwable(throwable);
            dagRun.dagStatus(DAGStatus.FAILED);
            dagRun.dagRunListeners().forEach(dagRunListener -> dagRunListener.onFailure(dagRun));
        }

    };

    public void schedule(DAGRunParameters dagRunParameters) {
        scheduledPool.execute(() -> {
            wrapDAGRunParameters(dagRunParameters);
            DAGRun dagRun = new DAGRun()
                    .dag(dagRunParameters.dag())
                    .dagStatus(DAGStatus.RUNNING)
                    .nodeRunMap(Maps.newHashMap())
                    .endNodeArrivalCounter(new AtomicInteger())
                    .dagRunListeners(dagRunParameters.dagRunListeners());
            dagRunParameters.dag().nodes().forEach(node -> dagRun.nodeRunMap().put(node, new NodeRun()
                    .node(node)
                    .nodeRunListeners(dagRunParameters.nodeRunListeners())
                    .arrivalCounter(new AtomicInteger())));
            dagRunParameters.dag().startNodes().forEach(node ->
                    distributeNode(dagRun, dagRun.nodeRunMap().get(node)));
        });
    }

    private void wrapDAGRunParameters(DAGRunParameters dagRunParameters) {
        if (Blank.isNull(dagRunParameters.dag())) throw new IllegalStateException("Null DAG");
        if (Blank.isNull(dagRunParameters.dagRunListeners())) dagRunParameters.dagRunListeners(Sets.newHashSet());
        if (Blank.isNull(dagRunParameters.nodeRunListeners())) dagRunParameters.nodeRunListeners(Sets.newHashSet());
        dagRunParameters.nodeRunListeners().add(nodeRunSpreadListener);
    }

    private void distributeNode(DAGRun dagRun, NodeRun nodeRun) {
        if (NodeStatus.FAILED.equals(nodeRun.nodeStatus())) return;
        Node node = nodeRun.node();
        DAG dag = dagRun.dag();

        // merged node
        int mergedSize = dag.predecessors(node).size();
        if (mergedSize > 1 && nodeRun.arrivalCounter().incrementAndGet() != mergedSize) return;

        nodeDistributor.distribute(dagRun, nodeRun.nodeStatus(NodeStatus.RUNNING), distributedPool);
    }

    public void close() {
        scheduledPool.shutdown();
    }
}
