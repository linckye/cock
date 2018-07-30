package org.liquid.core.dag;

import com.google.common.collect.Sets;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author linckye 2018-07-29
 */
@Named
@Data
@Accessors(chain = true, fluent = true)
public class LoacalNodeDistributor implements NodeDistributor {
    @Inject
    private ExecutorService distributedPool;

    @Override
    public void distribute(DAGRun dagRun, NodeRun nodeRun) {
        Runnable node;
        try {
            node = (Runnable) nodeRun.node();
        } catch (ClassCastException e) {
            throw new RuntimeException("Node must belong to Runnable");
        }
        distributedPool.execute(() -> {
            try {
                node.run();
                nodeRun.nodeRunListeners().forEach(nodeRunListener -> nodeRunListener.onSuccess(dagRun, nodeRun));
            } catch (Exception e) {
                nodeRun.nodeRunListeners().forEach(nodeRunListener -> nodeRunListener.onFailure(dagRun, nodeRun, e));
            }
        });
    }
}
