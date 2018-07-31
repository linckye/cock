package org.liquid.core.dag;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;

/**
 * {@code DAG} 调度参数。
 *
 * @author linckye 2018-07-30
 */
@Data
@Accessors(chain = true, fluent = true)
public class DAGScheduleParameters {

    private DAG dag;

    private Set<DAGScheduleListener> dagScheduleListeners;

    private Set<NodeDistributionListener> nodeDistributionListeners;

}
