package org.liquid.core.dag;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.Data;
import lombok.experimental.Accessors;
import org.joda.time.DateTime;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * DAG 调度器
 *
 * @author linckye 2018-07-27
 */
@Named
@Data
@Accessors(chain = true, fluent = true)
public class DAGScheduler {

    /**
     * DAG 调度池
     */
    @Resource(name = "scheduledPool")
    private ExecutorService scheduledPool;

    /**
     * Node 发布池
     */
    @Resource(name = "distributedPool")
    private ExecutorService distributedPool;

    /**
     * 负责发布一个 Node
     */
    @Inject
    private NodeDistributor nodeDistributor;

    /**
     * 负责 DAG 中 Node 的调度工作
     */
    private NodeRunListener nodeScheduledListener = new NodeRunListener() {

        @Override
        public void onSuccess(DAGRun dagRun, NodeRun nodeRun) {
            nodeRun.nodeRunStatus(NodeRunStatus.FINISHED).endTime(new DateTime());

            DAG dag = dagRun.dag();
            Node node = nodeRun.node();

            Set<Node> endNodes = dag.endNotes();
            if (endNodes.contains(node) &&
                    dagRun.endNodeBranchArrivalCounter().incrementAndGet() == endNodes.size()) {
                dagRun.dagRunStatus(DAGRunStatus.FINISHED)
                        .endTime(new DateTime())
                        .dagRunListeners()
                        .forEach(dagRunListener -> dagRunListener.onSuccess(dagRun));
            }

            dag.successors(node).forEach(successor ->
                    distributeNode(dagRun, dagRun.nodeRunMap().get(successor)));
        }

        @Override
        public void onFailure(DAGRun dagRun, NodeRun nodeRun, Throwable throwable) {
            nodeRun.nodeRunStatus(NodeRunStatus.FAILED)
                    .endTime(new DateTime())
                    .throwable(throwable);
            dagRun.dagRunStatus(DAGRunStatus.FAILED)
                    .endTime(new DateTime())
                    .dagRunListeners().forEach(dagRunListener -> dagRunListener.onFailure(dagRun));
        }

    };

    /**
     * 调度一个 DAG
     *
     * @param dagRunParameters DAG 调度参数
     */
    public void schedule(DAGRunParameters dagRunParameters) {
        Preconditions.checkNotNull(dagRunParameters.dag(), "Null dag");
        scheduledPool.execute(() -> {
            Set<DAGRunListener> dagRunListeners = Optional
                    .ofNullable(dagRunParameters.dagRunListeners())
                    .orElse(Sets.newHashSet());
            Set<NodeRunListener> nodeRunListeners = Optional
                    .ofNullable(dagRunParameters.nodeRunListeners())
                    .orElse(Sets.newHashSet());
            nodeRunListeners.add(nodeScheduledListener);

            DAGRun dagRun = new DAGRun()
                    .dag(dagRunParameters.dag())
                    .dagRunStatus(DAGRunStatus.RUNNING)
                    .nodeRunMap(Maps.newHashMap())
                    .startTime(new DateTime())
                    .endNodeBranchArrivalCounter(new AtomicInteger())
                    .dagRunListeners(dagRunListeners);
            dagRunParameters.dag().nodes().forEach(node -> dagRun.nodeRunMap().put(node, new NodeRun()
                    .node(node)
                    .nodeRunListeners(nodeRunListeners)
                    .branchArrivalCounter(new AtomicInteger())));
            dagRunParameters.dag().startNodes().forEach(node ->
                    distributeNode(dagRun, dagRun.nodeRunMap().get(node)));
        });
    }

    private void distributeNode(DAGRun dagRun, NodeRun nodeRun) {
        if (NodeRunStatus.FAILED.equals(nodeRun.nodeRunStatus())) return;
        Node node = nodeRun.node();
        DAG dag = dagRun.dag();

        // merged node
        int mergedSize = dag.predecessors(node).size();
        if (mergedSize > 1 && nodeRun.branchArrivalCounter().incrementAndGet() != mergedSize) return;

        nodeDistributor.distribute(dagRun, nodeRun.startTime(new DateTime()).nodeRunStatus(NodeRunStatus.RUNNING));
    }

    /**
     * 关闭调度器
     */
    public void shutdown() {
        scheduledPool.shutdown();
        distributedPool.shutdown();
    }

}
