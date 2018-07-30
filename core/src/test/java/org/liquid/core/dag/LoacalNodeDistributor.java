package org.liquid.core.dag;

import com.google.common.collect.Sets;

import javax.inject.Named;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author linckye 2018-07-29
 */
@Named
public class LoacalNodeDistributor implements NodeDistributor {

    @Override
    public void distribute(DAGRun dagRun, NodeRun nodeRun, ExecutorService executorService) {
        Runnable node;
        try {
            node = (Runnable) nodeRun.node();
        } catch (ClassCastException e) {
            throw new RuntimeException("Node must belong to Runnable");
        }
        executorService.execute(() -> {
            try {
                node.run();
                nodeRun.nodeRunListeners().forEach(nodeRunListener -> nodeRunListener.onSuccess(dagRun, nodeRun));
            } catch (Exception e) {
                nodeRun.nodeRunListeners().forEach(nodeRunListener -> nodeRunListener.onFailure(dagRun, nodeRun, e));
            }
        });
    }

    public static void main(String[] args) {
        LoacalNodeDistributor loacalDistributor = new LoacalNodeDistributor();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(() -> {
            executorService.execute(() -> loacalDistributor.distribute(new DAGRun(), new NodeRun().node(new LocalNode()).nodeRunListeners(Sets.newHashSet()), executorService));
            executorService.execute(() -> loacalDistributor.distribute(new DAGRun(), new NodeRun().node(new LocalNode()).nodeRunListeners(Sets.newHashSet()), executorService));
            executorService.execute(() -> loacalDistributor.distribute(new DAGRun(), new NodeRun().node(new LocalNode()).nodeRunListeners(Sets.newHashSet()), executorService));
            executorService.execute(() -> loacalDistributor.distribute(new DAGRun(), new NodeRun().node(new LocalNode()).nodeRunListeners(Sets.newHashSet()), executorService));
        });
    }

}
