package org.liquid.scheduler.core.dags;

import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;
import lombok.experimental.Accessors;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

/** {@code DagScheduler} 实现。**/
@Data
@Accessors(chain = true, fluent = true)
@Component
public class DagSchedulerImpl
        implements DagScheduler {

    /** {@code Dag } 调度池。**/
    @Autowired
    private ExecutorService scheduledPool;

    /** {@code Node } 发布池。**/
    @Autowired
    private ExecutorService distributedPool;

    @Autowired
    private NodeDistributor nodeDistributor;

    private List<DagScheduleListener> dagScheduleListeners = Lists.newArrayList();

    private List<NodeDistributionListener> nodeDistributionListeners = Lists.newArrayList();

    {
        nodeDistributionListeners.add(new NodeDistributionListener() {

            @Override
            public void onCompletion(@Nonnull DagSchedule dagSchedule, @Nonnull NodeDistribution nodeDistribution) {
                nodeDistribution.nodeDistributionStatus(NodeDistributionStatus.FINISHED).endTime(new DateTime());

                Dag dag = dagSchedule.dag();
                Node node = nodeDistribution.node();

                Set<Node> endNodes = dag.endNotes();
                if (endNodes.contains(node) &&
                        dagSchedule.endNodeBranchArrivalCounter().incrementAndGet() == endNodes.size()) {
                    dagSchedule.dagScheduleStatus(DagScheduleStatus.FINISHED)
                            .endTime(new DateTime())
                            .dagScheduleListeners()
                            .forEach(dagScheduleListener -> dagScheduleListener.onSuccess(dagSchedule));
                }

                dag.successors(node).forEach(successor ->
                        distributeNode(dagSchedule, dagSchedule.nodeDistributionMap().get(successor)));
            }

            @Override
            public void onFailure(@Nonnull DagSchedule dagSchedule, @Nonnull NodeDistribution nodeDistribution, @Nonnull Throwable throwable) {
                System.out.println(Throwables.getStackTraceAsString(throwable));
                nodeDistribution.nodeDistributionStatus(NodeDistributionStatus.FAILED)
                        .endTime(new DateTime())
                        .throwable(throwable);
                dagSchedule.dagScheduleStatus(DagScheduleStatus.FAILED)
                        .endTime(new DateTime())
                        .dagScheduleListeners().forEach(dagScheduleListener -> dagScheduleListener.onFailure(dagSchedule));
            }

        });
    }

    /** {@inheritDoc}
     * @param dag**/
    @Override
    public void schedule(@Nonnull Dag dag) {
        scheduledPool.execute(() -> {
            DagSchedule dagSchedule = new DagSchedule()
                    .dag(dag)
                    .dagScheduleStatus(DagScheduleStatus.SCHEDULING)
                    .nodeDistributionMap(Maps.newHashMap())
                    .startTime(new DateTime())
                    .endNodeBranchArrivalCounter(new AtomicInteger())
                    .dagScheduleListeners(dagScheduleListeners);
            dag.nodes().forEach(node -> dagSchedule.nodeDistributionMap().put(node, new NodeDistribution()
                    .node(node)
                    .nodeDistributionListeners(nodeDistributionListeners)
                    .branchArrivalCounter(new AtomicInteger())));
            dag.startNodes().forEach(node ->
                    distributeNode(dagSchedule, dagSchedule.nodeDistributionMap().get(node)));
        });
    }

    /** {@inheritDoc} **/
    @Override
    public void stop() {
        scheduledPool.shutdown();
        distributedPool.shutdown();
    }

    /** {@inheritDoc} **/
    @Override
    public DagSchedulerImpl registerDAGScheduleListener(DagScheduleListener dagScheduleListener) {
        dagScheduleListeners.add(dagScheduleListener);
        return this;
    }

    /** {@inheritDoc} **/
    @Override
    public DagSchedulerImpl registerNodeDistributionListener(NodeDistributionListener nodeDistributionListener) {
        nodeDistributionListeners.add(nodeDistributionListener);
        return this;
    }

    private void distributeNode(DagSchedule dagSchedule, NodeDistribution nodeDistribution) {
        if (NodeDistributionStatus.FAILED.equals(nodeDistribution.nodeDistributionStatus())) return;
        Node node = nodeDistribution.node();
        Dag dag = dagSchedule.dag();

        // merged node
        int mergedSize = dag.predecessors(node).size();
        if (mergedSize > 1 && nodeDistribution.branchArrivalCounter().incrementAndGet() != mergedSize) return;

        nodeDistributor.distribute(dagSchedule, nodeDistribution.startTime(new DateTime()).nodeDistributionStatus(NodeDistributionStatus.DISTRIBUTED));
    }

}
