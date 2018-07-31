package org.liquid.core.dag;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
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

/** {@code DAGScheduler} 实现。**/
@Named
@Data
@Accessors(chain = true, fluent = true)
public class DAGSchedulerImpl
        implements DAGScheduler {

    /** {@code DAG } 调度池。**/
    @Resource(name = "scheduledPool")
    private ExecutorService scheduledPool;

    /** {@code Node } 发布池。**/
    @Resource(name = "distributedPool")
    private ExecutorService distributedPool;

    @Inject
    private NodeDistributor nodeDistributor;

    /** 负责 {@code DAG } 中 {@code Node } 的依赖调度。**/
    private NodeDistributionListener nodeScheduledListener = new NodeDistributionListener() {

        @Override
        public void onCompletion(DAGSchedule dagSchedule, NodeDistribution nodeDistribution) {
            nodeDistribution.nodeDistributionStatus(NodeDistributionStatus.FINISHED).endTime(new DateTime());

            DAG dag = dagSchedule.dag();
            Node node = nodeDistribution.node();

            Set<Node> endNodes = dag.endNotes();
            if (endNodes.contains(node) &&
                    dagSchedule.endNodeBranchArrivalCounter().incrementAndGet() == endNodes.size()) {
                dagSchedule.dagScheduleStatus(DAGScheduleStatus.FINISHED)
                        .endTime(new DateTime())
                        .dagScheduleListeners()
                        .forEach(dagScheduleListener -> dagScheduleListener.onSuccess(dagSchedule));
            }

            dag.successors(node).forEach(successor ->
                    distributeNode(dagSchedule, dagSchedule.nodeDistributionMap().get(successor)));
        }

        @Override
        public void onFailure(DAGSchedule dagSchedule, NodeDistribution nodeDistribution, Throwable throwable) {
            System.out.println(Throwables.getStackTraceAsString(throwable));
            nodeDistribution.nodeDistributionStatus(NodeDistributionStatus.FAILED)
                    .endTime(new DateTime())
                    .throwable(throwable);
            dagSchedule.dagScheduleStatus(DAGScheduleStatus.FAILED)
                    .endTime(new DateTime())
                    .dagScheduleListeners().forEach(dagScheduleListener -> dagScheduleListener.onFailure(dagSchedule));
        }

    };

    /** {@inheritDoc} **/
    @Override
    public void schedule(DAGScheduleParameters dagScheduleParameters) {
        Preconditions.checkNotNull(dagScheduleParameters.dag(), "Null dag");
        scheduledPool.execute(() -> {
            Set<DAGScheduleListener> dagScheduleListeners = Optional
                    .ofNullable(dagScheduleParameters.dagScheduleListeners())
                    .orElse(Sets.newHashSet());
            Set<NodeDistributionListener> nodeDistributionListeners = Optional
                    .ofNullable(dagScheduleParameters.nodeDistributionListeners())
                    .orElse(Sets.newHashSet());
            nodeDistributionListeners.add(nodeScheduledListener);

            DAGSchedule dagSchedule = new DAGSchedule()
                    .dag(dagScheduleParameters.dag())
                    .dagScheduleStatus(DAGScheduleStatus.SCHEDULING)
                    .nodeDistributionMap(Maps.newHashMap())
                    .startTime(new DateTime())
                    .endNodeBranchArrivalCounter(new AtomicInteger())
                    .dagScheduleListeners(dagScheduleListeners);
            dagScheduleParameters.dag().nodes().forEach(node -> dagSchedule.nodeDistributionMap().put(node, new NodeDistribution()
                    .node(node)
                    .nodeDistributionListeners(nodeDistributionListeners)
                    .branchArrivalCounter(new AtomicInteger())));
            dagScheduleParameters.dag().startNodes().forEach(node ->
                    distributeNode(dagSchedule, dagSchedule.nodeDistributionMap().get(node)));
        });
    }

    /** {@inheritDoc} **/
    @Override
    public void shutdown() {
        scheduledPool.shutdown();
        distributedPool.shutdown();
    }

    /** {@inheritDoc} **/
    @Override
    public void registerDistributor(NodeDistributor nodeDistributor) {
        this.nodeDistributor = nodeDistributor;
    }

    private void distributeNode(DAGSchedule dagSchedule, NodeDistribution nodeDistribution) {
        if (NodeDistributionStatus.FAILED.equals(nodeDistribution.nodeDistributionStatus())) return;
        Node node = nodeDistribution.node();
        DAG dag = dagSchedule.dag();

        // merged node
        int mergedSize = dag.predecessors(node).size();
        if (mergedSize > 1 && nodeDistribution.branchArrivalCounter().incrementAndGet() != mergedSize) return;

        nodeDistributor.distribute(dagSchedule, nodeDistribution.startTime(new DateTime()).nodeDistributionStatus(NodeDistributionStatus.DISTRIBUTED));
    }

}
