package org.liquid.core.dag;

import javax.annotation.Nonnull;

/**
 * {@code Node} 发布器，负责将 {@code Node} 发布给工作者 。
 *
 * @author linckye 2018-07-27
 */
public interface NodeDistributor {

    /** 将 {@code Node} 发布给工作者。**/
    void distribute(@Nonnull DAGSchedule dagSchedule,
                    @Nonnull NodeDistribution nodeDistribution);

}
