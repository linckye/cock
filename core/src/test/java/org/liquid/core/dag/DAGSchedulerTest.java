package org.liquid.core.dag;

import com.google.common.collect.Sets;
import lombok.Data;
import lombok.experimental.Accessors;
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
@Data
@Accessors(chain = true, fluent = true)
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

        dag.edge(node1, node3, Edge.SIMPLE);
        dag.edge(node2, node4, Edge.SIMPLE);
        dag.edge(node3, node4, Edge.SIMPLE);
        dag.edge(node3, node5, Edge.SIMPLE);
        dag.edge(node4, node6, Edge.SIMPLE);
        dag.edge(node6, node5, Edge.SIMPLE);
        dag.edge(node5, node7, Edge.SIMPLE);
        dag.edge(node6, node8, Edge.SIMPLE);

        CountDownLatch latch = new CountDownLatch(1);

        dagScheduler.schedule(new DAGRunParameters().dag(dag).dagRunListeners(Sets.newHashSet(new DAGRunListener() {
            @Override
            public void onSuccess(DAGRun dagRun) {
                latch.countDown();
                Temp.set(dagRun);
            }

            @Override
            public void onFailure(DAGRun dagRun) {
                Temp.set(dagRun);
            }
        })));

        latch.await(10, TimeUnit.SECONDS);

        DAGRun dagRun = Temp.get();

        // check DAGRun
        assertNotNull(dagRun);
        assertEquals(DAGRunStatus.FINISHED, dagRun.dagRunStatus());
        assertNotNull(dagRun.startTime());
        assertNotNull(dagRun.endTime());

        assertNotNull(dagRun.nodeRunMap());

        assertNotNull(dagRun.nodeRunMap().get(node1));
        assertNotNull(dagRun.nodeRunMap().get(node2));
        assertNotNull(dagRun.nodeRunMap().get(node3));
        assertNotNull(dagRun.nodeRunMap().get(node4));
        assertNotNull(dagRun.nodeRunMap().get(node5));
        assertNotNull(dagRun.nodeRunMap().get(node6));
        assertNotNull(dagRun.nodeRunMap().get(node7));
        assertNotNull(dagRun.nodeRunMap().get(node8));
        assertNotNull(dagRun.nodeRunMap().get(node9));

        dagRun.nodeRunMap().values().forEach(nodeRun -> {
            assertEquals(NodeRunStatus.FINISHED, nodeRun.nodeRunStatus());
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
