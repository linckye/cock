package org.liquid.core.dag;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;

/**
 * DAG 运行参数
 *
 * @author linckye 2018-07-30
 */
@Data
@Accessors(chain = true, fluent = true)
public class DAGRunParameters {

    private DAG dag;

    private Set<DAGRunListener> dagRunListeners;

    private Set<NodeRunListener> nodeRunListeners;

}
