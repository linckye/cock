package org.liquid.scheduler.core.dags;

import javax.annotation.Nonnull;

/**
 * {@code Node} 发布器，负责将 {@code Node} 发布给工作者 。
 *
 * @author linckye 2018-07-27
 */
public interface NodeDistributor {

    /** 将 {@code Node} 发布给工作者。**/
    void distribute(@Nonnull DagSchedule dagSchedule,
                    @Nonnull NodeDistribution nodeDistribution);

}
