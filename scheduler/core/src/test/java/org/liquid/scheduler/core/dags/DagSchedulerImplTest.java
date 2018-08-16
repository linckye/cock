package org.liquid.scheduler.core.dags;

import org.junit.Before;
import org.junit.Test;
import org.liquid.test.concurrent.Temp;

import javax.annotation.Nonnull;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * @author linckye 2018-08-16
 */
public class DagSchedulerImplTest {

    private DagSchedulerImpl dagScheduler = new DagSchedulerImpl();

    @Before
    public void before() throws Exception {
        dagScheduler.scheduledPool(Executors.newCachedThreadPool())
                .distributedPool(dagScheduler.scheduledPool())
                .nodeDistributor(new LocalNodeDistributor()
                        .distributedPool(dagScheduler.distributedPool()));
    }

    @Test
    public void test() throws Exception {
        Dag dag = new Dag();

        LocalNode node1 = new LocalNode().msg("1");
        LocalNode node2 = new LocalNode().msg("2");
        LocalNode node3 = new LocalNode().msg("3");
        LocalNode node4 = new LocalNode().msg("4");
        LocalNode node5 = new LocalNode().msg("5");
        LocalNode node6 = new LocalNode().msg("6");
        LocalNode node7 = new LocalNode().msg("7");
        LocalNode node8 = new LocalNode().msg("8");
        LocalNode node9 = new LocalNode().msg("9");

        dag.node(node1);
        dag.node(node2);
        dag.node(node3);
        dag.node(node4);
        dag.node(node5);
        dag.node(node6);
        dag.node(node7);
        dag.node(node8);
        dag.node(node9);

        dag.edge(node1, node3, Edge.simple());
        dag.edge(node2, node4, Edge.simple());
        dag.edge(node3, node4, Edge.simple());
        dag.edge(node3, node5, Edge.simple());
        dag.edge(node4, node6, Edge.simple());
        dag.edge(node6, node5, Edge.simple());
        dag.edge(node5, node7, Edge.simple());
        dag.edge(node6, node8, Edge.simple());

        CountDownLatch latch = new CountDownLatch(1);

        dagScheduler.schedule(dag);
        dagScheduler.registerDAGScheduleListener(new DagScheduleListener() {
            @Override
            public void onSuccess(@Nonnull DagSchedule dagSchedule) {
                latch.countDown();
                Temp.set(dagSchedule);
            }

            @Override
            public void onFailure(@Nonnull DagSchedule dagSchedule) {
                latch.countDown();
                Temp.set(dagSchedule);
            }
        });

        latch.await(10, TimeUnit.SECONDS);

        DagSchedule dagSchedule = Temp.get();

        // check DAGSchedulePO
        assertNotNull(dagSchedule);
        assertEquals(DagScheduleStatus.FINISHED, dagSchedule.dagScheduleStatus());
        assertNotNull(dagSchedule.startTime());
        assertNotNull(dagSchedule.endTime());

        assertNotNull(dagSchedule.nodeDistributionMap());

        assertNotNull(dagSchedule.nodeDistributionMap().get(node1));
        assertNotNull(dagSchedule.nodeDistributionMap().get(node2));
        assertNotNull(dagSchedule.nodeDistributionMap().get(node3));
        assertNotNull(dagSchedule.nodeDistributionMap().get(node4));
        assertNotNull(dagSchedule.nodeDistributionMap().get(node5));
        assertNotNull(dagSchedule.nodeDistributionMap().get(node6));
        assertNotNull(dagSchedule.nodeDistributionMap().get(node7));
        assertNotNull(dagSchedule.nodeDistributionMap().get(node8));
        assertNotNull(dagSchedule.nodeDistributionMap().get(node9));

        dagSchedule.nodeDistributionMap().values().forEach(nodeRun -> {
            assertEquals(NodeDistributionStatus.FINISHED, nodeRun.nodeDistributionStatus());
            assertNotNull(nodeRun.startTime());
            assertNotNull(nodeRun.endTime());
            assertNotNull(((LocalNode) nodeRun.node()).runTrace());
        });

        /*
               1 -* 3 -* 5 -* 7
                    |    *
                    *    |
            9  2 -* 4 -* 6 -* 8
         */
        assertTrue(node1.runTrace().isRunBefore(node3.runTrace()));
        assertTrue(node2.runTrace().isRunBefore(node4.runTrace()));
        assertTrue(node3.runTrace().isRunBefore(node4.runTrace()));
        assertTrue(node3.runTrace().isRunBefore(node5.runTrace()));
        assertTrue(node4.runTrace().isRunBefore(node6.runTrace()));
        assertTrue(node5.runTrace().isRunBefore(node7.runTrace()));
        assertTrue(node6.runTrace().isRunBefore(node5.runTrace()));
        assertTrue(node6.runTrace().isRunBefore(node8.runTrace()));

    }

}