package org.liquid.core.dag;

import com.google.common.collect.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.liquid.test.concurrent.Temp;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * @author linckye 2018-07-29
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class DAGSchedulerTest {

    @Inject
    private DAGScheduler dagScheduler;

    @Test
    public void test() throws Exception {
        DAG dag = new DAG();

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

        dagScheduler.schedule(new DAGScheduleParameters().dag(dag).dagScheduleListeners(Sets.newHashSet(new DAGScheduleListener() {
            @Override
            public void onSuccess(DAGSchedule dagSchedule) {
                latch.countDown();
                Temp.set(dagSchedule);
            }

            @Override
            public void onFailure(DAGSchedule dagSchedule) {
                latch.countDown();
                Temp.set(dagSchedule);
            }
        })));

        latch.await(10, TimeUnit.SECONDS);

        DAGSchedule dagSchedule = Temp.get();

        // check DAGSchedule
        assertNotNull(dagSchedule);
        assertEquals(DAGScheduleStatus.FINISHED, dagSchedule.dagScheduleStatus());
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
