package org.liquid.core.dag;

import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;
import lombok.ToString;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 有向无环图，代表 Node 之间的依赖关系。
 *
 * @author linckye 2018-07-22
 */
@ToString
public class DAG {

    private MutableValueGraph<Node, Edge> graph = ValueGraphBuilder.directed().build();

    /** 获取所有节点。**/
    public Set<Node> nodes() {
        return graph.nodes();
    }

    /**
     * 添加一个节点。
     *
     * @return 是否添加成功
     */
    public boolean node(Node node) {
        return graph.addNode(node);
    }

    /**
     * 添加有向边，{@code edge} 将由 {@code predecessor} 指向 {@code successor}
     *
     * @return 连接两节点的旧边，不存在时为空。
     */
    public Edge edge(Node predecessor,
                     Node successor,
                     Edge edge) {
        return graph.putEdgeValue(predecessor, successor, edge);
    }

    /** 获取 {@code node} 的后继节点。**/
    public Set<Node> successors(Node node) {
        return graph.successors(node);
    }

    /** 获取 {@code node} 的前驱节点。**/
    public Set<Node> predecessors(Node node) {
        return graph.predecessors(node);
    }

    /** 获取由 {@code predecessor} 指向 {@code successor} 的有向边。**/
    public Optional<Edge> edge(Node predecessor,
                               Node successor) {
        return graph.edgeValue(predecessor, successor);
    }

    /** 获取 {@code node} 的入度。**/
    public int inDegree(Node node) {
        return graph.inDegree(node);
    }

    /** 获取 {@code node} 的出度。**/
    public int outDegree(Node node) {
        return graph.outDegree(node);
    }

    /** 获取 {@code DAG} 的开始节点。**/
    public Set<Node> startNodes() {
        return graph.nodes().stream()
                .filter(node -> graph.inDegree(node) == 0)
                .collect(Collectors.toSet());
    }

    /** 获取 {@code DAG} 的结束节点。**/
    public Set<Node> endNotes() {
        return graph.nodes().stream()
                .filter(node -> graph.outDegree(node) == 0)
                .collect(Collectors.toSet());
    }

}
