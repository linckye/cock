package org.liquid.core.dag;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.ExecutorService;

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
    public void distribute(@Nonnull DAGSchedule dagSchedule, @Nonnull NodeDistribution nodeDistribution) {
        Runnable node;
        try {
            node = (Runnable) nodeDistribution.node();
        } catch (ClassCastException e) {
            throw new RuntimeException("Node must belong to Runnable");
        }
        distributedPool.execute(() -> {
            try {
                node.run();
                nodeDistribution.nodeDistributionListeners().forEach(nodeRunListener -> nodeRunListener.onCompletion(dagSchedule, nodeDistribution));
            } catch (Exception e) {
                nodeDistribution.nodeDistributionListeners().forEach(nodeRunListener -> nodeRunListener.onFailure(dagSchedule, nodeDistribution, e));
            }
        });
    }
}
