package org.liquid.scheduler.core.dags;

import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * {@code Dag} 中的有向边，代表两个 {@code Node} 之间的依赖关系。
 *
 * @author linckye 2018-07-18
 */
@Accessors(chain = true, fluent = true)
public class Edge {

    @Getter
    private static final Edge simple = new Edge();

}
