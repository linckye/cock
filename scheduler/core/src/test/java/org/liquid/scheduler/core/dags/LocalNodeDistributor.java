package org.liquid.scheduler.core.dags;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.concurrent.ExecutorService;

/**
 * @author linckye 2018-07-29
 */
@Data
@Accessors(chain = true, fluent = true)
@Component
public class LocalNodeDistributor implements NodeDistributor {

    @Autowired
    private ExecutorService distributedPool;

    @Override
    public void distribute(@Nonnull DagSchedule dagSchedule, @Nonnull NodeDistribution nodeDistribution) {
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
